package ar.com.agea.services.inspector.gadget.dto;

import java.io.Serializable;

/**
 * 
 * @author Matias Suarez
 * @author AGEA 2013
 */
public class JobInstanceDTO implements Serializable {
	private static final long serialVersionUID = 4671192590867868441L;
	private String id;
	private String name;
	private String jobId;
	private String status;
	private String description;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}