package inspector.gadget.job

import inspector.gadget.Application
import inspector.gadget.util.DateUtil
import model.status.JobStatus

class JobInstance {
    transient def notificationService
    private static final int MAX_STRING_LENGTH = 5000
    Job job
    Date startedAt
    Date endedAt
    JobStatus status
    String description
    Set events = []

    static hasMany = [events: JobInstanceEvent]

    static constraints = {
        job(nullable: false)
        startedAt(nullable: false)
        endedAt(nullable: true)
        status(nullable: false, blank: false)
        description(nullable: true, blank: true, maxSize: MAX_STRING_LENGTH)
    }

    static mapping = {
        table 'T_JOB_INSTANCE'
        version false
        id(column: 'P_ID')
        job(column: 'F_JOB_ID')
        startedAt(column: 'A_STARTED_AT_DATE')
        endedAt(column: 'A_ENDED_AT_DATE',)
        status(column: 'A_STATUS')
        description(column: 'A_DESCRIPTION',  type: 'text')
    }

    String toString() {
        return "Job(${job.id}) instance (${id}) "
    }

    def beforeInsert() {
        trimDescription()
    }

    def beforeUpdate() {
        trimDescription()
    }

    private def trimDescription() {
        if (description?.length() > MAX_STRING_LENGTH) {
            this.description = this.description.substring(0, MAX_STRING_LENGTH -1)
        }
    }

    public def calculateResult() {
        Integer warningThreshold = job.warningDurationThreshold
        Integer jobExecutionDuration = DateUtil.diffInSeconds(this.startedAt, this.endedAt)

        if (jobExecutionDuration > warningThreshold) {
            this.warning("Job execution lasted longer than it should. Lasted ${jobExecutionDuration} and it should have lasted ${warningThreshold}.")
        } else {
            this.success("Job completed successfully in ${jobExecutionDuration} seconds.")
        }
    }

    def saveResults() {
        this.endedAt = new Date()
        this.calculateResult()
    }

    def crashed(String reason) {
        this.endedAt = Calendar.getInstance().getTime()
        this.error(reason)
    }

    def warning(String description) {
        this.status = JobStatus.WARNING
        this.description = description
        this.save(flush: true, failOnError: true)
        this.notificationService.fireWarningNotification(this)
    }

    def error(String description) {
        this.status = JobStatus.ERROR
        this.description = description
        this.trimDescription()
        this.save(flush: true, failOnError: true)
        this.notificationService.fireErrorNotification(this)
    }

    def success(String description) {
        this.status = JobStatus.OK
        this.description = description
        this.save(flush: true, failOnError: true)
    }

    def pending(String description) {
        this.status = JobStatus.PENDING
        this.description = description
        this.save(flush: true, failOnError: true)
    }

    static def findLastFrom(Job job) {
        def instanceList = JobInstance.findAllByJobAndStatusNotEqual(job, JobStatus.PENDING , [max: 1, sort: "id", order: "desc"])

        if (!instanceList.isEmpty()) {
            return instanceList.last()
        } else {
            return null;
        }
    }

    static def countByApplicationAndJobStatus(Application application, JobStatus jobStatus) {
        def query = "select count(*) from JobInstance as ji where ji.job.application.id=${application.id} and ji.status='${jobStatus}' order by id desc"
        def count = JobInstance.executeQuery(query).first()
        return count.intValue() ?: 0
    }

    static def createInstance(Job job) {
        def instance = new JobInstance(job: job, startedAt: Calendar.getInstance().getTime())
        instance.pending("")
        return instance
    }

}