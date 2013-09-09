package inspector.gadget.util

import org.joda.time.DateTime
import org.joda.time.Interval
import org.quartz.CronExpression

import java.text.ParseException
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

    static def firstDayOfMonth(Integer monthNumber) {
        if (monthNumber < 1 && monthNumber > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12. Cannot be ${monthNumber}")
        }

        def currentYear = Calendar.getInstance().get(Calendar.YEAR)
        return simpleDateFormat.parse("01-${monthNumber}-${currentYear} 00:00:00")
    }

    static def lastDayOfMonth(Integer monthNumber) {
        if (monthNumber < 1 && monthNumber > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12. Cannot be ${monthNumber}")
        }

        def calendar = new GregorianCalendar()
        calendar.set(Calendar.MONTH, monthNumber -1)
        def currentYear = Calendar.getInstance().get(Calendar.YEAR)
        def dayNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return simpleDateFormat.parse("${dayNumber}-${monthNumber}-${currentYear} 23:59:59")
    }

    static def substractDaysTo(Integer days, Date sourceDate) {
        DateTime dateTime = new DateTime(sourceDate.getTime())
        return dateTime.minusDays(days).toDate()
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

    static def validateCronExpression(String cronExpression) {
        try {
            new CronExpression(cronExpression)
            return true
        } catch (ParseException exception) {
            return false
        }
    }

    static java.util.Date toJavaDate(java.sql.Timestamp timestamp) {
        Calendar calendar = new GregorianCalendar()
        calendar.setTimeInMillis(timestamp.getTime())
        return calendar.getTime()
    }
}