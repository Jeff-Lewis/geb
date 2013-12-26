/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 * 
 */
public class BuildModelItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String security_code;
	private String status;
	
	/**
	 * @return the security_code
	 */
	public String getSecurity_code() {
		return security_code;
	}
	
	/**
	 * @param security_code
	 *            the security_code to set
	 */
	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
