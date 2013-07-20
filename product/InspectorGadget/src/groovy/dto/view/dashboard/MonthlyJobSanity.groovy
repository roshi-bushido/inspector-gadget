package dto.view.dashboard

import inspector.gadget.util.DateUtil

import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class MonthlyJobSanity {
    transient def simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy")

    // TreeMap implements a sorted map, dont change
    def dailyJobSanity = new TreeMap<String, DailyJobSanity>()
    def jobName

    public MonthlyJobSanity() {
        def currentDayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        def day = 1

        def tempCalendar = new GregorianCalendar()
        tempCalendar.setTime(DateUtil.firstDayOfCurrentMonth())

        while (day <= currentDayOfMonth) {
            dailyJobSanity.put(simpleDateFormat.format(tempCalendar.getTime()), new DailyJobSanity(tempCalendar.getTime()))
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1)
            day++
        }
    }

    public JobSanity getJobSanityFor(Date date) {
        String dateHash = simpleDateFormat.format(date)
        return dailyJobSanity.get(dateHash)
    }
}