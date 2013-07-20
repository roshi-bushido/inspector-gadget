package functional.inspector.gadget.cms

import geb.spock.GebReportingSpec
import groovyx.net.http.RESTClient
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
abstract class CmsSpec extends GebReportingSpec {
    static String JSON_CONTENT_TYPE = groovyx.net.http.ContentType.JSON
    static Integer OK_CODE = 200

    public RESTClient getClientForEndpoint(String endpoint) {
        return new RESTClient("http://localhost:8080/InspectorGadget/")
    }

    def setupSpec() {
        SpringSecurityUtils.metaClass.'static'.ifAllGranted = { String role ->
            return true
        }
        SpringSecurityUtils.metaClass.'static'.ifAnyGranted = { String roles ->
            return true
        }

    }

}