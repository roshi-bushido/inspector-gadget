package unit.inspector.gadget.model

import grails.plugin.spock.UnitSpec
import grails.test.mixin.TestFor
import inspector.gadget.job.Configuration

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

@TestFor(Configuration)
class ConfigurationSpec extends UnitSpec {

    def "should fail due to name being null" () {
        given:
            def configuration = new Configuration(notificationEmail: "sarasa@agea.com.ar")

        when:
            def wasSuccessful = configuration.validate()

        then:
            configuration.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to notification email being null" () {
        given:
            def configuration = new Configuration(name: "sarasa@agea.com.ar")

        when:
            def wasSuccessful = configuration.validate()

        then:
            configuration.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to notification email not being an email" () {
        given:
            def configuration = new Configuration(name: "sarasa@agea")

        when:
            def wasSuccessful = configuration.validate()

        then:
            configuration.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should not fail due to constraints validations" () {
        given:
            def configuration = new Configuration(name: "configuration", notificationEmail: "sarasa@agae.com.ar", isNotificationEnabled: true)

        when:
            def wasSuccessful = configuration.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }
}
