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
public class CompaniesQuarterItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Код Блумберг")
	private String security_code;
	@Column(name = "Период")
	private String period;
	@Column(name = "date")
	private String date;
	@Column(name = "IS_EPS")
	private BigDecimal value;
	@Column(name = "EQY_DPS")
	private BigDecimal eqy_dps;
	@Column(name = "EQY_DVD_YLD_IND")
	private BigDecimal eqy_dvd_yld_ind;
	@Column(name = "SALES_REV_TURN")
	private BigDecimal sales_rev_turn;
	@Column(name = "PROF_MARGIN")
	private BigDecimal prof_margin;
	@Column(name = "OPER_MARGIN")
	private BigDecimal oper_margin;
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
	 * @return the eqy_dvd_yld_ind
	 */
	public BigDecimal getEqy_dvd_yld_ind() {
		return eqy_dvd_yld_ind;
	}

	/**
	 * @param eqy_dvd_yld_ind
	 *            the eqy_dvd_yld_ind to set
	 */
	public void setEqy_dvd_yld_ind(BigDecimal eqy_dvd_yld_ind) {
		this.eqy_dvd_yld_ind = eqy_dvd_yld_ind;
	}

	/**
	 * @return the sales_rev_turn
	 */
	public BigDecimal getSales_rev_turn() {
		return sales_rev_turn;
	}

	/**
	 * @param sales_rev_turn
	 *            the sales_rev_turn to set
	 */
	public void setSales_rev_turn(BigDecimal sales_rev_turn) {
		this.sales_rev_turn = sales_rev_turn;
	}

	/**
	 * @return the prof_margin
	 */
	public BigDecimal getProf_margin() {
		return prof_margin;
	}

	/**
	 * @param prof_margin
	 *            the prof_margin to set
	 */
	public void setProf_margin(BigDecimal prof_margin) {
		this.prof_margin = prof_margin;
	}

	/**
	 * @return the oper_margin
	 */
	public BigDecimal getOper_margin() {
		return oper_margin;
	}

	/**
	 * @param oper_margin
	 *            the oper_margin to set
	 */
	public void setOper_margin(BigDecimal oper_margin) {
		this.oper_margin = oper_margin;
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
