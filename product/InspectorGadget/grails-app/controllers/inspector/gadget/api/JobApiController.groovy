package inspector.gadget.api

import error.ErrorCode
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.job.JobInstanceEvent
import model.status.JobStatus

class JobApiController extends ApiController {
    static allowedMethods = [
            startJobInstance: 'POST',
            finishJobInstance: 'POST',
            crashJobInstance: 'POST',
            list: 'GET',
            findJobByName: 'GET',
            listJobInstances: 'GET',
            addSuccessEvent: 'POST',
            addErrorEvent: 'POST'
    ]

    private def getValidatedJobOrRenderError() {
        def jobIdAsString = params.jobId
        if (jobIdAsString != null && !jobIdAsString.isEmpty() && jobIdAsString.isInteger()) {
            Job job = Job.findById(jobIdAsString.toLong())
            if (job != null) {
                return job
            } else {
                renderError(ErrorCode.E02.toDTO(), "No such Job with id " + jobIdAsString)
            }
        } else {
            renderError(ErrorCode.E01.toDTO(), "Job id is null or not an integer. Value is " + jobIdAsString)
        }
    }

    private def getValidatedJobInstanceOrRenderError() {
        def jobInstanceIdAsString = params.jobInstanceId
        if (jobInstanceIdAsString != null && !jobInstanceIdAsString.isEmpty() && jobInstanceIdAsString.isInteger()) {
            JobInstance instance = JobInstance.findById(jobInstanceIdAsString.toLong())
            if (instance != null) {
                return instance
            } else {
                renderError(ErrorCode.E02.toDTO(), "No such Job Instance with id " + jobInstanceIdAsString)
            }
        } else {
            renderError(ErrorCode.E01.toDTO(), "Job instance id is null or not an integer. Value is " + jobInstanceIdAsString)
        }
    }

    def listEventsFromInstance = {
        def instance = getValidatedJobInstanceOrRenderError()
        if (instance) {
            renderJSON(toCollectionDTO(instance.events))
        }
    }

    def addErrorEvent = {
        def message = params?.message ?: "event error without reason"
        def instance = getValidatedJobInstanceOrRenderError()
        if (instance) {
            def event = new JobInstanceEvent(instance: instance, status: JobStatus.ERROR, description: message)
            event.save(failOnError: true, flush: true)
            renderJSON(toApiDTO(event))
        }
    }

    def addSuccessEvent = {
        def message = params?.message ?: "ok"
        def instance = getValidatedJobInstanceOrRenderError()
        if (instance) {
            def event = new JobInstanceEvent(instance: instance, status: JobStatus.OK, description: message)
            event.save(failOnError: true, flush: true)
            renderJSON(toApiDTO(event))
        }
    }

    def startJobInstance = {
        def job = getValidatedJobOrRenderError()
        if (job != null) {
            def newInstance = JobInstance.createInstance(job)
            renderJSON(toApiDTO(newInstance))
        }
    }

    def finishJobInstance = {
        def instance = getValidatedJobInstanceOrRenderError()
        if (instance != null) {
            instance.saveResults()
            renderJSON(toApiDTO(instance))
        }
    }

    def crashJobInstance = {
        def reason = params?.trace ?: "Instance crashed without reason"
        def instance = getValidatedJobInstanceOrRenderError()
        if (instance != null) {
            instance.crashed(reason)
            renderJSON(toApiDTO(instance))
        }
    }

    def listJobs = {
        def jobs = Job.list()
        renderJSON(toCollectionDTO(jobs))
    }

    def findJobByName = {
        def jobName = params?.jobName

        if (jobName != null && !jobName?.isEmpty() && !jobName.equals("null")) {
            def job = Job.findByNameIlike(jobName)
            if ( job != null ) {
                renderJSON(toApiDTO(job))
            } else {
                renderError(ErrorCode.E02.toDTO(), "No such Job with name " + jobName)
            }
        } else {
            renderError(ErrorCode.E02.toDTO(), "No such Job with name " + jobName)
        }
    }

    def listJobInstances = {
        def job = getValidatedJobOrRenderError()
        if (job != null) {
            def instances = JobInstance.findAllByJob(job)
            renderJSON(toCollectionDTO(instances))
        }
    }
}