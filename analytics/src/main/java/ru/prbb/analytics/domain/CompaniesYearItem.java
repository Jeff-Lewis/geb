/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;

/**
 * @author RBr
 * 
 */
public class CompaniesYearItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Код Блумберг")
	private String security_code;
	@Column(name = "Период")
	private String period;
	@Column(name = "date")
	private String date;
	@Column(name = "IS_EPS")
	private BigDecimal value;
	@Column(name = "EPS_RECONSTRUCT_FLAG")
	private Integer eps_recon_flag;
	@Column(name = "EQY_DPS")
	private BigDecimal eqy_dps;
	@Column(name = "EQY_WEIGHTED_AVG_PX")
	private BigDecimal eqy_weighted_avg_px;
	@Column(name = "EQY_WEIGHTED_AVG_PX_ADR")
	private BigDecimal eqy_weighted_avg_px_adr;
	@Column(name = "BOOK_VAL_PER_SH")
	private BigDecimal book_val_per_sh;
	@Column(name = "OPER_ROE")
	private BigDecimal oper_roe;
	@Column(name = "RETENTION_RATIO")
	private BigDecimal r_ratio;
	@Column(name = "Currency")
	private String crnc;

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
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
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
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	/**
	 * @return the eps_recon_flag
	 */
	public Integer getEps_recon_flag() {
		return eps_recon_flag;
	}

	/**
	 * @param eps_recon_flag
	 *            the eps_recon_flag to set
	 */
	public void setEps_recon_flag(Integer eps_recon_flag) {
		this.eps_recon_flag = eps_recon_flag;
	}

	/**
	 * @return the eqy_dps
	 */
	public BigDecimal getEqy_dps() {
		return eqy_dps;
	}

	/**
	 * @param eqy_dps
	 *            the eqy_dps to set
	 */
	public void setEqy_dps(BigDecimal eqy_dps) {
		this.eqy_dps = eqy_dps;
	}

	/**
	 * @return the eqy_weighted_avg_px
	 */
	public BigDecimal getEqy_weighted_avg_px() {
		return eqy_weighted_avg_px;
	}

	/**
	 * @param eqy_weighted_avg_px
	 *            the eqy_weighted_avg_px to set
	 */
	public void setEqy_weighted_avg_px(BigDecimal eqy_weighted_avg_px) {
		this.eqy_weighted_avg_px = eqy_weighted_avg_px;
	}

	/**
	 * @return the eqy_weighted_avg_px_adr
	 */
	public BigDecimal getEqy_weighted_avg_px_adr() {
		return eqy_weighted_avg_px_adr;
	}

	/**
	 * @param eqy_weighted_avg_px_adr
	 *            the eqy_weighted_avg_px_adr to set
	 */
	public void setEqy_weighted_avg_px_adr(BigDecimal eqy_weighted_avg_px_adr) {
		this.eqy_weighted_avg_px_adr = eqy_weighted_avg_px_adr;
	}

	/**
	 * @return the book_val_per_sh
	 */
	public BigDecimal getBook_val_per_sh() {
		return book_val_per_sh;
	}

	/**
	 * @param book_val_per_sh
	 *            the book_val_per_sh to set
	 */
	public void setBook_val_per_sh(BigDecimal book_val_per_sh) {
		this.book_val_per_sh = book_val_per_sh;
	}

	/**
	 * @return the oper_roe
	 */
	public BigDecimal getOper_roe() {
		return oper_roe;
	}

	/**
	 * @param oper_roe
	 *            the oper_roe to set
	 */
	public void setOper_roe(BigDecimal oper_roe) {
		this.oper_roe = oper_roe;
	}

	/**
	 * @return the r_ratio
	 */
	public BigDecimal getR_ratio() {
		return r_ratio;
	}

	/**
	 * @param r_ratio
	 *            the r_ratio to set
	 */
	public void setR_ratio(BigDecimal r_ratio) {
		this.r_ratio = r_ratio;
	}

	/**
	 * @return the crnc
	 */
	public String getCrnc() {
		return crnc;
	}

	/**
	 * @param crnc
	 *            the crnc to set
	 */
	public void setCrnc(String crnc) {
		this.crnc = crnc;
	}
}
