/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * @author RBr
 */
//@Entity
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
	private Number koefUpside;
	@Column(name = "Koef Upside н.м.")
	private Number koefUpsideNM;
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
	private Number px_last;
	@Column(name = "peCurrent")
	private Number peCurrent;
	@Column(name = "methodOld")
	private Number methodOld;
	@Column(name = "methodNew")
	private Number methodNew;
	@Column(name = "consensus")
	private Number consensus;
	@Column(name = "roe")
	private Number roe;

	
	public CompaniesItem() {
	}

	public CompaniesItem(Object[] arr) {
		int i = 0;
		// id
		setId_sec(Utils.toLong(arr[i++]));
		// ISIN
		setIsin(Utils.toString(arr[i++]));
		// Название компании
		setSecurity_name(Utils.toString(arr[i++]));
		// Код Блумберг
		setSecurity_code(Utils.toString(arr[i++]));
		// Родной тикер
		setTicker(Utils.toString(arr[i++]));
		// Валюта расчета
		setCurrency(Utils.toString(arr[i++]));
		// ADR
		setAdr(Utils.toString(arr[i++]));
		// Сектор
		setIndstry_grp(Utils.toString(arr[i++]));
		// Группа в сводной
		setSvod_grp(Utils.toString(arr[i++]));
		// Koef Upside
		setKoefUpside(Utils.toDouble(arr[i++]));
		// Koef Upside н.м.
		setKoefUpsideNM(Utils.toDouble(arr[i++]));
		// Периодичность отчетности
		setPeriod(Utils.toString(arr[i++]));
		// EPS
		setEps(Utils.toString(arr[i++]));
		// g10 =
		setG10(Utils.toString(arr[i++]));
		// g5 =
		setG5(Utils.toString(arr[i++]));
		// b10 =
		setB10(Utils.toString(arr[i++]));
		// b5 =
		setB5(Utils.toString(arr[i++]));
		// PE10 =
		setPe10(Utils.toString(arr[i++]));
		// PE5 =
		setPe5(Utils.toString(arr[i++]));
		// crncy
		setCurrencyTrade(Utils.toString(arr[i++]));
		// eqy_fund_crncy
		setCurrencyReport(Utils.toString(arr[i++]));
		// px_last
		setPx_last(Utils.toDouble(arr[i++]));
		// peCurrent
		setPeCurrent(Utils.toDouble(arr[i++]));
		// methodOld
		setMethodOld(Utils.toDouble(arr[i++]));
		// methodNew
		setMethodNew(Utils.toDouble(arr[i++]));
		// consensus
		setConsensus(Utils.toDouble(arr[i++]));
		// roe
		setRoe(Utils.toDouble(arr[i++]));
	}

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

	public Number getKoefUpside() {
		return koefUpside;
	}

	public void setKoefUpside(Number koefUpside) {
		this.koefUpside = koefUpside;
	}

	public Number getKoefUpsideNM() {
		return koefUpsideNM;
	}

	public void setKoefUpsideNM(Number koefUpsideNM) {
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

	public Number getPx_last() {
		return px_last;
	}

	public void setPx_last(Number px_last) {
		this.px_last = px_last;
	}

	public Number getPeCurrent() {
		return peCurrent;
	}

	public void setPeCurrent(Number peCurrent) {
		this.peCurrent = peCurrent;
	}

	public Number getMethodOld() {
		return methodOld;
	}

	public void setMethodOld(Number methodOld) {
		this.methodOld = methodOld;
	}

	public Number getMethodNew() {
		return methodNew;
	}

	public void setMethodNew(Number methodNew) {
		this.methodNew = methodNew;
	}

	public Number getConsensus() {
		return consensus;
	}

	public void setConsensus(Number consensus) {
		this.consensus = consensus;
	}

	public Number getRoe() {
		return roe;
	}

	public void setRoe(Number roe) {
		this.roe = roe;
	}

}
