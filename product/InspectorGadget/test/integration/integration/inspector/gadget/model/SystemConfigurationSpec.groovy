package integration.inspector.gadget.model

import grails.plugin.spock.IntegrationSpec
import inspector.gadget.SystemConfiguration
import integration.inspector.gadget.sql.DatasetInitializer

import static org.hamcrest.Matchers.equalTo
import static org.junit.Assert.assertThat

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
class SystemConfigurationSpec extends IntegrationSpec {

    def setupSpec() {
        println("Initializing dataset for SystemConfigurationSpec.....")
        DatasetInitializer.initializeDataset()
        println("dataset initialization complete.")
    }

    def "should list all system configuration elements" () {
        when:
            def systemConfigurations = SystemConfiguration.findAll();
        then:
            assertThat(systemConfigurations.size(), equalTo(7))
    }

    def "should check is every system configuration is ok" () {
        expect:
            config.id != null
            config.key != null
            config.key.length() > 0
            config.value != null
            config.value.length() > 0

        where:
            config << SystemConfiguration.findAll()
    }
}

