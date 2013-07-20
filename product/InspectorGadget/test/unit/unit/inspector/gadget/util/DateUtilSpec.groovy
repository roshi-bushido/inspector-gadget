package unit.inspector.gadget.util
import grails.plugin.spock.UnitSpec
import inspector.gadget.util.DateUtil
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
class DateUtilSpec extends UnitSpec {
    @Shared def f = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss")

    def "should calculate first day of the current month"() {
        when: "calculating the first day of the current month"
            def firstDayOfMonth = DateUtil.firstDayOfCurrentMonth()
        then: "the day should be 1 and the month should be the current one"
            Calendar c = new GregorianCalendar()
            c.setTime(firstDayOfMonth)

            assertThat(Calendar.getInstance().get(Calendar.MONTH), equalTo(c.get(Calendar.MONTH)))
            assertThat(Calendar.getInstance().get(Calendar.YEAR), equalTo(c.get(Calendar.YEAR)))
            assertThat(1, equalTo(c.get(Calendar.DAY_OF_MONTH)))
    }

    @Unroll
    def "should get the difference between #firstDate and #secondDate" () {
        given : "two different dates and that #firstDate >= #secondDate "
        when: "i calculate the date difference"
           def diffInSeconds = DateUtil.diffInSeconds(firstDate, secondDate)
           def diffInMillis = DateUtil.diffInMillis(firstDate, secondDate)

        then: "the difference should be exactly #difference"
            assertThat(diffInSeconds, equalTo(difference))
            assertThat(diffInMillis, equalTo(difference * 1000))

        where:
            firstDate                       | secondDate                      || difference
            f.parse("01/01/2013 01:01:00")  | f.parse("01/01/2013 01:01:10")  || 10
            f.parse("01/01/2013 01:01:59")  | f.parse("01/01/2013 01:02:01")  || 2
            f.parse("01/01/2013 01:01:00")  | f.parse("01/01/2013 01:02:00")  || 60
            f.parse("01/01/2013 01:01:00")  | f.parse("01/01/2013 02:01:00")  || 60*60
            f.parse("01/01/2013 00:00:00")  | f.parse("02/01/2013 00:00:00")  || 60*60*24
    }

}
