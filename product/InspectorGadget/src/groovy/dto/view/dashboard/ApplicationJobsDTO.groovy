package dto.view.dashboard

import dto.view.ApplicationDTO

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class ApplicationJobsDTO implements Serializable {
    ApplicationDTO application
    Integer jobCount
    Integer failuresCount
}
