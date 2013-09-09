package inspector.gadget.job

import inspector.gadget.Application
import model.JobCriticity
import model.execution.CronExecutionStrategy
import model.execution.IntervalExecutionStrategy
import org.quartz.CronExpression

class Job {
    transient def notificationService
    String name
    String description
    Long warningDurationThreshold
    Long errorDurationThreshold
    Long executionInterval

    String executionRegExp
    Boolean usesRegExp = Boolean.FALSE

    Boolean enabled = false
    Configuration configuration
    Application application
    JobCriticity criticity = JobCriticity.MEDIUM

    String escalationSteps = "None"
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name(nullable: false, blank: false, unique: true)
        description(nullable: true)
        errorDurationThreshold(nullable: false, min: 0l, max: Long.MAX_VALUE)
        warningDurationThreshold(nullable: false, min: 0l, max: Long.MAX_VALUE)
        enabled(nullable: false)
        application(nullable: false)
        configuration(nullable: false)
        criticity(nullable: false, blank: false)
        executionInterval(nullable: true, min: 0l, max: Long.MAX_VALUE)
        escalationSteps(nullable: false, blank: false, minSize: 0, widget: 'textarea')
        usesRegExp(nullable: false)
        executionRegExp(nullable: true, dateCron: true, maxSize: 100, minSize: 0, validator:  { val, obj ->
            if (obj.usesRegExp) {
                return (obj.executionRegExp != null && !obj.executionRegExp?.isEmpty())
            } else {
                return obj.executionInterval > 0
            }
        })
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
        usesRegExp(column: 'A_USES_REG_EXP')
        executionRegExp(column: 'A_EXECUTION_REG_EXP')
        escalationSteps(column: 'A_ESCALATION_STEPS', type: 'text')
    }

    String toString() {
        return name
    }

    def shouldHaveStarted() {
        return getExecutionStrategy().shouldHaveStarted()
    }

    def shouldHaveFinish(JobInstance instance) {
        return getExecutionStrategy().shouldHaveFinished(instance)
    }

    Boolean hasInstances() {
        return JobInstance.countByJob(this) > 0
    }

    def beforeInsert() {
        if ( usesRegExp ) {
            executionInterval = 0l
        }
    }

    def afterInsert() {
        notificationService.fireJobCreationNotification(this)
    }

    def didNotStartNewInstance() {
        def now = Calendar.getInstance().getTime()
        def failedInstance = new JobInstance(job: this, startedAt: now, endedAt: now)
        failedInstance.error("Job (id=${this.id}) did not start a new instance")

        notificationService.fireNoStartupNotification(this)
    }

    def getExecutionRegExAsCronExpression() {
        return new CronExpression(this.executionRegExp)
    }

    def getExecutionStrategy() {
        if ( usesRegExp ) {
             return new CronExecutionStrategy(this)
        } else {
            return new IntervalExecutionStrategy(this)
        }
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