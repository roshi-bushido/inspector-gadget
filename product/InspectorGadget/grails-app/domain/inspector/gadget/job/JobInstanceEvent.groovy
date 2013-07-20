package inspector.gadget.job

import model.status.JobStatus

class JobInstanceEvent {
    private static final int MAX_STRING_LENGTH = 5000
    JobStatus status
    String description
    Date dateCreated

    static belongsTo = [instance: JobInstance]

    static constraints = {
        instance(nullable: false)
        dateCreated()
        status(nullable: false, blank: false)
        description(nullable: true, blank: true, maxSize: 500)
    }

    static mapping = {
        table 'T_JOB_INSTANCE_EVENT'
        version false
        id(column: 'P_ID')
        instance(column: 'F_JOB_INSTANCE_ID')
        dateCreated(column: 'A_CREATE_DATE')
        status(column: 'A_STATUS')
        description(column: 'A_DESCRIPTION', type: 'text')
    }

    def beforeInsert() {
        trimDescription()
    }

    private def trimDescription() {
        if (description?.length() > MAX_STRING_LENGTH) {
            this.description = this.description.substring(0, MAX_STRING_LENGTH - 1)
        }
    }

    def error(JobInstance instance, String description) {
        this.instance = instance
        this.status = JobStatus.ERROR
        this.description = description
        this.save(flush: true, failOnError: true)
    }

    def success(JobInstance instance, String description) {
        this.instance = instance
        this.status = JobStatus.OK
        this.description = description
        this.save(flush: true, failOnError: true)
    }
}