package unit.inspector.gadget.model
import grails.plugin.spock.UnitSpec
import grails.test.mixin.TestFor
import inspector.gadget.SystemConfiguration

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
@TestFor(SystemConfiguration)
class SystemConfigurationSpec extends UnitSpec {

    def "should fail due to key being null"() {
        given:
            def configuration = new SystemConfiguration(value: "10")

        when:
            def wasSuccessful = configuration.validate()

        then:
            configuration.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to value being null"() {
        given:
            def configuration = new SystemConfiguration(key: "myKey")

        when:
            def wasSuccessful = configuration.validate()

        then:
            configuration.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should not fail due to domain validation"() {
        given:
            def configuration = new SystemConfiguration(key: "myKey", value: "10")

        when:
            def wasSuccessful = configuration.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }
}
