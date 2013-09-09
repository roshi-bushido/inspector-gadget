package model.execution

import groovy.util.logging.Log
import inspector.gadget.SystemConfiguration
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.util.DateUtil

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
@Log
class IntervalExecutionStrategy extends JobExecutionStrategy {
    private final def OFFSET_IN_MILLIS = SystemConfiguration.findByKey("config.jobs.InstanceStartup").valueAsInteger()

    IntervalExecutionStrategy(Job job) {
        super(job)
    }

    Boolean shouldHaveStarted() {
        log.info("IntervalExecutionStrategy - Procesing Job (id=${getJob()?.getId()})....")
        JobInstance lastInstance = JobInstance.findLastFrom(getJob())
        def currentTimeInMilliSeconds = Calendar.getInstance().getTime().getTime()
        def finishedTimeInMilliSeconds = getJob().lastUpdated.getTime()

        if (lastInstance != null) {
            log.info("Job (id=${getJob()?.getId()}) has last a last instance with id=${lastInstance?.getId()}.")
            finishedTimeInMilliSeconds = lastInstance?.endedAt?.getTime() ?: 0
        }

        def nextRun = finishedTimeInMilliSeconds + DateUtil.toMillis(getJob().executionInterval) + DateUtil.toMillis(OFFSET_IN_MILLIS)

        if (currentTimeInMilliSeconds > nextRun) {
            return Boolean.TRUE
        }
        return Boolean.FALSE
    }
}
