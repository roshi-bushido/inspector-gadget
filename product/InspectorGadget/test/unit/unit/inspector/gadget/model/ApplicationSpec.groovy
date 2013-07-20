package unit.inspector.gadget.model
import grails.plugin.spock.UnitSpec
import grails.test.mixin.TestFor
import inspector.gadget.Application

import static org.junit.Assert.assertThat
import static org.hamcrest.Matchers.equalTo
/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Application)
class ApplicationSpec extends UnitSpec {

    def "should fail due to name being null" () {
        given:
            def application = new Application(code: "saras")

        when:
            def wasSuccessful = application.validate()

        then:
            application.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to code being null" () {
        given:
            def application = new Application(name: "saras")

        when:
            def wasSuccessful = application.validate()

        then:
            application.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should not fail due to domain validations" () {
        given:
            def application = new Application(name: "saras", code: "sarasa")

        when:
            def wasSuccessful = application.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }
}
