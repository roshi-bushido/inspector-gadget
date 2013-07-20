package functional.inspector.gadget.api
import groovyx.net.http.RESTClient
import net.sf.json.JSONObject

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.junit.Assert.assertThat
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class InformationApiSpec extends ApiSpec {

    def "should get the current version from the api" () {
        given:
            RESTClient client = getClientForEndpoint("version")
        when:
            def response = client.get(contentType: JSON_CONTENT_TYPE)
        then:
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
    }

    def "should get the current status from the api" () {
         given:
             RESTClient client = getClientForEndpoint("status")
         when:
             def response = client.get(contentType: JSON_CONTENT_TYPE)
             JSONObject object = response.data
         then:
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("server-status"), equalTo("ok"))
            assertThat(object.get("mail-relay-status"), equalTo("ok"))
     }
}

