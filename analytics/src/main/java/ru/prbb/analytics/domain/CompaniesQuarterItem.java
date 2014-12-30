/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * @author RBr
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
	private Number value;
	@Column(name = "EQY_DPS")
	private Number eqy_dps;
	@Column(name = "EQY_DVD_YLD_IND")
	private Number eqy_dvd_yld_ind;
	@Column(name = "SALES_REV_TURN")
	private Number sales_rev_turn;
	@Column(name = "PROF_MARGIN")
	private Number prof_margin;
	@Column(name = "OPER_MARGIN")
	private Number oper_margin;
	@Column(name = "Currency")
	private String crnc;
	@Column(name = "EQY_FUND_CRNCY")
	private String eqyFundCrncy;
	@Column(name = "IS_COMP_EPS_ADJUSTED")
	private Number isCompEpsAdjusted;
	@Column(name = "IS_BASIC_EPS_CONT_OPS")
	private Number isBasicEpsContOps;
	@Column(name = "IS_DIL_EPS_CONT_OPS")
	private Number isDilEpsContOps;
	@Column(name = "EBITDA")
	private Number ebitda;

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
	}

	public Number getEqy_dps() {
		return eqy_dps;
	}

	public void setEqy_dps(Number eqy_dps) {
		this.eqy_dps = eqy_dps;
	}

	public Number getEqy_dvd_yld_ind() {
		return eqy_dvd_yld_ind;
	}

	public void setEqy_dvd_yld_ind(Number eqy_dvd_yld_ind) {
		this.eqy_dvd_yld_ind = eqy_dvd_yld_ind;
	}

	public Number getSales_rev_turn() {
		return sales_rev_turn;
	}

	public void setSales_rev_turn(Number sales_rev_turn) {
		this.sales_rev_turn = sales_rev_turn;
	}

	public Number getProf_margin() {
		return prof_margin;
	}

	public void setProf_margin(Number prof_margin) {
		this.prof_margin = prof_margin;
	}

	public Number getOper_margin() {
		return oper_margin;
	}

	public void setOper_margin(Number oper_margin) {
		this.oper_margin = oper_margin;
	}

	public String getCrnc() {
		return crnc;
	}

	public void setCrnc(String crnc) {
		this.crnc = crnc;
	}

	public String getEqyFundCrncy() {
		return eqyFundCrncy;
	}

	public void setEqyFundCrncy(String eqyFundCrncy) {
		this.eqyFundCrncy = eqyFundCrncy;
	}

	public Number getIsCompEpsAdjusted() {
		return isCompEpsAdjusted;
	}

	public void setIsCompEpsAdjusted(Number isCompEpsAdjusted) {
		this.isCompEpsAdjusted = isCompEpsAdjusted;
	}

	public Number getIsBasicEpsContOps() {
		return isBasicEpsContOps;
	}

	public void setIsBasicEpsContOps(Number isBasicEpsContOps) {
		this.isBasicEpsContOps = isBasicEpsContOps;
	}

	public Number getIsDilEpsContOps() {
		return isDilEpsContOps;
	}

	public void setIsDilEpsContOps(Number isDilEpsContOps) {
		this.isDilEpsContOps = isDilEpsContOps;
	}

	public Number getEbitda() {
		return ebitda;
	}

	public void setEbitda(Number ebitda) {
		this.ebitda = ebitda;
	}
}
