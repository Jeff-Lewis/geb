/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * @author RBr
 * 
 */
public class ViewExceptionsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "security_code")
	private String sec_code;
	@Column(name = "related_security_code")
	private String rs_code;
	private String exc;
	@Column(name = "related_parameter")
	private String r_par;

	/**
	 * @return the sec_code
	 */
	public String getSec_code() {
		return sec_code;
	}

	/**
	 * @param sec_code
	 *            the sec_code to set
	 */
	public void setSec_code(String sec_code) {
		this.sec_code = sec_code;
	}

	/**
	 * @return the rs_code
	 */
	public String getRs_code() {
		return rs_code;
	}

	/**
	 * @param rs_code
	 *            the rs_code to set
	 */
	public void setRs_code(String rs_code) {
		this.rs_code = rs_code;
	}

	/**
	 * @return the exc
	 */
	public String getExc() {
		return exc;
	}

	/**
	 * @param exc
	 *            the exc to set
	 */
	public void setExc(String exc) {
		this.exc = exc;
	}

	/**
	 * @return the r_par
	 */
	public String getR_par() {
		return r_par;
	}

	/**
	 * @param r_par
	 *            the r_par to set
	 */
	public void setR_par(String r_par) {
		this.r_par = r_par;
	}
}
