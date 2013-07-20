package dto.view

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class JobInstanceDTO implements Serializable {
    Long id
    Long jobId
    Long applicationId

    String jobName
    String applicationName
    String reason
    Date endDate

}
