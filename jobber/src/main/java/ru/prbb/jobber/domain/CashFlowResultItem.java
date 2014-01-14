/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class CashFlowResultItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String security;
	private String params;
	private String date;
	private Double value;
	private Double value2;

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
	 * @return the security
	 */
	public String getSecurity() {
		return security;
	}

	/**
	 * @param security
	 *            the security to set
	 */
	public void setSecurity(String security) {
		this.security = security;
	}

	/**
	 * @return the params
	 */
	public String getParams() {
		return params;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the value
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Double value) {
		this.value = value;
	}

	/**
	 * @return the value2
	 */
	public Double getValue2() {
		return value2;
	}

	/**
	 * @param value2
	 *            the value2 to set
	 */
	public void setValue2(Double value2) {
		this.value2 = value2;
	}

}
