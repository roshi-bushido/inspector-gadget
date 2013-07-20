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
class JobApiSpec extends ApiSpec {

    def "should list all jobs from the api"() {
        given:
            RESTClient client = getClientForEndpoint("jobs/list")

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
                assertThat(object.get("id"), notNullValue())
                assertThat(object.get("name"), notNullValue())
                assertThat(object.get("enabled"), notNullValue())
            }
    }

    def "should get an existing job by name" () {
        given: "the name of an existing job"
            RESTClient client = getClientForEndpoint("jobs/Republicacion")

        when: "i look for the job"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return the job"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("id"), equalTo("2"))
            assertThat(object.get("name"), equalTo("Republicacion"))
            assertThat(object.get("enabled"), equalTo(false))
    }

    def "should get an existing job by insenstive name" () {
        given: "the name in upper case of an existing job"
            RESTClient client = getClientForEndpoint("jobs/REPUBLICACION")

        when: "i look for the job"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return the job"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("id"), equalTo("2"))
            assertThat(object.get("name"), equalTo("Republicacion"))
            assertThat(object.get("enabled"), equalTo(false))
    }


    def "should get an error due to non existing application by name" () {
        given: "the name of non existing job"
            RESTClient client = getClientForEndpoint("jobs/sarasaasdasdasdasdasdas")

        when: "i look for the job"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error with the code description"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E02"))
            assertThat(object.get("message"), equalTo("object-not-found"))
    }

    def "should get an error due to null job name" () {
        given: "a null value for the name of existing job"
            RESTClient client = getClientForEndpoint("jobs/null")

        when: "i look for the job"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error with the code description"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E02"))
            assertThat(object.get("message"), equalTo("object-not-found"))
    }
}
