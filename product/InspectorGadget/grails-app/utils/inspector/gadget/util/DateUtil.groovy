package inspector.gadget.util

import org.joda.time.Interval

import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class DateUtil {
    static def simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss")

    static def firstDayOfCurrentMonth() {
        def currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        def currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return simpleDateFormat.parse("01-${currentMonth}-${currentYear} 00:00:00")
    }

    static def diffInSeconds(Date from, Date to) {
        if (from != null && to != null) {
            Interval dateInterval = new Interval(from?.getTime(), to?.getTime())
            return dateInterval.toDuration().getStandardSeconds().intValue()
        } else {
            return -1
        }
    }

    static def diffInMillis(Date from, Date to) {
        if (from != null && to != null) {
            Interval dateInterval = new Interval(from?.getTime(), to?.getTime())
            return dateInterval.toDuration().getMillis().intValue()
        } else {
            return -1
        }
    }

    static def toMillis(Integer seconds) {
        return seconds * 1000
    }

    static def toMillis(Long seconds) {
        return seconds * 1000
    }

}