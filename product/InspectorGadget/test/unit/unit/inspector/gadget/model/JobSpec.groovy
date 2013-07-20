package unit.inspector.gadget.model
import grails.plugin.spock.UnitSpec
import grails.test.mixin.TestFor
import inspector.gadget.Application
import inspector.gadget.job.Configuration
import inspector.gadget.job.Job

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

@TestFor(Job)
class JobSpec extends UnitSpec {

    def "should fail due to application being null" () {
        given:
            def config = mockDomain(Configuration)
            def job = new Job(name: "job", maximumDuration: 10, executionInterval: 5, enabled: true, configuration: config)

        when:
            def wasSuccessful = job.validate()

        then:
            job.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to configuration being null" () {
        given:
            def job = new Job(name: "job", maximumDuration: 10, executionInterval: 5, enabled: true, application: new Application(id:1l))

        when:
            def wasSuccessful = job.validate()

        then:
            job.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should not fail due to constraints validations" () {
        given:
            def config = mockDomain(Configuration)
            def job = new Job(name: "job", errorDurationThreshold: 10, warningDurationThreshold: 10, executionInterval: 5, enabled: true, application: new Application(id:1l), configuration: config)

        when:
            def wasSuccessful = job.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }
}
