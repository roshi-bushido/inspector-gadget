package model.execution

import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.util.DateUtil

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
public abstract class JobExecutionStrategy {
    private Job job

    public JobExecutionStrategy(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job
    }

    abstract Boolean shouldHaveStarted()

    Boolean shouldHaveFinished(JobInstance instance) {
        def estimatedEndedTimeInMilliSeconds = instance.startedAt.getTime() + DateUtil.toMillis(instance.job.errorDurationThreshold)
        def nowInMilliSeconds = Calendar.getInstance().getTime().getTime()
        return (nowInMilliSeconds > estimatedEndedTimeInMilliSeconds)
    }
}