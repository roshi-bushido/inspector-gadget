package integration.inspector.gadget.model.execution

import grails.plugin.spock.IntegrationSpec
import inspector.gadget.Application
import inspector.gadget.job.Configuration
import inspector.gadget.job.Job
import model.execution.CronExecutionStrategy

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class ExecutionStrategySpec extends IntegrationSpec {

    def "should return false for a job that is executing ok" () {
        given: "an existing job and a cron expression strategy"
        def application = Application.list().first()
        def configuration = Configuration.list().first()

        def job = new Job(name: "test",
                            usesRegExp: true,
                            executionRegExp: "0 0 0/1 1/1 * ? *",
                            errorDurationThreshold: 500,
                            warningDurationThreshold: 200,
                            application: application,
                            configuration: configuration
            ).save(failOnError: true)
        def strategy = new CronExecutionStrategy(job)

        when: "checked if job should have started"
            def shouldHaveStarted = strategy.shouldHaveStarted()

        then: "it should return false"
            assertThat(false, equalTo(shouldHaveStarted))
    }
}
