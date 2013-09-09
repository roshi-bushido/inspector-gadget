package model.execution

import groovy.util.logging.Log
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.util.DateUtil
import org.quartz.CronExpression

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
@Log
class CronExecutionStrategy extends JobExecutionStrategy {
    CronExecutionStrategy(Job job) {
        super(job)
    }

    Boolean shouldHaveStarted() {
        log.info("CronExecutionStrategy - Procesing Job (id=${getJob()?.getId()})....")

        CronExpression cronExpression = getJob().getExecutionRegExAsCronExpression()
        JobInstance lastInstance = JobInstance.findLastFrom(getJob())

        def lastRun = getJob().lastUpdated

        if (lastInstance != null) {
            log.info("Job (id=${getJob()?.getId()}) has last a last instance with id=${lastInstance?.getId()}.")
            lastRun = lastInstance?.endedAt ?: lastInstance?.startedAt
        }

        lastRun = DateUtil.toJavaDate(lastRun)
        Date nextValidRun = cronExpression.getNextValidTimeAfter(lastRun)
        def currentTime = Calendar.getInstance().getTime()

        log.info("Last run value is: ${lastRun}")
        log.info("Next run value is: ${nextValidRun}")
        log.info("Current time is: ${currentTime}")

        if ( nextValidRun.before(currentTime)) {
            log.info("CronExecutionStrategy - Procesing Job (id=${getJob()?.getId()}) returns true")
            return Boolean.TRUE
        } else {
            log.info("CronExecutionStrategy - Procesing Job (id=${getJob()?.getId()}) returns false")
            return Boolean.FALSE
        }
    }

}
