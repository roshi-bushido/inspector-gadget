package ar.com.agea.services.inspector.gadget.dto;

import java.io.Serializable;

/**
 * 
 * @author Matias Suarez
 * @author AGEA 2013
 */
public class JobDTO implements Serializable {
	private static final long serialVersionUID = 8633503630095492212L;
	private String id;
	private String name;
	private Boolean enabled;
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
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}