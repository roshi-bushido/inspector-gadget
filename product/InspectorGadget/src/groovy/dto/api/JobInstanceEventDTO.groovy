package dto.api

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobInstanceEventDTO implements Serializable {
    String id
    String jobId
    String jobInstanceId
    String status
    String description
}
