package integration.inspector.gadget.model
import grails.plugin.spock.IntegrationSpec
import inspector.gadget.Application
import inspector.gadget.job.Configuration
import inspector.gadget.job.Job
import integration.inspector.gadget.sql.DatasetInitializer

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.junit.Assert.assertThat
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobSpec extends IntegrationSpec {

    def setupSpec() {
        println("Initializing dataset for JobSpec.....")
        DatasetInitializer.initializeDataset()
        println("dataset initialization complete.")
    }

    def "should check if a job have instances" () {
        given: "an existing job"
            def job = Job.findById(jobId)

        when: "seeing if a job has instances"
            def hasInstances = job.hasInstances()

        then:
            assertThat(hasInstances, equalTo(result))

        where:
            jobId || result
            1     || false
            3     || true
            4     || true
    }


    def "should fetch all active jobs" () {
        when:
            def list = Job.active.list()
        then:
            assertThat(list.size(), equalTo(2))
            list.each { job -> assertThat(job.enabled, equalTo(true)) }
    }

    def "should fetch all inactive jobs" () {
        when:
            def list = Job.inactive.list()
        then:
            assertThat(list.size(), equalTo(2))
            list.each { job -> assertThat(job.enabled, equalTo(false)) }
    }

    def "should fetch all active jobs count" () {
        given:
            def initialActiveJobCount = Job.active.count()
            def job = createDefaultJobFor("shouldFetchAllActiveJobs-")
            job.enabled = true

        when:
            job.save(flush: true, failOnError: true)

        then:
            assertThat(job.id, notNullValue())
            def currentActiveJobCount = Job.active.count()
            assertThat(currentActiveJobCount, equalTo(initialActiveJobCount + 1))
    }

    def "should fetch all inactive jobs count" () {
        given:
            def initialInactiveJobCount = Job.inactive.count()
            def job = createDefaultJobFor("shouldFetchAllInactiveJobs-")
            job.enabled = false
        when:
            job.save(flush: true, failOnError: true)
        then:
            assertThat(job.id, notNullValue())
            def currentInactiveJobCount = Job.inactive.count()
            assertThat(currentInactiveJobCount, equalTo(initialInactiveJobCount + 1))
    }

    def "should fetch match all active and inactive jobs" () {
        when:
            def jobCount = Job.count()
            def jobActiveCount = Job.active.count()
            def jobInactiveCount = Job.inactive.count()
        then:
            assertThat(jobCount, equalTo(jobActiveCount + jobInactiveCount))
    }

    def "should create a job with a valid configuration" () {
        given: "a newly created job"
            def job = createDefaultJobFor("shouldCreateAJobWithAValidConfiguration-")

        when: "its saved"
            job = job.save(flush: true, failOnError: true)

        then: "its id should not be null"
            assertThat(job.id, notNullValue())
    }

    private Job createDefaultJobFor(String testName) {
        def configuration = Configuration.list().first()
        def job = new Job(
                configuration: configuration,
                name: testName.concat(String.valueOf(Calendar.getInstance().getTime().getTime())),
                warningDurationThreshold: 100,
                errorDurationThreshold: 1000,
                executionInterval: 100,
                enabled: true,
                application: Application.findById(1l)
        )
        return job
    }

}
