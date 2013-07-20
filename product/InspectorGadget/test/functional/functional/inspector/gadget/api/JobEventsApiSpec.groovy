package functional.inspector.gadget.api
import groovyx.net.http.RESTClient
import model.status.JobStatus
import net.sf.json.JSONObject

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.junit.Assert.assertThat
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobEventsApiSpec extends ApiSpec {

    def "should list all events for non existing instance from the api"() {
        given: "an non existing id for an instance"
            RESTClient client = getClientForEndpoint("jobs/19/events")

        when: "i fetch the job instance events"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error with the code description"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E02"))
            assertThat(object.get("message"), equalTo("object-not-found"))
    }

    def "should list all events for null id from the api"() {
        given: "a null id for an instance"
            RESTClient client = getClientForEndpoint("jobs/null/events")

        when: "i fetch the job instance events"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error with the code description"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E01"))
            assertThat(object.get("message"), equalTo("null-param"))
    }

    def "should list all events for non number id from the api"() {
        given: "a non number id for an instance"
            RESTClient client = getClientForEndpoint("jobs/nonNumber/events")

        when: "i fetch the job instance events"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error with the code description"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E01"))
            assertThat(object.get("message"), equalTo("null-param"))
    }

    def "should list all events for an existing instance from the api"() {
        given: "an existing job instance with events"
            RESTClient client = getClientForEndpoint("jobs/1/events")

        when: "i fetch the job instance events"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            def objectList = response.data

        then: "the api should return a collection of events"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(objectList, notNullValue())
            assertThat(objectList.isEmpty(), equalTo(false))
            for (JSONObject object : objectList) {
                assertThat(object, notNullValue())
                assertThat(object.get("id"), notNullValue())
                assertThat(object.get("jobId"), notNullValue())
                assertThat(object.get("jobInstanceId"), notNullValue())
                assertThat(object.get("status"), notNullValue())
                assertThat(object.get("description"), notNullValue())
            }
    }

    def "should add a success event for an existing event instance with a valid message" () {
        given: "a existing running event instance"
            def message = "this a sample message"
            def instanceId = 1
            RESTClient client = getClientForEndpoint("jobs/${instanceId}/events/success")

        when: "i add a success event to the instance"
            def response = client.post( contentType: JSON_CONTENT_TYPE, params: [message: message])
            JSONObject object = response.data

        then: "the api should return the created event without an error"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())

            assertThat(object.get("id"), notNullValue())
            assertThat(object.get("jobInstanceId"), equalTo(instanceId.toString()))
            assertThat(object.get("status"), equalTo(JobStatus.OK.toString()))
            assertThat(object.get("description"), equalTo(message))
    }

    def "should add a success event for an existing event instance with a invalid message" () {
        given: "a existing running event instance"
            def instanceId = 1
            RESTClient client = getClientForEndpoint("jobs/${instanceId}/events/success")

        when: "i add a success event to the instance"
            def response = client.post(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return the created event without an error"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())

            assertThat(object.get("id"), notNullValue())
            assertThat(object.get("jobInstanceId"), equalTo(instanceId.toString()))
            assertThat(object.get("status"), equalTo(JobStatus.OK.toString()))
            assertThat(object.get("description"), equalTo("ok"))
    }

    def "should add a error event for an existing event instance with a valid message" () {
        given: "a existing running event instance"
            def message = "this a sample message"
            def instanceId = 1
            RESTClient client = getClientForEndpoint("jobs/${instanceId}/events/error")

        when: "i add a error event to the instance"
            def response = client.post(contentType: JSON_CONTENT_TYPE, params: [message: message])
            JSONObject object = response.data

        then: "the api should return the created event without an error"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())

            assertThat(object.get("id"), notNullValue())
            assertThat(object.get("jobInstanceId"), equalTo(instanceId.toString()))
            assertThat(object.get("status"), equalTo(JobStatus.ERROR.toString()))
            assertThat(object.get("description"), equalTo(message))
    }

    def "should add a error event for an existing event instance with a invalid message" () {
        given: "a existing running event instance"
            def instanceId = 1
            RESTClient client = getClientForEndpoint("jobs/${instanceId}/events/error")

        when: "i add a error event to the instance"
            def response = client.post(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return the created event without an error"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())

            assertThat(object.get("id"), notNullValue())
            assertThat(object.get("jobInstanceId"), equalTo(instanceId.toString()))
            assertThat(object.get("status"), equalTo(JobStatus.ERROR.toString()))
            assertThat(object.get("description"), equalTo("event error without reason"))
    }
}
