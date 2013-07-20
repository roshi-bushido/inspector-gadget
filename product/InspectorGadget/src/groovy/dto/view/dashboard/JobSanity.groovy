package dto.view.dashboard

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobSanity implements Serializable {
    String jobName
    String jobId
    Integer error
    Integer warning
    Integer success
    Integer pending
}
