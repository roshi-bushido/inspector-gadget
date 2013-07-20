package inspector.gadget.cms

import grails.plugins.springsecurity.Secured
import inspector.gadget.Application
import inspector.gadget.job.Job

class JobController extends CmsController {
    static scaffold = Job
    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    @Secured('ROLE_USER')
    def index() {
        redirect action: 'panel', params: params
    }

    @Secured('ROLE_USER')
    def panel() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [jobInstanceList: Job.list(params), jobInstanceTotal: Job.count()]
    }

    @Secured('ROLE_USER')
    def show() {
        def jobInstance = Job.get(params.id)
        if (!jobInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'job.label', default: 'Job'), params.id])
            redirect action: 'panel'
            return
        }
        [jobInstance: jobInstance]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[jobInstance: new Job(params), appList: toCollectionDTO(Application.list())]
			break
		case 'POST':
	        def jobInstance = new Job(params)
	        if (!jobInstance.save(flush: true)) {
	            render view: 'create', model: [jobInstance: jobInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'job.label', default: 'Job'), jobInstance.id])
	        redirect action: 'show', id: jobInstance.id
			break
		}
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def jobInstance = Job.get(params.id)
	        if (!jobInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'job.label', default: 'Job'), params.id])
	            redirect action: 'list'
	            return
	        }
	        [jobInstance: jobInstance, appList: toCollectionDTO(Application.list())]
			break
		case 'POST':
	        def jobInstance = Job.get(params.id)
	        if (!jobInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'job.label', default: 'Job'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (jobInstance.version > version) {
	                jobInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'job.label', default: 'Job')] as Object[],
	                          "Another inspector.gadget.user has updated this Job while you were editing")
	                render view: 'edit', model: [jobInstance: jobInstance]
	                return
	            }
	        }

	        jobInstance.properties = params

	        if (!jobInstance.save(flush: true)) {
	            render view: 'edit', model: [jobInstance: jobInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'job.label', default: 'Job'), jobInstance.id])
	        redirect action: 'show', id: jobInstance.id
			break
		}
    }
}
