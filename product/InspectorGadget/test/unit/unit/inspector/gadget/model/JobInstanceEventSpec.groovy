package unit.inspector.gadget.model

import grails.plugin.spock.UnitSpec
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import inspector.gadget.job.JobInstance
import inspector.gadget.job.JobInstanceEvent
import model.status.JobStatus

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
@Mock(JobInstance)
@TestFor(JobInstanceEvent)
class JobInstanceEventSpec extends UnitSpec {

    def "should not fail due to model validations" () {
        given:
            def jobInstance = mockDomain(JobInstance)
            def instance = new JobInstanceEvent(status: JobStatus.PENDING, description: "sample", instance: jobInstance, dateCreated: new Date())

        when:
            def wasSuccessful = instance.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }

    def "should fail due to job instance being null" () {
        given:
            def instance = new JobInstanceEvent(status: JobStatus.PENDING, description: "sample", dateCreated: new Date())

        when:
            def wasSuccessful = instance.validate()

        then:
            instance.errors.allErrors.each { println it }
            assertFalse wasSuccessful
    }

    def "should fail due to event status being null" () {
        given:
            def jobInstance = mockDomain(JobInstance)
            def instance = new JobInstanceEvent(description: "sample", instance: jobInstance, dateCreated: new Date())

        when:
            def wasSuccessful = instance.validate()

        then:
            instance.errors.allErrors.each { println it }
            assertFalse wasSuccessful
    }

    def "should fail due to create date being null" () {
        given:
            def jobInstance = mockDomain(JobInstance)
            def instance = new JobInstanceEvent(description: "sample", instance: jobInstance)

        when:
            def wasSuccessful = instance.validate()

        then:
            instance.errors.allErrors.each { println it }
            assertFalse wasSuccessful
    }
}
