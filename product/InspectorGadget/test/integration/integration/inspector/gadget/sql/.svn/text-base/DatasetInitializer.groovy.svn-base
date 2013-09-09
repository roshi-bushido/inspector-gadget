package integration.inspector.gadget.sql
import groovy.sql.Sql
import org.codehaus.groovy.grails.commons.ConfigurationHolder
/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class DatasetInitializer {

    static def initializeDataset() {
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
    }
}
