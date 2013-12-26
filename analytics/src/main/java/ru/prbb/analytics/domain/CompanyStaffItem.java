/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class CompanyStaffItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private Long security_id;
	private String security_code;
	private String short_name;
	private Long report_id;
	private String report_name;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the security_id
	 */
	public Long getSecurity_id() {
		return security_id;
	}

	/**
	 * @param security_id
	 *            the security_id to set
	 */
	public void setSecurity_id(Long security_id) {
		this.security_id = security_id;
	}

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
	 * @return the short_name
	 */
	public String getShort_name() {
		return short_name;
	}

	/**
	 * @param short_name
	 *            the short_name to set
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	/**
	 * @return the report_id
	 */
	public Long getReport_id() {
		return report_id;
	}

	/**
	 * @param report_id
	 *            the report_id to set
	 */
	public void setReport_id(Long report_id) {
		this.report_id = report_id;
	}

	/**
	 * @return the report_name
	 */
	public String getReport_name() {
		return report_name;
	}

	/**
	 * @param report_name
	 *            the report_name to set
	 */
	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}
}
