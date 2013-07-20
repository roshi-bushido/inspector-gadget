package ar.com.agea.services.inspector.gadget.delegate;

import ar.com.agea.services.inspector.gadget.dto.JobDTO;
import ar.com.agea.services.inspector.gadget.dto.JobInstanceDTO;
import ar.com.agea.services.inspector.gadget.dto.JobInstanceEventDTO;

import java.util.Collection;


/**
 * Java Client for Inspector Gadget Live API
 * @see http://arquitectura-wiki.agea.com.ar/index.php/Inspector_Gadget
 * 
 * @author Matias Suarez
 * @author AGEA 2013
 */
public interface InspectorGadgetServiceClient {

	/**
	 * Checks to see if the API is running.
	 * 
	 * @return <code>true</code> if it is, <code>false</code> false otherwise.
	 */
	Boolean isRunning();

    /**
     * Fetch's all configured jobs. 
     *
     * @return a {@link Collection} of {@link JobDTO} elements representing all available jobs.
     */
	Collection<JobDTO> getJobList();
	
	/**
	 * Finds a specific job by its name.
	 * 
	 * @param jobName the name of the job
	 * @return a {@link JobDTO} element if exists, <code>null</code> otherwise.
	 */
    JobDTO findJobDTOByName(String jobName);

    /**
     * Fetch's all instances from a specific job. 
     *
     * @param job the job
     * @return a {@link Collection} of {@link JobInstanceDTO} elements representing all available jobs instances.
     */
	Collection<JobInstanceDTO> getJobInstancesFrom(JobDTO job);

	/**
	 * Creates a new running instance for a job
	 * 
	 * @param job the job
	 * @return a {@link JobInstanceDTO} representing the newly created instance.
     * @throws ar.com.agea.services.inspector.gadget.exception.JobNotEnabledException if the job is not enabled in the backoffice.
	 */
	JobInstanceDTO startInstanceFor(JobDTO job);
	
	/**
	 * Finishes a new running instance from a job
	 * 
	 * @param instance the running instance
	 * @return a {@link JobInstanceDTO} representing the recently finished instance.
	 */
	JobInstanceDTO finishInstance(JobInstanceDTO instance);
	
	/**
	 * Crashes a running instance from a job
	 * 
	 * @param instance the running instance
	 * @param errorDescription the error that cause the instance to crash.
	 * @return a {@link JobInstanceDTO} representing the recently crashed instance.
	 */
	JobInstanceDTO crashInstance(JobInstanceDTO instance, String errorDescription);

    /**
   	 * Crashes a running instance from a job
   	 *
   	 * @param instance the running instance
   	 * @param exception the root exception that cause the instance to crash.
   	 * @return a {@link JobInstanceDTO} representing the recently crashed instance.
   	 */
   	JobInstanceDTO crashInstance(JobInstanceDTO instance, Throwable exception);


    Collection<JobInstanceEventDTO> getEventListFrom(JobInstanceDTO instance);

    JobInstanceEventDTO addSuccessEventFor(JobInstanceDTO instance, String message);

    JobInstanceEventDTO addErrorEventFor(JobInstanceDTO instance, String message);
}