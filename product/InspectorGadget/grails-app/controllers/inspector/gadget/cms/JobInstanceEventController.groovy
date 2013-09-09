package inspector.gadget.cms

import grails.plugins.springsecurity.Secured
import inspector.gadget.Application
import inspector.gadget.job.Job
import inspector.gadget.job.JobInstance
import inspector.gadget.job.JobInstanceEvent
import model.status.JobStatus

class JobInstanceEventController  extends CmsController {
    static scaffold = JobInstanceEvent

    @Secured('ROLE_USER')
    def index() {
        redirect action: 'panel', params: params
    }

    @Secured('ROLE_USER')
    def panel() {
        def max = (params.max)? Integer.valueOf(params.max) : 10
        def offset = (params.offset)? Integer.valueOf(params.offset) : 0

        def jobList = Job.list()
        def jobId = params.jobId

        def jobInstanceList
        def jobInstanceId = params.jobInstanceId
        def jobStatusId = params.jobStatusId

        def jobInstanceEventList
        def jobInstanceEventCount


        if ( jobId != null && !jobId.isEmpty()) {
            def jobFilterQuery = ""

            if ( jobInstanceId != null && !jobInstanceId.isEmpty()) {
                jobFilterQuery += " and ji.instance.id=${jobId}"
            }

            if ( jobStatusId != null && !jobStatusId.isEmpty()) {
              jobFilterQuery += " and ji.status='${JobStatus.byId(Integer.valueOf(jobStatusId))}'"
            }

            jobInstanceList = JobInstance.findAllByJob(Job.findById(Long.valueOf(jobId)))

            def query = "from JobInstanceEvent as ji where ji.instance.job.id=${jobId} ${jobFilterQuery} order by id desc"
            def countQuery = "select count(*) ${query}"
            jobInstanceEventList = JobInstanceEvent.findAll(query, [max: max, offset: offset])
            jobInstanceEventCount = JobInstanceEvent.executeQuery(countQuery).first()
        } else {
            if ( jobStatusId != null && !jobStatusId.isEmpty()) {
                jobInstanceEventList = JobInstanceEvent.findAllByStatus(JobStatus.byId(Integer.valueOf(jobStatusId)), [max: max, offset: offset, sort: "id", order: "desc"])
                jobInstanceEventCount = JobInstanceEvent.countByStatus(JobStatus.byId(Integer.valueOf(jobStatusId)))
                params.jobId=null
                params.jobInstanceId=null
            } else {
                jobInstanceEventList = JobInstanceEvent.list([max: max, offset: offset, sort: "id", order: "desc"])
                jobInstanceEventCount = JobInstanceEvent.count()
            }
        }

        [
            jobInstanceEventInstanceList: jobInstanceEventList,
            jobInstanceEventInstanceTotal: jobInstanceEventCount,
            jobList: jobList,
            jobInstanceList: jobInstanceList,
            jobStatusList: JobStatus.list(),
            params: params
        ]
    }
}