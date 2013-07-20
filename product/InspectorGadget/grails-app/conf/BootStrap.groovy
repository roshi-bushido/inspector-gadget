import inspector.gadget.SystemConfiguration
import org.codehaus.groovy.grails.commons.GrailsApplication

class BootStrap {
    GrailsApplication grailsApplication

    def init = { servletContext ->
        CheckInstanceCompletionJob instanceCompletionJob = grailsApplication.mainContext.getBean(CheckInstanceCompletionJob.NAME)
        CheckInstanceStartupJob instanceStartupJob = grailsApplication.mainContext.getBean(CheckInstanceStartupJob.NAME)

        instanceCompletionJob.register()
        instanceStartupJob.register()

        SystemConfiguration.registerObserver(instanceCompletionJob)
        SystemConfiguration.registerObserver(instanceStartupJob)
    }

    def destroy = {
    }
}