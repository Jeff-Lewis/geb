/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import ru.prbb.Utils;

/**
 * @author RBr
 * 
 */
public class BuildEPSItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String security_code;
	private String periodical_eps_status;
	private String yearly_eps_status;
	private String eps_median_status;
	private String pe_median_status;
	private String eps_growth_status;
	private String bv_growth_rate;
	private String pb_median;

	
	public BuildEPSItem() {
	}

	public BuildEPSItem(Object[] arr) {
		int idx = 0;
		setSecurity_code(Utils.toString(arr[idx++]));
		setPeriodical_eps_status(Utils.toString(arr[idx++]));
		setYearly_eps_status(Utils.toString(arr[idx++]));
		setEps_median_status(Utils.toString(arr[idx++]));
		setPe_median_status(Utils.toString(arr[idx++]));
		setEps_growth_status(Utils.toString(arr[idx++]));
		setBv_growth_rate(Utils.toString(arr[idx++]));
		setPb_median(Utils.toString(arr[idx++]));
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
	 * @return the periodical_eps_status
	 */
	public String getPeriodical_eps_status() {
		return periodical_eps_status;
	}
	
	/**
	 * @param periodical_eps_status
	 *            the periodical_eps_status to set
	 */
	public void setPeriodical_eps_status(String periodical_eps_status) {
		this.periodical_eps_status = periodical_eps_status;
	}
	
	/**
	 * @return the yearly_eps_status
	 */
	public String getYearly_eps_status() {
		return yearly_eps_status;
	}
	
	/**
	 * @param yearly_eps_status
	 *            the yearly_eps_status to set
	 */
	public void setYearly_eps_status(String yearly_eps_status) {
		this.yearly_eps_status = yearly_eps_status;
	}
	/**
	 * @return the eps_median_status
	 */
	public String getEps_median_status() {
		return eps_median_status;
	}
	
	/**
	 * @param eps_median_status
	 *            the eps_median_status to set
	 */
	public void setEps_median_status(String eps_median_status) {
		this.eps_median_status = eps_median_status;
	}
	
	/**
	 * @return the pe_median_status
	 */
	public String getPe_median_status() {
		return pe_median_status;
	}
	
	/**
	 * @param pe_median_status
	 *            the pe_median_status to set
	 */
	public void setPe_median_status(String pe_median_status) {
		this.pe_median_status = pe_median_status;
	}
	
	/**
	 * @return the eps_growth_status
	 */
	public String getEps_growth_status() {
		return eps_growth_status;
	}
	
	/**
	 * @param eps_growth_status
	 *            the eps_growth_status to set
	 */
	public void setEps_growth_status(String eps_growth_status) {
		this.eps_growth_status = eps_growth_status;
	}
	
	/**
	 * @return the bv_growth_rate
	 */
	public String getBv_growth_rate() {
		return bv_growth_rate;
	}
	
	/**
	 * @param bv_growth_rate
	 *            the bv_growth_rate to set
	 */
	public void setBv_growth_rate(String bv_growth_rate) {
		this.bv_growth_rate = bv_growth_rate;
	}
	
	/**
	 * @return the pb_median
	 */
	public String getPb_median() {
		return pb_median;
	}
	
	/**
	 * @param pb_median
	 *            the pb_median to set
	 */
	public void setPb_median(String pb_median) {
		this.pb_median = pb_median;
	}
}
