package inspector.gadget.api
import com.google.gson.JsonObject
import inspector.gadget.NotificationService
import org.codehaus.groovy.grails.commons.GrailsApplication

class InformationController extends ApiController {
    GrailsApplication grailsApplication
    NotificationService notificationService

    def status() {
        def object = new JsonObject();
        object.addProperty("server-status", "ok")
        object.addProperty("mail-relay-status", toMessage(notificationService.isRunning()))
        renderJSON(object)
    }

    def version() {
        def object = new JsonObject();
        object.addProperty("version", grailsApplication.metadata.get("app.version"));
        renderJSON(object)
    }


    private def toMessage(Boolean value) {
        if (value) {
            return "ok"
        } else {
            return "down"
        }
    }
}