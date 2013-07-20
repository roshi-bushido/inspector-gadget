package integration.inspector.gadget.model
import grails.plugin.spock.IntegrationSpec
import inspector.gadget.Application

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.junit.Assert.assertThat
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class ApplicationSpec extends IntegrationSpec {

    def "should fetch all applications" () {
        when:
            def list = Application.list()
        then:
            assertThat(list.isEmpty(), equalTo(false))
            assertThat(list.size(), equalTo(5))

            list.each { application ->
                assertThat(application.id, notNullValue())
                assertThat(application.name, notNullValue())
                assertThat(application.code, notNullValue())
            }
    }

    def "should get an application by code" () {
        when:
            def application = Application.findAllByCode("deautos").first()
        then:
            assertThat(application, notNullValue())
            assertThat(application.name, equalTo("DeAutos"))
            assertThat(application.code, equalTo("deautos"))
    }
}
