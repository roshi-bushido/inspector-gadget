package unit.inspector.gadget.model
import grails.plugin.spock.UnitSpec
import grails.test.mixin.TestFor
import inspector.gadget.user.User

import static org.hamcrest.Matchers.equalTo
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(User)
class UserSpec extends UnitSpec {

    def "should pass because it' still pending to be done"() {
        assertThat(true, equalTo(true))
    }

}
