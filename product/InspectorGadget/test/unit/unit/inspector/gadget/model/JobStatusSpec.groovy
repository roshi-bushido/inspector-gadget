package unit.inspector.gadget.model

import grails.plugin.spock.UnitSpec
import model.status.JobStatus

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.nullValue

import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobStatusSpec extends UnitSpec {

    def "should find a job status by existing id"() {
        given: "the id of an existing job status"
            def jobStatusId = 1

        when: "is looked up by id"
            def jobStatus = JobStatus.byId(jobStatusId)

        then: "it should return the job status"
            assertThat(jobStatus.id, equalTo(jobStatusId))
            assertThat(jobStatus.value, equalTo("Pending"))
    }

    def "should find a job status by non existing id"() {
        given: "the id of a non existing job status"
            def jobStatusId = 1000

        when: "is looked up by id"
            def jobStatus = JobStatus.byId(jobStatusId)

        then: "it should return null"
            assertThat(jobStatus, nullValue())
    }
}