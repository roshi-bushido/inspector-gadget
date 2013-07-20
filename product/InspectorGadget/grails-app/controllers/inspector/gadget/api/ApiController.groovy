package inspector.gadget.api

import dto.api.*
import inspector.gadget.Application
import inspector.gadget.BaseController
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.job.JobInstanceEvent

abstract class ApiController extends BaseController {
    public static def String ok = "success"

    def beforeInterceptor = {
        response.setContentType("application/json")
    }

    protected int getPageSize() {
        String valueAsString = params?.size ?: "10"

        if (valueAsString.isNumber()) {
            return Integer.valueOf(valueAsString)
        } else {
            return 10
        }
    }

    protected void renderError(ErrorDTO error, String detail) {
        error.detail = detail
        renderJSON(error)
    }

    protected JobInstanceDTO toApiDTO(JobInstance jobInstance) {
        return new JobInstanceDTO(
                id: jobInstance?.id,
                jobId: jobInstance?.job?.id,
                status: jobInstance?.status,
                name: jobInstance?.job?.name,
                description: jobInstance?.description
        )
    }

    protected JobInstanceEventDTO toApiDTO(JobInstanceEvent event) {
        return new JobInstanceEventDTO(
                id: event?.id,
                jobId: event?.instance?.job?.id,
                jobInstanceId: event?.instance?.id,
                status: event?.status,
                description: event?.description
        )
    }

    protected JobDTO toApiDTO(Job instance) {
        return new JobDTO(
                id: instance?.id,
                name: instance?.name,
                enabled: instance?.enabled
        )
    }

    protected ApplicationDTO toApiDTO(Application application) {
        return new ApplicationDTO(id:  application.id, name: application.name)
    }

    protected Collection<?> toCollectionDTO(Collection<?> objects) {
        def list = new ArrayList<?>();
        objects.each { object -> list.add(toApiDTO(object)) }
        return list;
    }

}
