/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class ViewModelItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id_sec;
	private String company_short_name;
	private String industry_group;
	private String CurrentPrice;
	private String TargetPrice;
	private String TargetPriceCons12M;
	private String YearHigh;
	private String YearLow;
	private String PE_current;
	private String PE_5;
	private String PE_10;
	private String DividendYield;
	private String BestPrice;
	private String DeltaBstCur_pct;
	private String CurrentUpside_pct;
	private String Currency;

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
		return CurrentPrice;
	}

	/**
	 * @param currentPrice
	 *            the currentPrice to set
	 */
	public void setCurrentPrice(String currentPrice) {
		CurrentPrice = currentPrice;
	}

	/**
	 * @return the targetPrice
	 */
	public String getTargetPrice() {
		return TargetPrice;
	}

	/**
	 * @param targetPrice
	 *            the targetPrice to set
	 */
	public void setTargetPrice(String targetPrice) {
		TargetPrice = targetPrice;
	}

	/**
	 * @return the targetPriceCons12M
	 */
	public String getTargetPriceCons12M() {
		return TargetPriceCons12M;
	}

	/**
	 * @param targetPriceCons12M
	 *            the targetPriceCons12M to set
	 */
	public void setTargetPriceCons12M(String targetPriceCons12M) {
		TargetPriceCons12M = targetPriceCons12M;
	}

	/**
	 * @return the yearHigh
	 */
	public String getYearHigh() {
		return YearHigh;
	}

	/**
	 * @param yearHigh
	 *            the yearHigh to set
	 */
	public void setYearHigh(String yearHigh) {
		YearHigh = yearHigh;
	}

	/**
	 * @return the yearLow
	 */
	public String getYearLow() {
		return YearLow;
	}

	/**
	 * @param yearLow
	 *            the yearLow to set
	 */
	public void setYearLow(String yearLow) {
		YearLow = yearLow;
	}

	/**
	 * @return the pE_current
	 */
	public String getPE_current() {
		return PE_current;
	}

	/**
	 * @param pE_current
	 *            the pE_current to set
	 */
	public void setPE_current(String pE_current) {
		PE_current = pE_current;
	}

	/**
	 * @return the pE_5
	 */
	public String getPE_5() {
		return PE_5;
	}

	/**
	 * @param pE_5
	 *            the pE_5 to set
	 */
	public void setPE_5(String pE_5) {
		PE_5 = pE_5;
	}

	/**
	 * @return the pE_10
	 */
	public String getPE_10() {
		return PE_10;
	}

	/**
	 * @param pE_10
	 *            the pE_10 to set
	 */
	public void setPE_10(String pE_10) {
		PE_10 = pE_10;
	}

	/**
	 * @return the dividendYield
	 */
	public String getDividendYield() {
		return DividendYield;
	}

	/**
	 * @param dividendYield
	 *            the dividendYield to set
	 */
	public void setDividendYield(String dividendYield) {
		DividendYield = dividendYield;
	}

	/**
	 * @return the bestPrice
	 */
	public String getBestPrice() {
		return BestPrice;
	}

	/**
	 * @param bestPrice
	 *            the bestPrice to set
	 */
	public void setBestPrice(String bestPrice) {
		BestPrice = bestPrice;
	}

	/**
	 * @return the deltaBstCur_pct
	 */
	public String getDeltaBstCur_pct() {
		return DeltaBstCur_pct;
	}

	/**
	 * @param deltaBstCur_pct
	 *            the deltaBstCur_pct to set
	 */
	public void setDeltaBstCur_pct(String deltaBstCur_pct) {
		DeltaBstCur_pct = deltaBstCur_pct;
	}

	/**
	 * @return the currentUpside_pct
	 */
	public String getCurrentUpside_pct() {
		return CurrentUpside_pct;
	}

	/**
	 * @param currentUpside_pct
	 *            the currentUpside_pct to set
	 */
	public void setCurrentUpside_pct(String currentUpside_pct) {
		CurrentUpside_pct = currentUpside_pct;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return Currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		Currency = currency;
	}
}
