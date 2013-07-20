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
class ApplicationApiSpec extends ApiSpec {

    def "should list all applications from the api" () {
        given:
            RESTClient client = getClientForEndpoint("apps/list")
        when:
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            def objectList = response.data
        then:
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(objectList, notNullValue())
            assertThat(objectList.isEmpty(), equalTo(false))
            for (JSONObject object : objectList) {
                assertThat(object, notNullValue())
                assertThat(object.get("name"), notNullValue())
                assertThat(object.get("id"), notNullValue())
            }
    }

    def "should get an existing application by id" () {
        given: "the id of an existing application"
            RESTClient client = getClientForEndpoint("apps/show/1")

        when: "i get the application by id"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return the application"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("name"), equalTo("DeAutos"))
            assertThat(object.get("id"), equalTo(1))
    }

    def "should get an error due to non existing application by id" () {
        given: "the id of an non existing application"
            RESTClient client = getClientForEndpoint("apps/show/9999")

        when: "i get the application by id"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error code"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E03"))
            assertThat(object.get("message"), equalTo("invalid-id"))
    }

    def "should get an error due to non number application by id" () {
        given: "the id of an non existing application"
            RESTClient client = getClientForEndpoint("apps/show/1lk")

        when: "i get the application by id"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error code"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E01"))
            assertThat(object.get("message"), equalTo("null-param"))
    }

}