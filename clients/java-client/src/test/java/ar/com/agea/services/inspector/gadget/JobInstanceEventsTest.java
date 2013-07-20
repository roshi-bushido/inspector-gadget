package ar.com.agea.services.inspector.gadget;

import ar.com.agea.services.inspector.gadget.delegate.InspectorGadgetServiceClient;
import ar.com.agea.services.inspector.gadget.delegate.impl.InspectorGadgetServiceClientImpl;
import ar.com.agea.services.inspector.gadget.dto.JobDTO;
import ar.com.agea.services.inspector.gadget.dto.JobInstanceDTO;
import ar.com.agea.services.inspector.gadget.dto.JobInstanceEventDTO;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:bushido.ms@gmail.com">Matías Suárez</a>
 */
public class JobInstanceEventsTest extends UnitTest {
    InspectorGadgetServiceClient client = new InspectorGadgetServiceClientImpl();

    @Test
    public void shouldGetAllEventsFromAnActiveJobInstance() {
        JobDTO job = client.findJobDTOByName("sampleCIJob");
        assertNotNull(job);
        assertNotNull(job.getId());

        Collection<JobInstanceDTO> jobInstanceList = client.getJobInstancesFrom(job);
        assertFalse(jobInstanceList.isEmpty());

        for (JobInstanceDTO instance : jobInstanceList) {
            Collection<JobInstanceEventDTO> eventList = client.getEventListFrom(instance);
            for (JobInstanceEventDTO event : eventList) {
                assertNotNull(event);
                assertNotNull(event.getId());
                assertNotNull(event.getStatus());
                assertNotNull(event.getDescription());
                assertEquals(job.getId(), event.getJobId());
                assertEquals(instance.getId(), event.getJobInstanceId());
            }
        }
    }

    @Test
    public void shouldAddASuccessEventSuccessfullyToAnExistingEventInstance() {
        JobDTO job = client.findJobDTOByName("sampleCIJob");
        JobInstanceDTO runningInstance = client.startInstanceFor(job);
        assertNotNull(runningInstance);
        assertNotNull(runningInstance.getId());

        assertTrue(client.getEventListFrom(runningInstance).isEmpty());
        client.addSuccessEventFor(runningInstance, "Everything went ok!");

        Collection<JobInstanceEventDTO> eventListFrom = client.getEventListFrom(runningInstance);
        assertFalse(eventListFrom.isEmpty());
        assertThat(eventListFrom.size(), is(1));

        JobInstanceEventDTO createdEvent = eventListFrom.iterator().next();
        assertNotNull(createdEvent.getId());
        assertThat(createdEvent.getStatus(), is("OK"));
        assertThat(createdEvent.getJobInstanceId(), equalTo(runningInstance.getId()));
        assertThat(createdEvent.getJobId(), equalTo(runningInstance.getJobId()));
    }

    @Test
    public void shouldAddAnErrorEventSuccessfullyToAnExistingEventInstance() {
        JobDTO job = client.findJobDTOByName("sampleCIJob");
        JobInstanceDTO runningInstance = client.startInstanceFor(job);
        assertNotNull(runningInstance);
        assertNotNull(runningInstance.getId());

        assertTrue(client.getEventListFrom(runningInstance).isEmpty());
        client.addErrorEventFor(runningInstance, "Ups!");

        Collection<JobInstanceEventDTO> eventListFrom = client.getEventListFrom(runningInstance);
        assertFalse(eventListFrom.isEmpty());
        assertThat(eventListFrom.size(), is(1));

        JobInstanceEventDTO createdEvent = eventListFrom.iterator().next();
        assertNotNull(createdEvent.getId());
        assertThat(createdEvent.getStatus(), is("ERROR"));
        assertThat(createdEvent.getJobInstanceId(), equalTo(runningInstance.getId()));
        assertThat(createdEvent.getJobId(), equalTo(runningInstance.getJobId()));
    }
}
