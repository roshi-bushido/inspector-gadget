package unit.inspector.gadget

import dto.view.dashboard.MonthlyJobSanity
import grails.plugin.spock.UnitSpec
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
class MonthlyJobSanitySpec extends UnitSpec {

    @Shared
    def f = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")

    def "should create a monthly job sanity for current month"() {
        given:
            def dayOnMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        when:
            def monthly = MonthlyJobSanity.forCurrentMonth()
        then:
            assertThat(dayOnMonth, equalTo(monthly.dailyJobSanity.size()))
    }

    @Unroll
    def "should create a monthly job sanity for a given month (#month)"() {
        when:
            def monthly = MonthlyJobSanity.forMonth(month)
        then:
            assertThat(days, equalTo(monthly.dailyJobSanity.size()))
        where:
            month  || days
             1     || 31
             2     || 28
             3     || 31
             4     || 30
             5     || 31
             6     || 30
             7     || 31
             8     || 31
             9     || 30
             10    || 31
             11    || 30
             12    || 31

    }
}
