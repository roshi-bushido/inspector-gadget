package inspector.gadget.cms

import dto.view.dashboard.ApplicationSanity
import dto.view.dashboard.GlobalDashboardDTO
import dto.view.dashboard.JobSanity
import dto.view.dashboard.MonthlyJobSanity
import inspector.gadget.Application
import inspector.gadget.SystemConfiguration
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.util.DateUtil
import model.status.JobStatus

class DashboardController extends CmsController {

    def index() {
        redirect(action: "global")
    }

    def application() {
        def applicationId = params?.applicationId
        def application = Application.findById(Long.valueOf(applicationId))
        def applicationSanity = new ApplicationSanity(application: toViewDTO(application))

        def jobs = Job.findAllByApplication(application)

        if (!jobs.isEmpty()) {
            jobs.each { job ->
                if (job.hasInstances()) {
                    def errors = JobInstance.countByJobAndStatus(job, JobStatus.ERROR)
                    def success = JobInstance.countByJobAndStatus(job, JobStatus.OK)
                    def pending = JobInstance.countByJobAndStatus(job, JobStatus.PENDING)
                    def warnings = JobInstance.countByJobAndStatus(job, JobStatus.WARNING)
                    applicationSanity.addJobSanity(new JobSanity(error: errors, success: success, pending: pending, warning: warnings, jobName: job.name, jobId: job.id))
                }
            }
        }

        withFormat {
            html { [instance: applicationSanity, applicationList: toCollectionDTO(Application.list())] }
            json {
                response.setContentType("application/json")
                renderJSON(applicationSanity)
            }
        }
    }

    def job() {
        def jobId = params?.jobId
        def job = Job.findById(Long.valueOf(jobId))
        def jobList = Job.findAll()
        def currentMonth = params.currentMonth ?: (Calendar.getInstance().get(Calendar.MONTH) + 1)
        def startDate
        def endDate
        def monthlySanity

        if ( params.currentMonth != null && params.currentMonth.isNumber() ) {
            startDate = DateUtil.firstDayOfMonth(Integer.valueOf(currentMonth))
            endDate = DateUtil.lastDayOfMonth(Integer.valueOf(currentMonth))
            monthlySanity = MonthlyJobSanity.forMonth(Integer.valueOf(params.currentMonth))
        } else {
            startDate = DateUtil.firstDayOfCurrentMonth()
            endDate = Calendar.getInstance().getTime()
            currentMonth = (Calendar.getInstance().get(Calendar.MONTH) + 1)
            monthlySanity = MonthlyJobSanity.forCurrentMonth()
        }

        def instances = JobInstance.findAllByJobAndStartedAtBetween(job, startDate, endDate)

        monthlySanity.jobName = job.name
        instances.each { jobInstance ->
            def dailyJobSanity = monthlySanity.getJobSanityFor(jobInstance.startedAt)
            if (jobInstance.status.equals(JobStatus.ERROR)) { dailyJobSanity?.addError() }
            if (jobInstance.status.equals(JobStatus.WARNING)) { dailyJobSanity?.addWarning() }
            if (jobInstance.status.equals(JobStatus.OK)) { dailyJobSanity?.addSuccess() }
            if (jobInstance.status.equals(JobStatus.PENDING)) { dailyJobSanity?.addPending() }
        }

        withFormat {
            html { [instance: monthlySanity, jobList: toCollectionDTO(jobList), currentMonth: currentMonth] }
            json {
                response.setContentType("application/json")
                renderJSON(monthlySanity)
            }
        }
    }

    def global() {
        def globalDashboard = new GlobalDashboardDTO()
        def refreshRate = SystemConfiguration.findByKey("config.dashboard.refresh").valueAsInteger()
        def daysBefore  = SystemConfiguration.findByKey("config.dashboard.daysBefore").valueAsInteger()

        def from = DateUtil.substractDaysTo(daysBefore, Calendar.getInstance().getTime())
        def to = Calendar.getInstance().getTime()

        def lastFailures = JobInstance.findAllByStatusAndStartedAtBetween(JobStatus.ERROR, from, to, [sort: "id", order: "desc", max: 10])
        lastFailures.each { failure -> globalDashboard.addJobFailure(toViewDTO(failure)) }

        Application.list().each { application ->
            def count = Job.countByApplication(application)
            def failures = JobInstance.countByApplicationAndJobStatus(application, JobStatus.ERROR)
            if (count > 0) {
                globalDashboard.setJobCountFor(toViewDTO(application), count.intValue(), failures)
            }
        }

        withFormat {
            html {
                [dashboardInstance: globalDashboard, refreshRate: refreshRate] }
            json {
                response.setContentType("application/json")
                renderJSON(globalDashboard)
            }
        }
    }
}
