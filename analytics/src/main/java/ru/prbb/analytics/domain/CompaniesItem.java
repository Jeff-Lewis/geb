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
 * 
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
	 * @return the isin
	 */
	public String getIsin() {
		return isin;
	}

	/**
	 * @param isin
	 *            the isin to set
	 */
	public void setIsin(String isin) {
		this.isin = isin;
	}

	/**
	 * @return the security_name
	 */
	public String getSecurity_name() {
		return security_name;
	}

	/**
	 * @param security_name
	 *            the security_name to set
	 */
	public void setSecurity_name(String security_name) {
		this.security_name = security_name;
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
	 * @return the ticker
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * @param ticker
	 *            the ticker to set
	 */
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the adr
	 */
	public String getAdr() {
		return adr;
	}

	/**
	 * @param adr
	 *            the adr to set
	 */
	public void setAdr(String adr) {
		this.adr = adr;
	}

	/**
	 * @return the indstry_grp
	 */
	public String getIndstry_grp() {
		return indstry_grp;
	}

	/**
	 * @param indstry_grp
	 *            the indstry_grp to set
	 */
	public void setIndstry_grp(String indstry_grp) {
		this.indstry_grp = indstry_grp;
	}

	/**
	 * @return the svod_grp
	 */
	public String getSvod_grp() {
		return svod_grp;
	}

	/**
	 * @param svod_grp
	 *            the svod_grp to set
	 */
	public void setSvod_grp(String svod_grp) {
		this.svod_grp = svod_grp;
	}

	/**
	 * @return the koefUpside
	 */
	public BigDecimal getKoefUpside() {
		return koefUpside;
	}

	/**
	 * @param koefUpside
	 *            the koefUpside to set
	 */
	public void setKoefUpside(BigDecimal koefUpside) {
		this.koefUpside = koefUpside;
	}

	/**
	 * @return the koefUpsideNM
	 */
	public BigDecimal getKoefUpsideNM() {
		return koefUpsideNM;
	}

	/**
	 * @param koefUpsideNM
	 *            the koefUpsideNM to set
	 */
	public void setKoefUpsideNM(BigDecimal koefUpsideNM) {
		this.koefUpsideNM = koefUpsideNM;
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
	 * @return the eps
	 */
	public String getEps() {
		return eps;
	}

	/**
	 * @param eps
	 *            the eps to set
	 */
	public void setEps(String eps) {
		this.eps = eps;
	}

	/**
	 * @return the g10
	 */
	public String getG10() {
		return g10;
	}

	/**
	 * @param g10
	 *            the g10 to set
	 */
	public void setG10(String g10) {
		this.g10 = g10;
	}

	/**
	 * @return the g5
	 */
	public String getG5() {
		return g5;
	}

	/**
	 * @param g5
	 *            the g5 to set
	 */
	public void setG5(String g5) {
		this.g5 = g5;
	}

	/**
	 * @return the b10
	 */
	public String getB10() {
		return b10;
	}

	/**
	 * @param b10
	 *            the b10 to set
	 */
	public void setB10(String b10) {
		this.b10 = b10;
	}

	/**
	 * @return the b5
	 */
	public String getB5() {
		return b5;
	}

	/**
	 * @param b5
	 *            the b5 to set
	 */
	public void setB5(String b5) {
		this.b5 = b5;
	}

	/**
	 * @return the pe10
	 */
	public String getPe10() {
		return pe10;
	}

	/**
	 * @param pe10
	 *            the pe10 to set
	 */
	public void setPe10(String pe10) {
		this.pe10 = pe10;
	}

	/**
	 * @return the pe5
	 */
	public String getPe5() {
		return pe5;
	}

	/**
	 * @param pe5
	 *            the pe5 to set
	 */
	public void setPe5(String pe5) {
		this.pe5 = pe5;
	}

}
