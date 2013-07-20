package ar.com.agea.services.inspector.gadget;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConfigurationTest.class,
        InspectorGadgetServiceClientTest.class,
        JobInstanceEventsTest.class
})
public class InspectorGadgetClientTestSuite {

}
