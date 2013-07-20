package ar.com.agea.services.inspector.gadget.dto;

import java.io.Serializable;

/**
 * 
 * @author Matias Suarez
 * @author AGEA 2013
 */
public class ErrorDTO implements Serializable {
	private static final long serialVersionUID = 4255841313969300280L;
	private String code;
	private String message;
	private String detail;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}