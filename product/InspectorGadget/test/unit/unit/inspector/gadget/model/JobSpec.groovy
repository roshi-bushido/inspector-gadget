package unit.inspector.gadget.model
import grails.plugin.spock.UnitSpec
import grails.test.mixin.TestFor
import inspector.gadget.Application
import inspector.gadget.job.Configuration
import inspector.gadget.job.Job
import model.execution.CronExecutionStrategy
import model.execution.IntervalExecutionStrategy
import spock.lang.Ignore

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

@TestFor(Job)
class JobSpec extends UnitSpec {

    def "should fail due to application being null" () {
        given:
            def config = mockDomain(Configuration)
            def job = new Job(name: "job", maximumDuration: 10, executionInterval: 5, enabled: true, configuration: config, escalationSteps: "empty")

        when:
            def wasSuccessful = job.validate()

        then:
            job.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to escalation steps being null" () {
        given:
            def config = mockDomain(Configuration)
            def application = mockDomain(Application)
            def job = new Job(name: "job", maximumDuration: 10, executionInterval: 5, enabled: true, configuration: config, application: application, escalationSteps: null)

        when:
            def wasSuccessful = job.validate()

        then:
            job.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to configuration being null" () {
        given:
            def application = mockDomain(Application)
            def job = new Job(name: "job", maximumDuration: 10, executionInterval: 5, enabled: true, application: application, escalationSteps: "emtpy")

        when:
            def wasSuccessful = job.validate()

        then:
            job.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should not fail due to constraints validations" () {
        given:
            def config = mockDomain(Configuration)
            def application = mockDomain(Application)
            def job = new Job(name: "job", errorDurationThreshold: 10, warningDurationThreshold: 10, executionInterval: 5, enabled: true, application: application, configuration: config, escalationSteps: "this is a sample")

        when:
            def wasSuccessful = job.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }

    def "should fail due to empty regex when using cron expression" () {
        given:
            def config = mockDomain(Configuration)
            def application = mockDomain(Application)
            def job = new Job(name: "job", errorDurationThreshold: 10, warningDurationThreshold: 10, executionInterval: 5, enabled: true, application: application, configuration: config, escalationSteps: "this is a sample", usesRegExp: true)

        when:
            def wasSuccessful = job.validate()

        then:
            assertThat(wasSuccessful, equalTo(false))
    }

    //TODO: see how to avoid de SystemConfiguration exception within the static initialization of the class
    @Ignore
    def "should get a time interval execution strategy" () {
        given:
            def job = new Job(name: "job")

        when:
            def strategy = job.getExecutionStrategy()

        then:
            assertThat( (strategy instanceof IntervalExecutionStrategy), equalTo(true))
    }

    def "should get a regular expression execution strategy" () {
        given:
            def job = new Job(name: "job", usesRegExp: true)

        when:
            def strategy = job.getExecutionStrategy()

        then:
            assertThat( (strategy instanceof CronExecutionStrategy), equalTo(true))
    }

}
