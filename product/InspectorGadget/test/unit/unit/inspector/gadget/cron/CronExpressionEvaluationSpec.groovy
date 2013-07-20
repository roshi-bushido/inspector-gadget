package unit.inspector.gadget.cron
import grails.plugin.spock.UnitSpec
import inspector.gadget.util.DateUtil
import org.quartz.CronExpression
import spock.lang.Shared
import spock.lang.Unroll

import java.text.SimpleDateFormat

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat


/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class CronExpressionEvaluationSpec extends UnitSpec {
    @Shared SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")

    @Unroll
    def "should get the next valid date from a cron expression and a startup date" () {
        given: "a valid cron expression and a valid startup date"
            CronExpression cron = new CronExpression("0 0/1 * * * ? *")
            def startupDate = formatter.parse("01-01-2013 ${time}")

        when: "calculating the time difference between the startup date and the next valid date"
            def nextFiringDate = cron.getNextValidTimeAfter(startupDate)
            def nextDate = nextFiringDate
            def differenceInSeconds = DateUtil.diffInSeconds(startupDate, nextDate)

            println("\n\n")
            println("#######################################################")
            println("Startup date is ${startupDate}")
            println("Next date is    ${nextDate}")
            println("Diff in seconds is    ${differenceInSeconds}")
            println("#######################################################")
        then: "the difference should be 60 seconds"
            assertThat(differenceInSeconds, equalTo(seconds))

        where:
            time        || seconds
            "00:00:00"  || 60
            "00:06:00"  || 60
            "00:59:00"  || 60
    }
}