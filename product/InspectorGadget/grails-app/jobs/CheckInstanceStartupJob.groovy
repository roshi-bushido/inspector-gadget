import grails.plugins.quartz.GrailsJobClassConstants
import groovy.util.logging.Log
import inspector.gadget.SystemConfiguration
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.util.DateUtil
import model.SystemConfigurationAwareness
import org.quartz.Scheduler
import org.quartz.impl.matchers.GroupMatcher

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
@Log
class CheckInstanceStartupJob implements SystemConfigurationAwareness {
    public static String KEY = "config.jobs.InstanceStartup"
    public static String NAME = "CheckInstanceStartupJob"

    private final def OFFSET_IN_MILLIS = SystemConfiguration.findByKey("config.jobs.InstanceStartup").valueAsInteger()

    Scheduler quartzScheduler

    static triggers ={}

    def execute() {
        log.info("running CheckInstanceStartupJobat ${Calendar.getInstance().getTime().getDateTimeString()}...")
        def startDate = Calendar.getInstance().getTime()
        log.info("Initializing CheckInstanceStartupJob at ${startDate.getDateTimeString()}...")

        Job.active.list().each { job ->
            JobInstance lastInstance = JobInstance.findLastFrom(job)

            if (lastInstance != null) {
                log.info("CheckInstanceStartupJob - Procesing JobInstance (id=${lastInstance?.getId()})....")
                def currentTimeInMilliSeconds = Calendar.getInstance().getTime().getTime()
                def finishedTimeInMilliSeconds = lastInstance?.endedAt?.getTime() ?: 0

                def nextRun = finishedTimeInMilliSeconds + DateUtil.toMillis(job.executionInterval) + DateUtil.toMillis(OFFSET_IN_MILLIS)

                if (currentTimeInMilliSeconds > nextRun) {
                    log.info("Instance did not start for job (id=${job.getId()}). Sending notification....")
                    job.didNotStartNewInstance()
                }
            } else {
                log.info("CheckInstanceStartupJob - Procesing Job (id=${job?.getId()})....")
                if (job?.lastUpdated != null) {
                    def currentTimeInMilliSeconds = Calendar.getInstance().getTime().getTime()
                    def finishedTimeInMilliSeconds = job.lastUpdated.getTime()
                    def nextRun = finishedTimeInMilliSeconds + DateUtil.toMillis(job.executionInterval) + DateUtil.toMillis(OFFSET_IN_MILLIS)

                    if (currentTimeInMilliSeconds > nextRun) {
                        log.info("Instance did not start for job (id=${job.getId()}). Sending notification....")
                        job.didNotStartNewInstance()
                    }
                }
            }
        }

        def endDate = Calendar.getInstance().getTime()
        log.info("CheckInstanceStartupJob at ${endDate.getDateTimeString()}...")
        log.info("CheckInstanceStartupJob took ${DateUtil.diffInSeconds(startDate, endDate)} seconds")
    }

    void propertyChanged(SystemConfiguration configuration) {
        if (configuration.getKey().toLowerCase().equals(KEY.toLowerCase())) {
            log.info("Property ${configuration.key} changed.")
            this.unRegister()
            this.register()
        }
    }

    void register() {
        def instanceStartupJobRepeatCount = SystemConfiguration.findByKey(CheckInstanceStartupJob.KEY).valueAsLong()
        CheckInstanceStartupJob.schedule(DateUtil.toMillis(instanceStartupJobRepeatCount), 1, null)
        log.info("Registering trigger for ${CheckInstanceStartupJob.NAME}...")
    }

    void unRegister() {
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(GrailsJobClassConstants.DEFAULT_GROUP))
        jobKeys.each { jobKey ->
            if ( CheckInstanceStartupJob.NAME.toLowerCase().equals(jobKey.getName()?.toLowerCase())) {
                log.info("Removing trigger ${jobKey.getName()}...")
                def triggerList = quartzScheduler.getTriggersOfJob(jobKey)
                triggerList.each { trigger ->
                    quartzScheduler.unscheduleJob(trigger.getKey())
                }
            }
        }
    }
}