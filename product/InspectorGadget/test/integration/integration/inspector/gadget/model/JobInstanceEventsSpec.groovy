package integration.inspector.gadget.model
import grails.plugin.spock.IntegrationSpec
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.job.JobInstanceEvent
import integration.inspector.gadget.sql.DatasetInitializer
import model.status.JobStatus

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobInstanceEventsSpec extends IntegrationSpec {

    def setupSpec() {
        println("Initializing dataset for JobInstanceEventsSpec.....")
        DatasetInitializer.initializeDataset()
        println("dataset initialization complete.")
    }

    def "should fetch all active jobs" () {
        when:
            def list = Job.active.list()
        then:
            assertThat(list.size(), equalTo(2))
            list.each { job -> assertThat(job.enabled, equalTo(true)) }
    }

    def "should fetch all events from an existing job instance" () {
        when: "an existing job instance with events is loaded"
            def jobInstance = JobInstance.findById(1l);

        then: "its event list should not be null or empty"
            assertThat(jobInstance, notNullValue())
            assertThat(jobInstance.events.isEmpty(), equalTo(false))
            jobInstance.events.each { event ->
                assertThat(event.id, notNullValue())
                assertThat(event.dateCreated, notNullValue())
                assertThat(event.status, notNullValue())
                assertThat(event.description, notNullValue())
            }
    }

    def "should create a new event for an active instance" () {
        given: "an existing job instance with events is loaded"
            def jobInstance = JobInstance.findById(1l);
            assertThat(jobInstance, notNullValue())
            def currentEvents = JobInstanceEvent.findAll().size()

        when: "a new event is created"
            def instanceEvent = new JobInstanceEvent(instance: jobInstance, status: JobStatus.ERROR, description: "sarasa")
            instanceEvent.save(flush: true, failOnError: true)

        then: "the event count should greater than before"
            def updatedCurrentEvents = JobInstanceEvent.findAll().size()
            assertThat(updatedCurrentEvents, equalTo(currentEvents+1))
    }
}

