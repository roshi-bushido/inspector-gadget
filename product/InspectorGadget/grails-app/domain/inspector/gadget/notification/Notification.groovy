package inspector.gadget.notification

import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance

class Notification {
    Boolean wasSent
    Job job
    JobInstance jobInstance
    String errorMessage
    Date dateCreated

    static constraints = {
        wasSent(nullable: false)
        errorMessage(nullable: true)
        job(nullable: false)
        jobInstance(nullable: true)
    }

    static mapping = {
        table 'T_NOTIFICATION'
        version false
        id(column: 'P_ID')
        job(column: 'F_JOB_ID')
        jobInstance(column: 'F_JOB_INSTANCE_ID')
        errorMessage(column: 'A_ERROR_MESSAGE')
        wasSent(column: 'A_WAS_SENT')
        dateCreated(column: 'A_CREATE_DATE')
    }

    def sentOk() {
        this.wasSent = true
        this.save(flush: true, failOnError: true);
        return this;
    }

    def notSentDueTo(String message) {
        this.wasSent = false
        this.errorMessage = message
        this.save(flush: true, failOnError: true)
        return this;
    }
}
