/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class EquityItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id_sec;
	private String security_code;
	private String short_name;
	private String portfolio;
	private String wl_flag;
	private String pivot;

	/**
	 * @return the id_sec
	 */
	public Long getId_sec() {
		return id_sec;
	}

	/**
	 * @param id_sec
	 *            the id_sec to set
	 */
	public void setId_sec(Long id_sec) {
		this.id_sec = id_sec;
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
	 * @return the portfolio
	 */
	public String getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio
	 *            the portfolio to set
	 */
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @return the wl_flag
	 */
	public String getWl_flag() {
		return wl_flag;
	}

	/**
	 * @param wl_flag
	 *            the wl_flag to set
	 */
	public void setWl_flag(String wl_flag) {
		this.wl_flag = wl_flag;
	}

	/**
	 * @return the pivot
	 */
	public String getPivot() {
		return pivot;
	}

	/**
	 * @param pivot
	 *            the pivot to set
	 */
	public void setPivot(String pivot) {
		this.pivot = pivot;
	}
}
