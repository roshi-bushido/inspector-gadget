package unit.inspector.gadget.model
import grails.plugin.spock.UnitSpec
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import inspector.gadget.user.Role
import inspector.gadget.user.User
import inspector.gadget.user.UserRole

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

@Mock(Role)
@TestFor(UserRole)
class UserRoleSpec extends UnitSpec {

    def "should fail due to user being null validation" () {
        given:
            def role = new UserRole(user: null, role: mockDomain(Role))

        when:
            def wasSuccessful = role.validate()

        then:
            role.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to role being null validation"() {
        given:
            def role = new UserRole(user: mockDomain(User), role: null)
        when:
            def wasSuccessful = role.validate()

        then:
            role.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should not fail due to validations"() {
        given:
            def role = new UserRole(user: mockDomain(User), role: mockDomain(Role))

        when:
            def wasSuccessful = role.validate()

        then:
            assertThat(wasSuccessful, equalTo(true))
    }
}
