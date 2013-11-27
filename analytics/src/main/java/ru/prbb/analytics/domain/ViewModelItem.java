/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

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
	private Double CurrentPrice;
	private Double TargetPrice;
	private Double TargetPriceCons12M;
	private Double YearHigh;
	private Double YearLow;
	private Double PE_current;
	private Double PE_5;
	private Double PE_10;
	private Double DividendYield;
	private Double BestPrice;
	private Double DeltaBstCur_pct;
	private Double CurrentUpside_pct;
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
	public Double getCurrentPrice() {
		return CurrentPrice;
	}

	/**
	 * @param currentPrice
	 *            the currentPrice to set
	 */
	public void setCurrentPrice(Double currentPrice) {
		CurrentPrice = currentPrice;
	}

	/**
	 * @return the targetPrice
	 */
	public Double getTargetPrice() {
		return TargetPrice;
	}

	/**
	 * @param targetPrice
	 *            the targetPrice to set
	 */
	public void setTargetPrice(Double targetPrice) {
		TargetPrice = targetPrice;
	}

	/**
	 * @return the targetPriceCons12M
	 */
	public Double getTargetPriceCons12M() {
		return TargetPriceCons12M;
	}

	/**
	 * @param targetPriceCons12M
	 *            the targetPriceCons12M to set
	 */
	public void setTargetPriceCons12M(Double targetPriceCons12M) {
		TargetPriceCons12M = targetPriceCons12M;
	}

	/**
	 * @return the yearHigh
	 */
	public Double getYearHigh() {
		return YearHigh;
	}

	/**
	 * @param yearHigh
	 *            the yearHigh to set
	 */
	public void setYearHigh(Double yearHigh) {
		YearHigh = yearHigh;
	}

	/**
	 * @return the yearLow
	 */
	public Double getYearLow() {
		return YearLow;
	}

	/**
	 * @param yearLow
	 *            the yearLow to set
	 */
	public void setYearLow(Double yearLow) {
		YearLow = yearLow;
	}

	/**
	 * @return the pE_current
	 */
	public Double getPE_current() {
		return PE_current;
	}

	/**
	 * @param pE_current
	 *            the pE_current to set
	 */
	public void setPE_current(Double pE_current) {
		PE_current = pE_current;
	}

	/**
	 * @return the pE_5
	 */
	public Double getPE_5() {
		return PE_5;
	}

	/**
	 * @param pE_5
	 *            the pE_5 to set
	 */
	public void setPE_5(Double pE_5) {
		PE_5 = pE_5;
	}

	/**
	 * @return the pE_10
	 */
	public Double getPE_10() {
		return PE_10;
	}

	/**
	 * @param pE_10
	 *            the pE_10 to set
	 */
	public void setPE_10(Double pE_10) {
		PE_10 = pE_10;
	}

	/**
	 * @return the dividendYield
	 */
	public Double getDividendYield() {
		return DividendYield;
	}

	/**
	 * @param dividendYield
	 *            the dividendYield to set
	 */
	public void setDividendYield(Double dividendYield) {
		DividendYield = dividendYield;
	}

	/**
	 * @return the bestPrice
	 */
	public Double getBestPrice() {
		return BestPrice;
	}

	/**
	 * @param bestPrice
	 *            the bestPrice to set
	 */
	public void setBestPrice(Double bestPrice) {
		BestPrice = bestPrice;
	}

	/**
	 * @return the deltaBstCur_pct
	 */
	public Double getDeltaBstCur_pct() {
		return DeltaBstCur_pct;
	}

	/**
	 * @param deltaBstCur_pct
	 *            the deltaBstCur_pct to set
	 */
	public void setDeltaBstCur_pct(Double deltaBstCur_pct) {
		DeltaBstCur_pct = deltaBstCur_pct;
	}

	/**
	 * @return the currentUpside_pct
	 */
	public Double getCurrentUpside_pct() {
		return CurrentUpside_pct;
	}

	/**
	 * @param currentUpside_pct
	 *            the currentUpside_pct to set
	 */
	public void setCurrentUpside_pct(Double currentUpside_pct) {
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
