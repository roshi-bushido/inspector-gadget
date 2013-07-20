package ar.com.agea.inspector.gadget.samples;

import ar.com.agea.services.inspector.gadget.delegate.InspectorGadgetServiceClient;
import ar.com.agea.services.inspector.gadget.delegate.impl.InspectorGadgetServiceClientImpl;
import ar.com.agea.services.inspector.gadget.dto.JobDTO;
import ar.com.agea.services.inspector.gadget.dto.JobInstanceDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
public class ErrorProcessDemo {
    public static Log logger = LogFactory.getLog(SimpleProcessDemo.class);

    public static void main(String[] args) {
        InspectorGadgetServiceClient client = new InspectorGadgetServiceClientImpl();

        if (!client.isRunning()) {
            logger.info("Client is not running");
            System.exit(-1);
        }

        JobDTO job = client.findJobDTOByName("sampleJob");
        JobInstanceDTO jobInstanceDTO = client.startInstanceFor(job);

        Throwable error = doProcessStuff();

        client.crashInstance(jobInstanceDTO, error);
        logger.info("###### Process ended with an exception ########");
    }

    static Throwable doProcessStuff() {
        return new RuntimeException("Sample exception");
    }
}
