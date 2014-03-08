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
@Deprecated
public class ViewPortfolioSecurityItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_sec;
	private String security_code;
	private String short_name;
	private String calculation_crncy;
	private String indstry_group;
	private Integer all_flag;
	private Integer portfolio;
	private Integer wl_flag;
	private Integer pivot;
	private Integer new_flag;

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
	 * @return the calculation_crncy
	 */
	public String getCalculation_crncy() {
		return calculation_crncy;
	}

	/**
	 * @param calculation_crncy
	 *            the calculation_crncy to set
	 */
	public void setCalculation_crncy(String calculation_crncy) {
		this.calculation_crncy = calculation_crncy;
	}

	/**
	 * @return the indstry_group
	 */
	public String getIndstry_group() {
		return indstry_group;
	}

	/**
	 * @param indstry_group
	 *            the indstry_group to set
	 */
	public void setIndstry_group(String indstry_group) {
		this.indstry_group = indstry_group;
	}

	/**
	 * @return the all_flag
	 */
	public Integer getAll_flag() {
		return all_flag;
	}

	/**
	 * @param all_flag
	 *            the all_flag to set
	 */
	public void setAll_flag(Integer all_flag) {
		this.all_flag = all_flag;
	}

	/**
	 * @return the portfolio
	 */
	public Integer getPortfolio() {
		return portfolio;
	}

	/**
	 * @param portfolio
	 *            the portfolio to set
	 */
	public void setPortfolio(Integer portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * @return the wl_flag
	 */
	public Integer getWl_flag() {
		return wl_flag;
	}

	/**
	 * @param wl_flag
	 *            the wl_flag to set
	 */
	public void setWl_flag(Integer wl_flag) {
		this.wl_flag = wl_flag;
	}

	/**
	 * @return the pivot
	 */
	public Integer getPivot() {
		return pivot;
	}

	/**
	 * @param pivot
	 *            the pivot to set
	 */
	public void setPivot(Integer pivot) {
		this.pivot = pivot;
	}

	/**
	 * @return the new_flag
	 */
	public Integer getNew_flag() {
		return new_flag;
	}

	/**
	 * @param new_flag
	 *            the new_flag to set
	 */
	public void setNew_flag(Integer new_flag) {
		this.new_flag = new_flag;
	}
}
