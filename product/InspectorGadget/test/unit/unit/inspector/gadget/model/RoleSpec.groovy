package unit.inspector.gadget.model
import grails.plugin.spock.UnitSpec
import grails.test.mixin.TestFor
import inspector.gadget.user.Role

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

@TestFor(Role)
class RoleSpec extends UnitSpec {

    def "should fail due to validations" () {
        given:
            def role = new Role(authority: "")

        when:
            def wasSuccessful = role.validate()

        then:
            role.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should not fail due to validations" () {
        given:
            def role = new Role(authority: "user")

        when:
            def wasSuccessful = role.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }

}
