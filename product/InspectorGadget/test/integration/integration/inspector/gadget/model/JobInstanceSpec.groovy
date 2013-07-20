package integration.inspector.gadget.model

import inspector.gadget.Application
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import integration.inspector.gadget.sql.DatasetInitializer
import model.status.JobStatus
import spock.lang.Specification
import spock.lang.Unroll

import static org.hamcrest.Matchers.*
import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobInstanceSpec extends Specification {

    def setupSpec() {
        println("Initializing dataset for JobInstanceSpec.....")
        DatasetInitializer.initializeDataset()
        println("dataset initialization complete.")
    }

    @Unroll
    def "should should get all instances by status (#status) from app(#appId)" () {
        given: "a list of job instances"
            def application = Application.findById(Long.valueOf(appId))

        when: "it fetch them by app and status"
            def count = JobInstance.countByApplicationAndJobStatus(application, status)

        then: "should get a collection of job instances from that app in that status"
            assertThat(expectedCount.intValue(), equalTo(count.intValue()))
        where:
            status              | appId || expectedCount
            JobStatus.PENDING   | 2     || 2
            JobStatus.WARNING   | 2     || 1
            JobStatus.ERROR     | 1     || 0
            JobStatus.OK        | 2     || 0
    }

    def "should be pending after creation" () {
        given: "a existing job"
            def job = Job.list().first()
            assertThat(job, notNullValue())
            assertThat(job.id, notNullValue())

        when: "a new instance is created"
            def instance = JobInstance.createInstance(job)

        then: "it fields should be instantiated ok"
            assertThat(instance, notNullValue())
            assertThat(instance.id, notNullValue())
            assertThat(instance.endedAt, nullValue())
            assertThat(instance.startedAt, notNullValue())
            assertThat(instance.status, equalTo(JobStatus.PENDING))
    }

    def "should be in error due to crashing" () {
        given: "a existing job and a new instance created ok"
            def reason = "Test crashing"
            def job = Job.list().first()
            assertThat(job, notNullValue())
            def instance = JobInstance.createInstance(job)

        when: "it crashes due to an error"
            assertThat(instance, notNullValue())
            instance.crashed(reason)

        then: "should have an ERROR status"
            assertThat(instance.description, equalTo(reason))
            assertThat(instance.endedAt, notNullValue())
            assertThat(instance.status, equalTo(JobStatus.ERROR))
    }

    def "should be in ok status due to ending correctly" () {
        given: "a existing job and a new instance created ok"
            def job = Job.list().first()
            assertThat(job, notNullValue())
            def instance = JobInstance.createInstance(job)
            assertThat(instance, notNullValue())
            assertThat(instance.startedAt, notNullValue())
            assertThat(instance.status, equalTo(JobStatus.PENDING))

        when: "it ends normally"
            instance.saveResults();

        then: "should have an ok status"
            assertThat(instance.endedAt, notNullValue())
            assertThat(instance.status, equalTo(JobStatus.OK))
    }
}
