package inspector.gadget.cms

import grails.plugins.springsecurity.Secured
import inspector.gadget.Application
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import model.status.JobStatus

class JobInstanceController extends CmsController {
    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']
    static scaffold = JobInstance

    @Secured('ROLE_USER')
    def index() {
        redirect action: 'panel', params: params
    }

    @Secured('ROLE_USER')
    def panel() {
        def max = (params.max)? Integer.valueOf(params.max) : 10
        def offset = (params.offset)? Integer.valueOf(params.offset) : 0
        def applications = Application.list()

        def currentApplication = params.applicationId
        def jobId = params.jobId
        def jobStatusId = params.jobStatusId

        def jobInstanceList
        def jobInstanceCount
        def jobList = new ArrayList()

        if ( currentApplication != null && !currentApplication.isEmpty()) {
            def jobFilterQuery = ""

            if ( jobId != null && !jobId.isEmpty()) {
              jobFilterQuery += " and ji.job.id=${jobId}"
            }

            if ( jobStatusId != null && !jobStatusId.isEmpty()) {
              jobFilterQuery += " and ji.status='${JobStatus.byId(Integer.valueOf(jobStatusId))}'"
            }

            def query = "from JobInstance as ji where ji.job.application.id=${currentApplication} ${jobFilterQuery} order by id desc"
            def countQuery = "select count(*) ${query}"
            jobInstanceList = JobInstance.findAll(query, [max: max, offset: offset])
            jobInstanceCount = JobInstance.executeQuery(countQuery).first()
            jobList = Job.findAllByApplication(Application.findById(Long.valueOf(currentApplication)))
        } else {
            if ( jobStatusId != null && !jobStatusId.isEmpty()) {
                jobInstanceList = JobInstance.findAllByStatus(JobStatus.byId(Integer.valueOf(jobStatusId)), [max: max, offset: offset, sort: "id", order: "desc"])
                jobInstanceCount = JobInstance.countByStatus(JobStatus.byId(Integer.valueOf(jobStatusId)))
                params.jobId=null
                params.applicationId=null
            } else {
                jobInstanceList = JobInstance.list([max: max, offset: offset, sort: "id", order: "desc"])
                jobInstanceCount = JobInstance.count()
            }
        }

        [jobInstanceInstanceList: jobInstanceList, jobInstanceInstanceTotal: jobInstanceCount, applicationList: applications, jobList: jobList, params: params, jobStatusList: JobStatus.list()]
    }

    @Secured('ROLE_USER')
    def show() {
        def jobInstanceInstance = JobInstance.get(params.id)
        if (!jobInstanceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'jobInstance.label', default: 'JobInstance'), params.id])
            redirect action: 'list'
            return
        }

        [jobInstanceInstance: jobInstanceInstance]
    }
}
