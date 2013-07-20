package dto.view.dashboard

import dto.view.ApplicationDTO

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class ApplicationSanity implements Serializable {
    Collection<JobSanity> jobSanityCollection = new ArrayList<JobSanity>()
    ApplicationDTO application

    def addJobSanity(JobSanity job) {
        jobSanityCollection.add(job)
    }

}
