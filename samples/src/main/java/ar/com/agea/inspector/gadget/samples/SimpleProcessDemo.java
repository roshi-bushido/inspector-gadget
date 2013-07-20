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
public class SimpleProcessDemo {
   public static Log logger = LogFactory.getLog(SimpleProcessDemo.class);

    public static void main(String[] args) {
        InspectorGadgetServiceClient client = new InspectorGadgetServiceClientImpl();

        if (!client.isRunning()) {
            logger.info("Client is not running");
            System.exit(-1);
        }

        JobDTO job = client.findJobDTOByName("sampleJob");
        JobInstanceDTO jobInstanceDTO = client.startInstanceFor(job);

        doProcessStuff();

        client.finishInstance(jobInstanceDTO);
        logger.info("###### Process ended successfully ########");
    }

    static void doProcessStuff() {
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            logger.error(e.getCause().getMessage());
        }
    }
}
