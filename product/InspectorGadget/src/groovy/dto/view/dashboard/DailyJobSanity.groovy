package dto.view.dashboard

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class DailyJobSanity extends JobSanity {
    Date day

    DailyJobSanity() {
        this.error = 0
        this.warning = 0
        this.pending = 0
        this.success = 0
    }

    DailyJobSanity(Date day) {
        this.day  = day
        this.error = 0
        this.warning = 0
        this.pending = 0
        this.success = 0
    }

    void addError() {
        error++
    }

    void addWarning() {
        warning++
    }

    void addSuccess() {
        success++
    }

    void addPending() {
        pending++
    }
}
