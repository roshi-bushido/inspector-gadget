package inspector.gadget.cms

import dto.view.ApplicationDTO
import dto.view.JobDTO
import dto.view.JobInstanceDTO
import grails.plugins.springsecurity.Secured
import inspector.gadget.Application
import inspector.gadget.BaseController
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance

@Secured(['ROLE_ADMIN', 'IS_AUTHENTICATED_FULLY'])
abstract class CmsController extends BaseController {

    protected JobInstanceDTO toViewDTO(JobInstance jobInstance) {
        return new JobInstanceDTO(
                id: jobInstance.id,
                jobId: jobInstance.job.id,
                applicationId: jobInstance.job.application.id,
                jobName: jobInstance.job.name,
                applicationName: jobInstance.job.application.name,
                endDate: jobInstance.endedAt,
                reason: jobInstance.description
        )
    }

    protected JobDTO toViewDTO(Job job) {
        return new JobDTO(id: job.id, name: job.name)
    }

    protected ApplicationDTO toViewDTO(Application application) {
        if (application != null) {
            return new ApplicationDTO(id: application.id, name: application.name)
        } else {
            return null
        }
    }

    protected Collection<?> toCollectionDTO(Collection<?> collection) {
        def list = new ArrayList<?>(collection.size())
        collection.each { object -> list.add(toViewDTO(object)) }
        return list
    }

}