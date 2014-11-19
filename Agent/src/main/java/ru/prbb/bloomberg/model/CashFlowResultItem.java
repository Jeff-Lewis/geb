/**
 * 
 */
package ru.prbb.bloomberg.model;

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
	private Double coupon;
	private Double principal;

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

	public Double getCoupon() {
		return coupon;
	}

	public void setCoupon(Double coupon) {
		this.coupon = coupon;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	@Override
	public String toString() {
		return "CashFlowResultItem [id=" + id + ", security=" + security
				+ ", params=" + params + ", date=" + date
				+ ", coupon=" + coupon + ", principal=" + principal + "]";
	}

}
