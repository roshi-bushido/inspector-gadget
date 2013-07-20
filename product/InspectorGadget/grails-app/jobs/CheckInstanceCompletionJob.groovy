import grails.plugins.quartz.GrailsJobClassConstants
import groovy.util.logging.Log
import inspector.gadget.SystemConfiguration
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.util.DateUtil
import model.SystemConfigurationAwareness
import model.status.JobStatus
import org.quartz.Scheduler
import org.quartz.impl.matchers.GroupMatcher

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
@Log
class CheckInstanceCompletionJob implements SystemConfigurationAwareness {
    public static String KEY = "config.jobs.InstanceCompletion"
    public static String NAME = "CheckInstanceCompletionJob"

    Scheduler quartzScheduler

    static triggers ={}

    def execute() {
        def startDate = Calendar.getInstance().getTime()
        log.info("Initializing CheckInstanceCompletionJob at ${startDate.getDateTimeString()}...")

        Job.active.list().each { job ->
            JobInstance.findAllByJobAndStatusLike(job, JobStatus.PENDING)?.each { lastInstance ->
                if (lastInstance?.shouldHaveFinish()) {
                    lastInstance?.error("Job Instance ${lastInstance?.id} never finish....")
                }
            }
        }
        def endDate = Calendar.getInstance().getTime()
        log.info("CheckInstanceCompletionJob at ${endDate.getDateTimeString()}...")
        log.info("CheckInstanceCompletionJob took ${DateUtil.diffInSeconds(startDate, endDate)} seconds")
    }

    void propertyChanged(SystemConfiguration configuration) {
        if (configuration.getKey().toLowerCase().equals(KEY.toLowerCase())) {
            log.info("Property ${configuration.key} changed.")
            this.unRegister()
            this.register()
        }
    }

    void register() {
        def repeatCount = SystemConfiguration.findByKey(CheckInstanceCompletionJob.KEY).valueAsLong()
        CheckInstanceCompletionJob.schedule(DateUtil.toMillis(repeatCount), 1, null)
        log.info("Registering trigger for ${CheckInstanceCompletionJob.NAME}...")
    }


    void unRegister() {
        def jobKeys = quartzScheduler.getJobKeys(GroupMatcher.jobGroupEquals(GrailsJobClassConstants.DEFAULT_GROUP))
        jobKeys.each { jobKey ->
            if ( CheckInstanceCompletionJob.NAME.toLowerCase().equals(jobKey.getName()?.toLowerCase())) {
                log.info("Removing trigger ${jobKey.getName()}...")
                def triggerList = quartzScheduler.getTriggersOfJob(jobKey)
                triggerList.each { trigger ->
                    quartzScheduler.unscheduleJob(trigger.getKey())
                }
            }
        }
    }
}
