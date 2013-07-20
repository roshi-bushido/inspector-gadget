package functional.inspector.gadget.api
import groovyx.net.http.RESTClient
import model.status.JobStatus
import net.sf.json.JSONObject

import static groovyx.net.http.ContentType.URLENC
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static org.junit.Assert.assertThat
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobInstanceSpec extends ApiSpec {

    def "should list all instances for non existing job from the api"() {
        given: "an non existing id for a job"
            RESTClient client = getClientForEndpoint("jobs/10000000/list")

        when: "i fetch the job instances"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error with the code description"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E02"))
            assertThat(object.get("message"), equalTo("object-not-found"))
    }

    def "should list all instances for null id job from the api"() {
        given: "a null id for a job"
            RESTClient client = getClientForEndpoint("jobs/null/list")

        when: "i fetch the job instances"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error with the code description"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E01"))
            assertThat(object.get("message"), equalTo("null-param"))
    }

    def "should list all instances for non number id from the api"() {
        given: "a non number id for a job"
            RESTClient client = getClientForEndpoint("jobs/nonNumber/list")

        when: "i fetch the job instances"
            def response = client.get(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return an error with the code description"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("code"), equalTo("E01"))
            assertThat(object.get("message"), equalTo("null-param"))
    }

    def "should list all events for an existing job from the api"() {
        given: "an existing job with instances"
            def jobId = "3"
            RESTClient client = getClientForEndpoint("jobs/${jobId}/list")

        when: "i fetch the job instances"
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
                assertThat(object.get("jobId"), equalTo(jobId))
                assertThat(object.get("name"), notNullValue())
                assertThat(object.get("status"), notNullValue())
            }
    }

    def "should start a new instance for a existing job" () {
        given: "an existing id for a job"
            def jobId = "3"
            RESTClient client = getClientForEndpoint("jobs/${jobId}/start")

        when: "i start a new instance for the job"
            def response = client.post(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return the recently created instance"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("id"), notNullValue())
            assertThat(object.get("jobId"), equalTo(jobId))
            assertThat(object.get("name"), notNullValue())
            assertThat(object.get("status"), equalTo(JobStatus.PENDING.toString()))
    }

    def "should finish a existing pending instance for a existing job" () {
        def jobId = "3"
        def pendingInstanceAsJSON = getClientForEndpoint("jobs/${jobId}/start").post(contentType: JSON_CONTENT_TYPE).data as JSONObject
        assertThat(pendingInstanceAsJSON, notNullValue())
        assertThat(pendingInstanceAsJSON.get("id"), notNullValue())
        assertThat(pendingInstanceAsJSON.get("status"), equalTo(JobStatus.PENDING.toString()))

        given: "an existing pending instance for a existing job"
            RESTClient client = getClientForEndpoint("jobs/${jobId}/${pendingInstanceAsJSON.get("id")}/finish")

        when: "i start a new instance for the job"
            def response = client.post(contentType: JSON_CONTENT_TYPE)
            JSONObject object = response.data

        then: "the api should return the recently created instance"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("id"), equalTo(pendingInstanceAsJSON.get("id")))
            assertThat(object.get("jobId"), equalTo(jobId))
            assertThat(object.get("name"), notNullValue())
            assertThat(object.get("status"), equalTo(JobStatus.OK.toString()))
    }

    def "should crash a existing pending instance for a existing job with a simple reason" () {
        def jobId = "3"
        def pendingInstanceAsJSON = getClientForEndpoint("jobs/${jobId}/start").post(contentType: JSON_CONTENT_TYPE).data as JSONObject
        assertThat(pendingInstanceAsJSON, notNullValue())
        assertThat(pendingInstanceAsJSON.get("id"), notNullValue())
        assertThat(pendingInstanceAsJSON.get("status"), equalTo(JobStatus.PENDING.toString()))

        given: "an existing pending instance for a existing job"
            RESTClient client = getClientForEndpoint("jobs/${jobId}/${pendingInstanceAsJSON.get("id")}/crash")

        when: "i start a new instance for the job with a simple reason"
            def reason = "sarasa"
            def response = client.post(requestContentType: URLENC, body: [trace: reason])
            JSONObject object = response.data

        then: "the api should return the recently created instance"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("id"), equalTo(pendingInstanceAsJSON.get("id")))
            assertThat(object.get("jobId"), equalTo(jobId))
            assertThat(object.get("name"), notNullValue())
            assertThat(object.get("description"), equalTo(reason))
            assertThat(object.get("status"), equalTo(JobStatus.ERROR.toString()))
    }

    def "should crash a existing pending instance for a existing job with a exception reason" () {
        def jobId = "3"
        def pendingInstanceAsJSON = getClientForEndpoint("jobs/${jobId}/start").post(contentType: JSON_CONTENT_TYPE).data as JSONObject
        assertThat(pendingInstanceAsJSON, notNullValue())
        assertThat(pendingInstanceAsJSON.get("id"), notNullValue())
        assertThat(pendingInstanceAsJSON.get("status"), equalTo(JobStatus.PENDING.toString()))

        given: "an existing pending instance for a existing job"
            RESTClient client = getClientForEndpoint("jobs/${jobId}/${pendingInstanceAsJSON.get("id")}/crash")

        when: "i start a new instance for the job with a complex reason"
            def reason = generateNullPointAsStringException()
            def response = client.post(requestContentType: URLENC, body: [trace: reason])
            JSONObject object = response.data

        then: "the api should return the recently created instance"
            assertThat(response.status, equalTo(OK_CODE))
            assertThat(response.contentType, equalTo(JSON_CONTENT_TYPE))
            assertThat(object, notNullValue())
            assertThat(object.get("id"), equalTo(pendingInstanceAsJSON.get("id")))
            assertThat(object.get("jobId"), equalTo(jobId))
            assertThat(object.get("name"), notNullValue())
            assertThat(object.get("status"), equalTo(JobStatus.ERROR.toString()))
    }



    private def generateNullPointAsStringException() {
        try {
            String sarasa = null
            sarasa.hashCode()
        } catch (NullPointerException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString()
        }
    }
}
