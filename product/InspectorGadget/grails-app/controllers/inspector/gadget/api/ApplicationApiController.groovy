package inspector.gadget.api

import error.ErrorCode
import inspector.gadget.Application

class ApplicationApiController extends ApiController {
    static allowedMethods = [show: 'GET', list: 'GET']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        renderJSON(toCollectionDTO(Application.list()))
    }

    def show() {
        if (params?.id !=null && !params?.id?.isNumber()) {
            renderJSON(ErrorCode.E01.toDTO())
            return
        }

        def appId = Integer.valueOf(params?.id)
        def applicationInstance = Application.findById(appId)
        if (applicationInstance) {
            renderJSON(toApiDTO(applicationInstance))
        } else {
            renderJSON(ErrorCode.E03.toDTO())
            return
        }
    }
}
