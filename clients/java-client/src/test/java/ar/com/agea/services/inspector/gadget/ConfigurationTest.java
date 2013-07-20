package ar.com.agea.services.inspector.gadget;

import org.junit.Test;

import java.util.Properties;

public class ConfigurationTest extends UnitTest {
	Properties configurationKeys;

	public ConfigurationTest() {
		configurationKeys = new java.util.Properties();
		try {
            configurationKeys.load(this.getClass().getClassLoader().getResourceAsStream("inspector-gadget-default.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void shouldGetHostConfiguration() throws Throwable {
		assertNotNull(configurationKeys);
		assertNotNull(configurationKeys.get("inspector.gadget.api.host"));
		assertNotNull(configurationKeys.get("inspector.gadget.api.catalog.status"));
		assertNotNull(configurationKeys.get("inspector.gadget.api.catalog.job.list"));
        assertNotNull(configurationKeys.get("inspector.gadget.api.catalog.job.byName"));
        assertNotNull(configurationKeys.get("inspector.gadget.api.catalog.job.instance.list"));
        assertNotNull(configurationKeys.get("inspector.gadget.api.catalog.job.instance.start"));
        assertNotNull(configurationKeys.get("inspector.gadget.api.catalog.job.instance.finish"));
        assertNotNull(configurationKeys.get("inspector.gadget.api.catalog.job.instance.crashed"));

	}
}