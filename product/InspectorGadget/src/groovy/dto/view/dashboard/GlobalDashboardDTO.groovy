package dto.view.dashboard

import dto.view.ApplicationDTO
import dto.view.JobInstanceDTO

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class GlobalDashboardDTO implements Serializable {
    Collection<JobInstanceDTO> lastFailures = new ArrayList<JobInstanceDTO>()
    Collection<ApplicationJobsDTO> applicationJobs = new ArrayList<ApplicationJobsDTO>()

    public void addJobFailure(JobInstanceDTO instanceDTO) {
        if (instanceDTO != null) {
            lastFailures.add(instanceDTO)
        }
    }

    public void setJobCountFor(ApplicationDTO applicationDTO, Integer count, Integer failures) {
        this.applicationJobs.add(new ApplicationJobsDTO(application: applicationDTO, jobCount: count, failuresCount: failures))
    }
}
