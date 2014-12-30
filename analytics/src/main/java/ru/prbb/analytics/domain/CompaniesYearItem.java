/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * @author RBr
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
	private Number value;
	@Column(name = "EPS_RECONSTRUCT_FLAG")
	private Integer eps_recon_flag;
	@Column(name = "EQY_DPS")
	private Number eqy_dps;
	@Column(name = "EQY_WEIGHTED_AVG_PX")
	private Number eqy_weighted_avg_px;
	@Column(name = "EQY_WEIGHTED_AVG_PX_ADR")
	private Number eqy_weighted_avg_px_adr;
	@Column(name = "BOOK_VAL_PER_SH")
	private Number book_val_per_sh;
	@Column(name = "OPER_ROE")
	private Number oper_roe;
	@Column(name = "RETENTION_RATIO")
	private Number r_ratio;
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
	@Column(name = "EQY_DVD_YLD_IND")
	private Number eqyDvdYldInd;
	@Column(name = "EBITDA")
	private Number ebitda;
	@Column(name = "SALES_REV_TURN")
	private Number salesRevTurn;
	@Column(name = "OPER_MARGIN")
	private Number operMargin;
	@Column(name = "PROF_MARGIN")
	private Number profMargin;
	@Column(name = "IS_AVG_NUM_SH_FOR_EPS")
	private Number isAvgNumShForEps;

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

	public Integer getEps_recon_flag() {
		return eps_recon_flag;
	}

	public void setEps_recon_flag(Integer eps_recon_flag) {
		this.eps_recon_flag = eps_recon_flag;
	}

	public Number getEqy_dps() {
		return eqy_dps;
	}

	public void setEqy_dps(Number eqy_dps) {
		this.eqy_dps = eqy_dps;
	}

	public Number getEqy_weighted_avg_px() {
		return eqy_weighted_avg_px;
	}

	public void setEqy_weighted_avg_px(Number eqy_weighted_avg_px) {
		this.eqy_weighted_avg_px = eqy_weighted_avg_px;
	}

	public Number getEqy_weighted_avg_px_adr() {
		return eqy_weighted_avg_px_adr;
	}

	public void setEqy_weighted_avg_px_adr(Number eqy_weighted_avg_px_adr) {
		this.eqy_weighted_avg_px_adr = eqy_weighted_avg_px_adr;
	}

	public Number getBook_val_per_sh() {
		return book_val_per_sh;
	}

	public void setBook_val_per_sh(Number book_val_per_sh) {
		this.book_val_per_sh = book_val_per_sh;
	}

	public Number getOper_roe() {
		return oper_roe;
	}

	public void setOper_roe(Number oper_roe) {
		this.oper_roe = oper_roe;
	}

	public Number getR_ratio() {
		return r_ratio;
	}

	public void setR_ratio(Number r_ratio) {
		this.r_ratio = r_ratio;
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

	public Number getEqyDvdYldInd() {
		return eqyDvdYldInd;
	}

	public void setEqyDvdYldInd(Number eqyDvdYldInd) {
		this.eqyDvdYldInd = eqyDvdYldInd;
	}

	public Number getEbitda() {
		return ebitda;
	}

	public void setEbitda(Number ebitda) {
		this.ebitda = ebitda;
	}

	public Number getSalesRevTurn() {
		return salesRevTurn;
	}

	public void setSalesRevTurn(Number salesRevTurn) {
		this.salesRevTurn = salesRevTurn;
	}

	public Number getOperMargin() {
		return operMargin;
	}

	public void setOperMargin(Number operMargin) {
		this.operMargin = operMargin;
	}

	public Number getProfMargin() {
		return profMargin;
	}

	public void setProfMargin(Number profMargin) {
		this.profMargin = profMargin;
	}

	public Number getIsAvgNumShForEps() {
		return isAvgNumShForEps;
	}

	public void setIsAvgNumShForEps(Number isAvgNumShForEps) {
		this.isAvgNumShForEps = isAvgNumShForEps;
	}
}
