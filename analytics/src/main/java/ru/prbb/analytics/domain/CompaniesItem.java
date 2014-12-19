/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 */
@Entity
public class CompaniesItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Long id_sec;
	@Column(name = "ISIN")
	private String isin;
	@Column(name = "Название компании")
	private String security_name;
	@Column(name = "Код Блумберг")
	private String security_code;
	@Column(name = "Родной тикер")
	private String ticker;
	@Column(name = "Валюта расчета")
	private String currency;
	@Column(name = "ADR")
	private String adr;
	@Column(name = "Сектор")
	private String indstry_grp;
	@Column(name = "Группа в сводной")
	private String svod_grp;
	@Column(name = "Koef Upside")
	private BigDecimal koefUpside;
	@Column(name = "Koef Upside н.м.")
	private BigDecimal koefUpsideNM;
	@Column(name = "Периодичность отчетности")
	private String period;
	@Column(name = "EPS")
	private String eps;
	@Column(name = "g10 =")
	private String g10;
	@Column(name = "g5 =")
	private String g5;
	@Column(name = "b10 =")
	private String b10;
	@Column(name = "b5 =")
	private String b5;
	@Column(name = "PE10 =")
	private String pe10;
	@Column(name = "PE5 =")
	private String pe5;
	@Column(name = "crncy")
	private String currencyTrade;
	@Column(name = "eqy_fund_crncy")
	private String currencyReport;
	@Column(name = "px_last")
	private BigDecimal px_last;
	@Column(name = "peCurrent")
	private BigDecimal peCurrent;
	@Column(name = "methodOld")
	private BigDecimal methodOld;
	@Column(name = "methodNew")
	private BigDecimal methodNew;
	@Column(name = "consensus")
	private BigDecimal consensus;
	@Column(name = "roe")
	private BigDecimal roe;

	public Long getId_sec() {
		return id_sec;
	}

	public void setId_sec(Long id_sec) {
		this.id_sec = id_sec;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getSecurity_name() {
		return security_name;
	}

	public void setSecurity_name(String security_name) {
		this.security_name = security_name;
	}

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getAdr() {
		return adr;
	}

	public void setAdr(String adr) {
		this.adr = adr;
	}

	public String getIndstry_grp() {
		return indstry_grp;
	}

	public void setIndstry_grp(String indstry_grp) {
		this.indstry_grp = indstry_grp;
	}

	public String getSvod_grp() {
		return svod_grp;
	}

	public void setSvod_grp(String svod_grp) {
		this.svod_grp = svod_grp;
	}

	public BigDecimal getKoefUpside() {
		return koefUpside;
	}

	public void setKoefUpside(BigDecimal koefUpside) {
		this.koefUpside = koefUpside;
	}

	public BigDecimal getKoefUpsideNM() {
		return koefUpsideNM;
	}

	public void setKoefUpsideNM(BigDecimal koefUpsideNM) {
		this.koefUpsideNM = koefUpsideNM;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getEps() {
		return eps;
	}

	public void setEps(String eps) {
		this.eps = eps;
	}

	public String getG10() {
		return g10;
	}

	public void setG10(String g10) {
		this.g10 = g10;
	}

	public String getG5() {
		return g5;
	}

	public void setG5(String g5) {
		this.g5 = g5;
	}

	public String getB10() {
		return b10;
	}

	public void setB10(String b10) {
		this.b10 = b10;
	}

	public String getB5() {
		return b5;
	}

	public void setB5(String b5) {
		this.b5 = b5;
	}

	public String getPe10() {
		return pe10;
	}

	public void setPe10(String pe10) {
		this.pe10 = pe10;
	}

	public String getPe5() {
		return pe5;
	}

	public void setPe5(String pe5) {
		this.pe5 = pe5;
	}

	public String getCurrencyTrade() {
		return currencyTrade;
	}

	public void setCurrencyTrade(String currencyTrade) {
		this.currencyTrade = currencyTrade;
	}

	public String getCurrencyReport() {
		return currencyReport;
	}

	public void setCurrencyReport(String currencyReport) {
		this.currencyReport = currencyReport;
	}

	public BigDecimal getPx_last() {
		return px_last;
	}

	public void setPx_last(BigDecimal px_last) {
		this.px_last = px_last;
	}

	public BigDecimal getPeCurrent() {
		return peCurrent;
	}

	public void setPeCurrent(BigDecimal peCurrent) {
		this.peCurrent = peCurrent;
	}

	public BigDecimal getMethodOld() {
		return methodOld;
	}

	public void setMethodOld(BigDecimal methodOld) {
		this.methodOld = methodOld;
	}

	public BigDecimal getMethodNew() {
		return methodNew;
	}

	public void setMethodNew(BigDecimal methodNew) {
		this.methodNew = methodNew;
	}

	public BigDecimal getConsensus() {
		return consensus;
	}

	public void setConsensus(BigDecimal consensus) {
		this.consensus = consensus;
	}

	public BigDecimal getRoe() {
		return roe;
	}

	public void setRoe(BigDecimal roe) {
		this.roe = roe;
	}

}
