package unit.inspector.gadget.model

import grails.plugin.spock.UnitSpec
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.notification.Notification

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
@Mock([Job, JobInstance])
@TestFor(Notification)
class NotificationSpec extends UnitSpec {

    def "should fail due to job being null"() {
        given:
            def notification = new Notification(jobInstance: mockDomain(JobInstance), wasSent: true, errorMessage: "sarasa")

        when:
            def wasSuccessful = notification.validate()

        then:
            notification.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to was sent flag being null"() {
        given:
            def notification = new Notification(job: mockDomain(Job), jobInstance: mockDomain(JobInstance), errorMessage: "sarasa")

        when:
            def wasSuccessful = notification.validate()

        then:
            notification.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }


    def "should not fail due to domain validations with jobInstance"() {
        given:
            def notification = new Notification(job: mockDomain(Job), jobInstance: mockDomain(JobInstance), wasSent: true, errorMessage: "sarasa")

        when:
            def wasSuccessful = notification.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }

    def "should not fail due to domain validations without jobInstance"() {
        given:
            def notification = new Notification(job: mockDomain(Job), wasSent: true, errorMessage: "sarasa")

        when:
            def wasSuccessful = notification.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }

}
