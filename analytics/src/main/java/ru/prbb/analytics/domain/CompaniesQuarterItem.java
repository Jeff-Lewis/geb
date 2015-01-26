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
	@Column(name = "Currency")
	private String currency;
	@Column(name = "EPS")
	private Number eps;
	@Column(name = "IS_EPS")
	private Number is_eps;
	@Column(name = "IS_COMP_EPS_ADJUSTED")
	private Number is_comp_eps_adjusted;
	@Column(name = "IS_BASIC_EPS_CONT_OPS")
	private Number is_basic_eps_cont_ops;
	@Column(name = "IS_DIL_EPS_CONT_OPS")
	private Number is_dil_eps_cont_ops;
	@Column(name = "EBITDA")
	private Number ebitda;
	@Column(name = "BEST_EBITDA")
	private Number best_ebitda;
	@Column(name = "SALES_REV_TURN")
	private Number sales_rev_turn;
	@Column(name = "NET_REV")
	private Number net_rev;
	@Column(name = "PROF_MARGIN")
	private Number prof_margin;
	@Column(name = "OPER_MARGIN")
	private Number oper_margin;
	@Column(name = "OPER_ROE")
	private Number oper_roe;
	@Column(name = "EQY_DPS")
	private Number eqy_dps;
	@Column(name = "EQY_DVD_YLD_IND")
	private Number eqy_dvd_yld_ind;

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Number getEps() {
		return eps;
	}

	public void setEps(Number eps) {
		this.eps = eps;
	}

	public Number getIs_eps() {
		return is_eps;
	}

	public void setIs_eps(Number is_eps) {
		this.is_eps = is_eps;
	}

	public Number getIs_comp_eps_adjusted() {
		return is_comp_eps_adjusted;
	}

	public void setIs_comp_eps_adjusted(Number is_comp_eps_adjusted) {
		this.is_comp_eps_adjusted = is_comp_eps_adjusted;
	}

	public Number getIs_basic_eps_cont_ops() {
		return is_basic_eps_cont_ops;
	}

	public void setIs_basic_eps_cont_ops(Number is_basic_eps_cont_ops) {
		this.is_basic_eps_cont_ops = is_basic_eps_cont_ops;
	}

	public Number getIs_dil_eps_cont_ops() {
		return is_dil_eps_cont_ops;
	}

	public void setIs_dil_eps_cont_ops(Number is_dil_eps_cont_ops) {
		this.is_dil_eps_cont_ops = is_dil_eps_cont_ops;
	}

	public Number getEbitda() {
		return ebitda;
	}

	public void setEbitda(Number ebitda) {
		this.ebitda = ebitda;
	}

	public Number getBest_ebitda() {
		return best_ebitda;
	}

	public void setBest_ebitda(Number best_ebitda) {
		this.best_ebitda = best_ebitda;
	}

	public Number getSales_rev_turn() {
		return sales_rev_turn;
	}

	public void setSales_rev_turn(Number sales_rev_turn) {
		this.sales_rev_turn = sales_rev_turn;
	}

	public Number getNet_rev() {
		return net_rev;
	}

	public void setNet_rev(Number net_rev) {
		this.net_rev = net_rev;
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

	public Number getOper_roe() {
		return oper_roe;
	}

	public void setOper_roe(Number oper_roe) {
		this.oper_roe = oper_roe;
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
}
