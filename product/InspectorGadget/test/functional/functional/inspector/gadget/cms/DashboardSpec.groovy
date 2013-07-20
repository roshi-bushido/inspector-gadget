package functional.inspector.gadget.cms

import groovyx.net.http.RESTClient
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.junit.Assert.assertThat

class DashboardSpec extends CmsSpec {

    def "should get the global dashboard as JSON"() {
        given: "the dashboard format is JSON"
            RESTClient client = getClientForEndpoint("dashboard/application?applicationId=2&format=json")

        when: "fetching the dashboard"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            println(response)
            def dashboard = response.data

        then: "should return a json object with the dashboard data"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(dashboard, notNullValue())
            println(dashboard)
    }

}

