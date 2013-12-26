/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class ViewModelItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_sec;
	private String company_short_name;
	private String industry_group;
	@Column(name = "CurrentPrice")
	private String currentPrice;
	@Column(name = "BestPrice")
	private String bestPrice;
	@Column(name = "DeltaBstCur_pct")
	private String deltaBstCur_pct;
	@Column(name = "CurrentUpside_pct")
	private String currentUpside_pct;
	@Column(name = "UpsideCons_pct")
	private String upsideCons_pct;
	@Column(name = "AvgUpside")
	private String avgUpside;
	private String volatility;
	@Column(name = "TargetPrice")
	private String targetPrice;
	@Column(name = "TargetPriceCons12M")
	private String targetPriceCons12M;
	@Column(name = "TargetPriceNewMeth")
	private String targetPriceNewMeth;
	@Column(name = "BestPriceNewMeth")
	private String bestPriceNewMeth;
	@Column(name = "CurUpsideNewMeth")
	private String curUpsideNewMeth;
	@Column(name = "DeltaBstPrcCurPrcNewMeth")
	private String deltaBstPrcCurPrcNewMeth;
	@Column(name = "YearHigh")
	private String yearHigh;
	@Column(name = "YearLow")
	private String yearLow;
	@Column(name = "PE_current")
	private String pe_current;
	@Column(name = "PE_5")
	private String pe_5;
	@Column(name = "PE_10")
	private String pe_10;
	@Column(name = "DividendYield")
	private String dividendYield;
	@Column(name = "Beta")
	private String beta;
	@Column(name = "Currency")
	private String currency;

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
	 * @return the company_short_name
	 */
	public String getCompany_short_name() {
		return company_short_name;
	}

	/**
	 * @param company_short_name
	 *            the company_short_name to set
	 */
	public void setCompany_short_name(String company_short_name) {
		this.company_short_name = company_short_name;
	}

	/**
	 * @return the industry_group
	 */
	public String getIndustry_group() {
		return industry_group;
	}

	/**
	 * @param industry_group
	 *            the industry_group to set
	 */
	public void setIndustry_group(String industry_group) {
		this.industry_group = industry_group;
	}

	/**
	 * @return the currentPrice
	 */
	public String getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * @param currentPrice
	 *            the currentPrice to set
	 */
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * @return the bestPrice
	 */
	public String getBestPrice() {
		return bestPrice;
	}

	/**
	 * @param bestPrice
	 *            the bestPrice to set
	 */
	public void setBestPrice(String bestPrice) {
		this.bestPrice = bestPrice;
	}

	/**
	 * @return the deltaBstCur_pct
	 */
	public String getDeltaBstCur_pct() {
		return deltaBstCur_pct;
	}

	/**
	 * @param deltaBstCur_pct
	 *            the deltaBstCur_pct to set
	 */
	public void setDeltaBstCur_pct(String deltaBstCur_pct) {
		this.deltaBstCur_pct = deltaBstCur_pct;
	}

	/**
	 * @return the currentUpside_pct
	 */
	public String getCurrentUpside_pct() {
		return currentUpside_pct;
	}

	/**
	 * @param currentUpside_pct
	 *            the currentUpside_pct to set
	 */
	public void setCurrentUpside_pct(String currentUpside_pct) {
		this.currentUpside_pct = currentUpside_pct;
	}

	/**
	 * @return the upsideCons_pct
	 */
	public String getUpsideCons_pct() {
		return upsideCons_pct;
	}

	/**
	 * @param upsideCons_pct
	 *            the upsideCons_pct to set
	 */
	public void setUpsideCons_pct(String upsideCons_pct) {
		this.upsideCons_pct = upsideCons_pct;
	}

	/**
	 * @return the avgUpside
	 */
	public String getAvgUpside() {
		return avgUpside;
	}

	/**
	 * @param avgUpside
	 *            the avgUpside to set
	 */
	public void setAvgUpside(String avgUpside) {
		this.avgUpside = avgUpside;
	}

	/**
	 * @return the volatility
	 */
	public String getVolatility() {
		return volatility;
	}

	/**
	 * @param volatility
	 *            the volatility to set
	 */
	public void setVolatility(String volatility) {
		this.volatility = volatility;
	}

	/**
	 * @return the targetPrice
	 */
	public String getTargetPrice() {
		return targetPrice;
	}

	/**
	 * @param targetPrice
	 *            the targetPrice to set
	 */
	public void setTargetPrice(String targetPrice) {
		this.targetPrice = targetPrice;
	}

	/**
	 * @return the targetPriceCons12M
	 */
	public String getTargetPriceCons12M() {
		return targetPriceCons12M;
	}

	/**
	 * @param targetPriceCons12M
	 *            the targetPriceCons12M to set
	 */
	public void setTargetPriceCons12M(String targetPriceCons12M) {
		this.targetPriceCons12M = targetPriceCons12M;
	}

	/**
	 * @return the targetPriceNewMeth
	 */
	public String getTargetPriceNewMeth() {
		return targetPriceNewMeth;
	}

	/**
	 * @param targetPriceNewMeth
	 *            the targetPriceNewMeth to set
	 */
	public void setTargetPriceNewMeth(String targetPriceNewMeth) {
		this.targetPriceNewMeth = targetPriceNewMeth;
	}

	/**
	 * @return the bestPriceNewMeth
	 */
	public String getBestPriceNewMeth() {
		return bestPriceNewMeth;
	}

	/**
	 * @param bestPriceNewMeth
	 *            the bestPriceNewMeth to set
	 */
	public void setBestPriceNewMeth(String bestPriceNewMeth) {
		this.bestPriceNewMeth = bestPriceNewMeth;
	}

	/**
	 * @return the curUpsideNewMeth
	 */
	public String getCurUpsideNewMeth() {
		return curUpsideNewMeth;
	}

	/**
	 * @param curUpsideNewMeth
	 *            the curUpsideNewMeth to set
	 */
	public void setCurUpsideNewMeth(String curUpsideNewMeth) {
		this.curUpsideNewMeth = curUpsideNewMeth;
	}

	/**
	 * @return the deltaBstPrcCurPrcNewMeth
	 */
	public String getDeltaBstPrcCurPrcNewMeth() {
		return deltaBstPrcCurPrcNewMeth;
	}

	/**
	 * @param deltaBstPrcCurPrcNewMeth
	 *            the deltaBstPrcCurPrcNewMeth to set
	 */
	public void setDeltaBstPrcCurPrcNewMeth(String deltaBstPrcCurPrcNewMeth) {
		this.deltaBstPrcCurPrcNewMeth = deltaBstPrcCurPrcNewMeth;
	}

	/**
	 * @return the yearHigh
	 */
	public String getYearHigh() {
		return yearHigh;
	}

	/**
	 * @param yearHigh
	 *            the yearHigh to set
	 */
	public void setYearHigh(String yearHigh) {
		this.yearHigh = yearHigh;
	}

	/**
	 * @return the yearLow
	 */
	public String getYearLow() {
		return yearLow;
	}

	/**
	 * @param yearLow
	 *            the yearLow to set
	 */
	public void setYearLow(String yearLow) {
		this.yearLow = yearLow;
	}

	/**
	 * @return the pe_current
	 */
	public String getPe_current() {
		return pe_current;
	}

	/**
	 * @param pe_current
	 *            the pe_current to set
	 */
	public void setPe_current(String pe_current) {
		this.pe_current = pe_current;
	}

	/**
	 * @return the pe_5
	 */
	public String getPe_5() {
		return pe_5;
	}

	/**
	 * @param pe_5
	 *            the pe_5 to set
	 */
	public void setPe_5(String pe_5) {
		this.pe_5 = pe_5;
	}

	/**
	 * @return the pe_10
	 */
	public String getPe_10() {
		return pe_10;
	}

	/**
	 * @param pe_10
	 *            the pe_10 to set
	 */
	public void setPe_10(String pe_10) {
		this.pe_10 = pe_10;
	}

	/**
	 * @return the dividendYield
	 */
	public String getDividendYield() {
		return dividendYield;
	}

	/**
	 * @param dividendYield
	 *            the dividendYield to set
	 */
	public void setDividendYield(String dividendYield) {
		this.dividendYield = dividendYield;
	}

	/**
	 * @return the beta
	 */
	public String getBeta() {
		return beta;
	}

	/**
	 * @param beta
	 *            the beta to set
	 */
	public void setBeta(String beta) {
		this.beta = beta;
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
}
