package functional.inspector.gadget.api
import geb.spock.GebReportingSpec
import groovy.sql.Sql
import groovyx.net.http.RESTClient
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
abstract class ApiSpec  extends GebReportingSpec {
    public static String JSON_CONTENT_TYPE = groovyx.net.http.ContentType.JSON
    public static Integer OK_CODE = 200

    public RESTClient getClientForEndpoint(String endpoint) {
        return getClientForEndpoint(endpoint, "1.0")
    }

    public RESTClient getClientForEndpoint(String endpoint, String version) {
        return new RESTClient("http://localhost:8080/InspectorGadget/api/${version}/${endpoint}")
    }

    def setupSpec() {
        println("Initializing dataset for ApiSpec.....")
        String sqlFilePath = 'test/integration/integration/inspector/gadget/sql/test-data.sql'
        String sqlString = new File(sqlFilePath).text
        System.out.println(sqlString);

        def dataSource = ConfigurationHolder.config?.dataSource
        def sql = Sql.newInstance(
                dataSource.url,
                dataSource.username,
                dataSource.password,
                dataSource.driverClassName)

        sqlString.eachLine {
            sql.execute(it)
        }
        println("dataset initialization complete.")
    }

}
