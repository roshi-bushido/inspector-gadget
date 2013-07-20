package inspector.gadget.job

import inspector.gadget.Application
import model.JobCriticity

class Job {
    transient def notificationService
    String name
    String description
    Long warningDurationThreshold
    Long errorDurationThreshold
    Long executionInterval
    Boolean enabled = false
    Configuration configuration
    Application application
    JobCriticity criticity = JobCriticity.MEDIUM

    Date dateCreated
    Date lastUpdated

    static constraints = {
        name(nullable: false, blank: false, unique: true)
        description(nullable: true)
        errorDurationThreshold(nullable: false, min: 0l, max: Long.MAX_VALUE)
        warningDurationThreshold(nullable: false, min: 0l, max: Long.MAX_VALUE)
        executionInterval(nullable: false, min: 0l, max: Long.MAX_VALUE)
        enabled(nullable: false)
        application(nullable: false)
        configuration(nullable: false)
        criticity(nullable: false, blank: false)
    }

    static mapping = {
        table 'T_JOB'
        version false
        id(column: 'P_ID')
        name(column: 'A_NAME')
        description(column: 'A_DESCRIPTION')
        warningDurationThreshold(column: 'A_WARN_DURATION_THRESHOLD_IN_SECONDS')
        errorDurationThreshold(column: 'A_ERROR_DURATION_THRESHOLD_IN_SECONDS')
        executionInterval(column: 'A_EXECUTION_INTERVAL_IN_SECONDS')
        enabled(column: 'A_IS_ENABLED')
        application(column: 'F_APPLICATION_ID')
        configuration(column: 'F_CONFIGURATION_ID')
        criticity(column: 'A_CRITICITY')
        dateCreated(column: 'A_CREATION_DATE')
        lastUpdated(column: 'A_LAST_UPDATE_DATE')
    }

    String toString() {
        return name
    }

    Boolean hasInstances() {
        return JobInstance.countByJob(this) > 0
    }

    def afterInsert() {
        notificationService.fireJobCreationNotification(this)
    }

    def didNotStartNewInstance() {
        notificationService.fireNoStartupNotification(this)
    }

    static namedQueries = {
        active {
            eq 'enabled', true
        }
        inactive {
            eq 'enabled', false
        }
    }
}