package ar.com.agea.services.inspector.gadget;

import ar.com.agea.services.inspector.gadget.delegate.InspectorGadgetServiceClient;
import ar.com.agea.services.inspector.gadget.delegate.impl.InspectorGadgetServiceClientImpl;
import ar.com.agea.services.inspector.gadget.dto.JobDTO;
import ar.com.agea.services.inspector.gadget.dto.JobInstanceDTO;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

/**
 * {@link InspectorGadgetServiceClient} test cases.
 *
 * @author Matias Suarez
 * @author AGEA 2013
 */
public class InspectorGadgetServiceClientTest extends UnitTest {
    InspectorGadgetServiceClient client = new InspectorGadgetServiceClientImpl();

    @Test
    public void shouldCreateclientOk() {
        InspectorGadgetServiceClient client = new InspectorGadgetServiceClientImpl();
        assertNotNull(client);
    }

    @Test
    public void shouldGetJobList() {
        Collection<JobDTO> jobList = client.getJobList();
        assertFalse(jobList.isEmpty());
        for(JobDTO job : jobList) {
            assertNotNull(job);
            assertNotNull(job.getId());
            assertNotNull(job.getName());
            assertNotNull(job.getEnabled());
            System.out.println(job.getName());
        }
    }

    @Test
    public void shouldGetExistingJobByName() {
        String jobName = "sampleCIJob";
        JobDTO job = client.findJobDTOByName(jobName);
        assertNotNull(job);
        assertNotNull(job.getId());
        assertNotNull(job.getName());
        assertNotNull(job.getEnabled());
        assertEquals(jobName, job.getName());
    }

    @Test
    public void shouldGetExistingJobByNameWithCaseSensitivity() {
        String jobName = "samplecijob";
        JobDTO job = client.findJobDTOByName(jobName);
        assertNotNull(job);
        assertNotNull(job.getId());
        assertNotNull(job.getName());
        assertNotNull(job.getEnabled());
        assertEquals(jobName.toLowerCase(), job.getName().toLowerCase());
    }

    @Test
    public void shouldGetNullForNonExistingJobByName() {
        JobDTO job = client.findJobDTOByName("Sarasaasdasasd");
        assertNull(job);
    }

    @Test
    public void shouldGetJobInstanceListForEmpleosJob() {
        JobDTO empleosJob = client.findJobDTOByName("sampleCIJob");
        Collection<JobInstanceDTO> jobList = client.getJobInstancesFrom(empleosJob);
        assertFalse(jobList.isEmpty());
        for(JobInstanceDTO job : jobList) {
            assertNotNull(job);
            assertNotNull(job.getId());
            assertNotNull(job.getName());
            assertEquals(empleosJob.getId(), job.getJobId());
        }
    }

    @Test
    public void shouldCreateNewJobInstanceForEmpleosJob() {
        JobDTO job = client.findJobDTOByName("samplecijob");
        assertNotNull(job);

        JobInstanceDTO instance = client.startInstanceFor(job);

        assertNotNull(instance);
        assertNotNull(instance.getId());
        assertNotNull(instance.getStatus());
        assertEquals(job.getId(), instance.getJobId());
        assertNotNull(instance.getDescription());
    }

    @Test
    public void shouldCreateAndFinishAJobInstance() {
        JobDTO job = client.findJobDTOByName("sampleCIJob");
        JobInstanceDTO instance = client.startInstanceFor(job);

        assertNotNull(instance);
        assertNotNull(instance.getId());
        assertNotNull(instance.getStatus());
        assertEquals(job.getId(), instance.getJobId());

        instance = client.finishInstance(instance);
        assertNotNull(instance);
        assertNotNull(instance.getId());
        assertNotNull(instance.getDescription());
        assertNotNull(instance.getStatus());
        assertEquals(job.getId(), instance.getJobId());

    }

    @Test
    public void shouldCreateAndCrashAJobInstanceWithSingleReason() {
        String reason = "failed";
        JobDTO job = client.findJobDTOByName("sampleCIJob");
        JobInstanceDTO instance = client.startInstanceFor(job);

        assertNotNull(instance);
        assertNotNull(instance.getId());
        assertNotNull(instance.getStatus());
        assertEquals(job.getId(), instance.getJobId());

        instance = client.crashInstance(instance, reason);
        assertNotNull(instance);
        assertNotNull(instance.getId());
        assertNotNull(instance.getDescription());
        assertNotNull(instance.getStatus());
        assertEquals(job.getId(), instance.getJobId());
        assertTrue(instance.getDescription().contains(reason));
    }

    @Test
    public void shouldCreateAndCrashAJobInstanceWithRealExceptionTraceAsString() {
        JobDTO job = client.findJobDTOByName("sampleCIJob");
        JobInstanceDTO instance = client.startInstanceFor(job);

        assertNotNull(instance);
        assertNotNull(instance.getId());
        assertNotNull(instance.getStatus());
        assertEquals(job.getId(), instance.getJobId());


        Throwable throwable = generateNullPointerTrace();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);

        String reason = sw.toString();
        assertNotNull(reason);

        instance = client.crashInstance(instance, reason);
        assertNotNull(instance);
        assertNotNull(instance.getId());
        assertNotNull(instance.getDescription());
        assertNotNull(instance.getStatus());
        assertEquals(job.getId(), instance.getJobId());
        assertTrue(instance.getDescription().contains(reason));
    }

    @Test
      public void shouldCreateAndCrashAJobInstanceWithRealExceptionTraceAsException() {
          JobDTO job = client.findJobDTOByName("sampleCIJob");
          JobInstanceDTO instance = client.startInstanceFor(job);

          assertNotNull(instance);
          assertNotNull(instance.getId());
          assertNotNull(instance.getStatus());
          assertEquals(job.getId(), instance.getJobId());


          Throwable throwable = generateNullPointerTrace();
          instance = client.crashInstance(instance, throwable);
          assertNotNull(instance);
          assertNotNull(instance.getId());
          assertNotNull(instance.getDescription());
          assertNotNull(instance.getStatus());
          assertEquals(job.getId(), instance.getJobId());
      }

    @SuppressWarnings("null")
	private Throwable generateNullPointerTrace() {
        try {
            String nullString = null;
            nullString.toString();
        } catch (NullPointerException e) {
            return e;
        }
        return null;
    }

}