package unit.inspector.gadget.model
import grails.plugin.spock.UnitSpec
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import model.status.JobStatus

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

@Mock(Job)
@TestFor(JobInstance)
class JobInstanceSpec extends UnitSpec {

    def "should fail due to job parent being null" () {
        given:
            def instance = new JobInstance(startedAt: new Date(), endedAt: new Date(), status: JobStatus.PENDING)

        when:
            def wasSuccessful = instance.validate()

        then:
            instance.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should fail due to status not valid" () {
        given:
            def instance = new JobInstance(startedAt: new Date(), endedAt: new Date(), status: "sarasa", job: mockDomain(Job))

        when:
            def wasSuccessful = instance.validate()

        then:
            instance.errors.allErrors.each { println it }
            assertThat(wasSuccessful, equalTo(false))
    }

    def "should not fail due to constraints validations" () {
            given:
            def calendar = Calendar.getInstance()
            def startDate = calendar.getTime()
            calendar.add(Calendar.HOUR_OF_DAY, +1)
            def endDate = calendar.getTime()
            def instance = new JobInstance(startedAt: startDate, endedAt: endDate, status: JobStatus.PENDING, job: mockDomain(Job))

        when:
            def wasSuccessful = instance.validate()
        then:
            assertThat(wasSuccessful, equalTo(true))
    }
}
