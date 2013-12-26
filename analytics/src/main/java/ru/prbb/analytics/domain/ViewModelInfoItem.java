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
public class ViewModelInfoItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id_sec;
	private BigDecimal factEPS1Q;
	private BigDecimal factEPS2Q;
	private BigDecimal factEPS3Q;
	private BigDecimal factEPS4Q;
	@Column(name = "TargetPriceCons12M")
	private BigDecimal targetPriceCons12M;
	@Column(name = "TargetPriceDecCons")
	private BigDecimal targetPriceDecCons;
	@Column(name = "TargetPrice")
	private BigDecimal targetPrice;
	@Column(name = "BestPrice")
	private BigDecimal bestPrice;
	private BigDecimal r;
	private BigDecimal teta;
	@Column(name = "PriceMedian")
	private BigDecimal priceMedian;
	@Column(name = "LastYearAvgWhtPrice")
	private BigDecimal lastYearAvgWhtPrice;
	private BigDecimal m1Q;
	private BigDecimal m2Q;
	private BigDecimal m3Q;
	private BigDecimal m4Q;
	private BigDecimal forecastEPS2Q;
	private BigDecimal forecastEPS3Q;
	private BigDecimal forecastEPS4Q;
	private BigDecimal forecastEPS;
	private BigDecimal forecastEPS12M;
	private BigDecimal forecastEPS_NextYear;
	private BigDecimal forecastEPS1QNext;
	private BigDecimal forecastEPS2QNext;
	private BigDecimal forecastEPS3QNext;
	private BigDecimal forecastEPS4QNext;
	private BigDecimal forecastEPScons;
	private BigDecimal forecastEPScons12M;
	@Column(name = "EPSttm")
	private BigDecimal eps_ttm;
	private BigDecimal g5;
	private BigDecimal g10;
	private BigDecimal gk;
	@Column(name = "PE_5")
	private BigDecimal pe_5;
	@Column(name = "PE_10")
	private BigDecimal pe_10;
	@Column(name = "PE_current")
	private BigDecimal pe_current;
	@Column(name = "PE_ttm")
	private BigDecimal pe_ttm;
	@Column(name = "PE_cons")
	private BigDecimal pe_cons;
	private String date_ins;

	/**
	 * @return the id_sec
	 */
	public String getId_sec() {
		return id_sec;
	}

	/**
	 * @param id_sec
	 *            the id_sec to set
	 */
	public void setId_sec(String id_sec) {
		this.id_sec = id_sec;
	}

	/**
	 * @return the factEPS1Q
	 */
	public BigDecimal getFactEPS1Q() {
		return factEPS1Q;
	}

	/**
	 * @param factEPS1Q
	 *            the factEPS1Q to set
	 */
	public void setFactEPS1Q(BigDecimal factEPS1Q) {
		this.factEPS1Q = factEPS1Q;
	}

	/**
	 * @return the factEPS2Q
	 */
	public BigDecimal getFactEPS2Q() {
		return factEPS2Q;
	}

	/**
	 * @param factEPS2Q
	 *            the factEPS2Q to set
	 */
	public void setFactEPS2Q(BigDecimal factEPS2Q) {
		this.factEPS2Q = factEPS2Q;
	}

	/**
	 * @return the factEPS3Q
	 */
	public BigDecimal getFactEPS3Q() {
		return factEPS3Q;
	}

	/**
	 * @param factEPS3Q
	 *            the factEPS3Q to set
	 */
	public void setFactEPS3Q(BigDecimal factEPS3Q) {
		this.factEPS3Q = factEPS3Q;
	}

	/**
	 * @return the factEPS4Q
	 */
	public BigDecimal getFactEPS4Q() {
		return factEPS4Q;
	}

	/**
	 * @param factEPS4Q
	 *            the factEPS4Q to set
	 */
	public void setFactEPS4Q(BigDecimal factEPS4Q) {
		this.factEPS4Q = factEPS4Q;
	}

	/**
	 * @return the targetPriceCons12M
	 */
	public BigDecimal getTargetPriceCons12M() {
		return targetPriceCons12M;
	}

	/**
	 * @param targetPriceCons12M
	 *            the targetPriceCons12M to set
	 */
	public void setTargetPriceCons12M(BigDecimal targetPriceCons12M) {
		this.targetPriceCons12M = targetPriceCons12M;
	}

	/**
	 * @return the targetPriceDecCons
	 */
	public BigDecimal getTargetPriceDecCons() {
		return targetPriceDecCons;
	}

	/**
	 * @param targetPriceDecCons
	 *            the targetPriceDecCons to set
	 */
	public void setTargetPriceDecCons(BigDecimal targetPriceDecCons) {
		this.targetPriceDecCons = targetPriceDecCons;
	}

	/**
	 * @return the targetPrice
	 */
	public BigDecimal getTargetPrice() {
		return targetPrice;
	}

	/**
	 * @param targetPrice
	 *            the targetPrice to set
	 */
	public void setTargetPrice(BigDecimal targetPrice) {
		this.targetPrice = targetPrice;
	}

	/**
	 * @return the bestPrice
	 */
	public BigDecimal getBestPrice() {
		return bestPrice;
	}

	/**
	 * @param bestPrice
	 *            the bestPrice to set
	 */
	public void setBestPrice(BigDecimal bestPrice) {
		this.bestPrice = bestPrice;
	}

	/**
	 * @return the r
	 */
	public BigDecimal getR() {
		return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setR(BigDecimal r) {
		this.r = r;
	}

	/**
	 * @return the teta
	 */
	public BigDecimal getTeta() {
		return teta;
	}

	/**
	 * @param teta
	 *            the teta to set
	 */
	public void setTeta(BigDecimal teta) {
		this.teta = teta;
	}

	/**
	 * @return the priceMedian
	 */
	public BigDecimal getPriceMedian() {
		return priceMedian;
	}

	/**
	 * @param priceMedian
	 *            the priceMedian to set
	 */
	public void setPriceMedian(BigDecimal priceMedian) {
		this.priceMedian = priceMedian;
	}

	/**
	 * @return the lastYearAvgWhtPrice
	 */
	public BigDecimal getLastYearAvgWhtPrice() {
		return lastYearAvgWhtPrice;
	}

	/**
	 * @param lastYearAvgWhtPrice
	 *            the lastYearAvgWhtPrice to set
	 */
	public void setLastYearAvgWhtPrice(BigDecimal lastYearAvgWhtPrice) {
		this.lastYearAvgWhtPrice = lastYearAvgWhtPrice;
	}

	/**
	 * @return the m1Q
	 */
	public BigDecimal getM1Q() {
		return m1Q;
	}

	/**
	 * @param m1q
	 *            the m1Q to set
	 */
	public void setM1Q(BigDecimal m1q) {
		m1Q = m1q;
	}

	/**
	 * @return the m2Q
	 */
	public BigDecimal getM2Q() {
		return m2Q;
	}

	/**
	 * @param m2q
	 *            the m2Q to set
	 */
	public void setM2Q(BigDecimal m2q) {
		m2Q = m2q;
	}

	/**
	 * @return the m3Q
	 */
	public BigDecimal getM3Q() {
		return m3Q;
	}

	/**
	 * @param m3q
	 *            the m3Q to set
	 */
	public void setM3Q(BigDecimal m3q) {
		m3Q = m3q;
	}

	/**
	 * @return the m4Q
	 */
	public BigDecimal getM4Q() {
		return m4Q;
	}

	/**
	 * @param m4q
	 *            the m4Q to set
	 */
	public void setM4Q(BigDecimal m4q) {
		m4Q = m4q;
	}

	/**
	 * @return the forecastEPS2Q
	 */
	public BigDecimal getForecastEPS2Q() {
		return forecastEPS2Q;
	}

	/**
	 * @param forecastEPS2Q
	 *            the forecastEPS2Q to set
	 */
	public void setForecastEPS2Q(BigDecimal forecastEPS2Q) {
		this.forecastEPS2Q = forecastEPS2Q;
	}

	/**
	 * @return the forecastEPS3Q
	 */
	public BigDecimal getForecastEPS3Q() {
		return forecastEPS3Q;
	}

	/**
	 * @param forecastEPS3Q
	 *            the forecastEPS3Q to set
	 */
	public void setForecastEPS3Q(BigDecimal forecastEPS3Q) {
		this.forecastEPS3Q = forecastEPS3Q;
	}

	/**
	 * @return the forecastEPS4Q
	 */
	public BigDecimal getForecastEPS4Q() {
		return forecastEPS4Q;
	}

	/**
	 * @param forecastEPS4Q
	 *            the forecastEPS4Q to set
	 */
	public void setForecastEPS4Q(BigDecimal forecastEPS4Q) {
		this.forecastEPS4Q = forecastEPS4Q;
	}

	/**
	 * @return the forecastEPS
	 */
	public BigDecimal getForecastEPS() {
		return forecastEPS;
	}

	/**
	 * @param forecastEPS
	 *            the forecastEPS to set
	 */
	public void setForecastEPS(BigDecimal forecastEPS) {
		this.forecastEPS = forecastEPS;
	}

	/**
	 * @return the forecastEPS12M
	 */
	public BigDecimal getForecastEPS12M() {
		return forecastEPS12M;
	}

	/**
	 * @param forecastEPS12M
	 *            the forecastEPS12M to set
	 */
	public void setForecastEPS12M(BigDecimal forecastEPS12M) {
		this.forecastEPS12M = forecastEPS12M;
	}

	/**
	 * @return the forecastEPS_NextYear
	 */
	public BigDecimal getForecastEPS_NextYear() {
		return forecastEPS_NextYear;
	}

	/**
	 * @param forecastEPS_NextYear
	 *            the forecastEPS_NextYear to set
	 */
	public void setForecastEPS_NextYear(BigDecimal forecastEPS_NextYear) {
		this.forecastEPS_NextYear = forecastEPS_NextYear;
	}

	/**
	 * @return the forecastEPS1QNext
	 */
	public BigDecimal getForecastEPS1QNext() {
		return forecastEPS1QNext;
	}

	/**
	 * @param forecastEPS1QNext
	 *            the forecastEPS1QNext to set
	 */
	public void setForecastEPS1QNext(BigDecimal forecastEPS1QNext) {
		this.forecastEPS1QNext = forecastEPS1QNext;
	}

	/**
	 * @return the forecastEPS2QNext
	 */
	public BigDecimal getForecastEPS2QNext() {
		return forecastEPS2QNext;
	}

	/**
	 * @param forecastEPS2QNext
	 *            the forecastEPS2QNext to set
	 */
	public void setForecastEPS2QNext(BigDecimal forecastEPS2QNext) {
		this.forecastEPS2QNext = forecastEPS2QNext;
	}

	/**
	 * @return the forecastEPS3QNext
	 */
	public BigDecimal getForecastEPS3QNext() {
		return forecastEPS3QNext;
	}

	/**
	 * @param forecastEPS3QNext
	 *            the forecastEPS3QNext to set
	 */
	public void setForecastEPS3QNext(BigDecimal forecastEPS3QNext) {
		this.forecastEPS3QNext = forecastEPS3QNext;
	}

	/**
	 * @return the forecastEPS4QNext
	 */
	public BigDecimal getForecastEPS4QNext() {
		return forecastEPS4QNext;
	}

	/**
	 * @param forecastEPS4QNext
	 *            the forecastEPS4QNext to set
	 */
	public void setForecastEPS4QNext(BigDecimal forecastEPS4QNext) {
		this.forecastEPS4QNext = forecastEPS4QNext;
	}

	/**
	 * @return the forecastEPScons
	 */
	public BigDecimal getForecastEPScons() {
		return forecastEPScons;
	}

	/**
	 * @param forecastEPScons
	 *            the forecastEPScons to set
	 */
	public void setForecastEPScons(BigDecimal forecastEPScons) {
		this.forecastEPScons = forecastEPScons;
	}

	/**
	 * @return the forecastEPScons12M
	 */
	public BigDecimal getForecastEPScons12M() {
		return forecastEPScons12M;
	}

	/**
	 * @param forecastEPScons12M
	 *            the forecastEPScons12M to set
	 */
	public void setForecastEPScons12M(BigDecimal forecastEPScons12M) {
		this.forecastEPScons12M = forecastEPScons12M;
	}

	/**
	 * @return the eps_ttm
	 */
	public BigDecimal getEps_ttm() {
		return eps_ttm;
	}

	/**
	 * @param eps_ttm
	 *            the eps_ttm to set
	 */
	public void setEps_ttm(BigDecimal eps_ttm) {
		this.eps_ttm = eps_ttm;
	}

	/**
	 * @return the g5
	 */
	public BigDecimal getG5() {
		return g5;
	}

	/**
	 * @param g5
	 *            the g5 to set
	 */
	public void setG5(BigDecimal g5) {
		this.g5 = g5;
	}

	/**
	 * @return the g10
	 */
	public BigDecimal getG10() {
		return g10;
	}

	/**
	 * @param g10
	 *            the g10 to set
	 */
	public void setG10(BigDecimal g10) {
		this.g10 = g10;
	}

	/**
	 * @return the gk
	 */
	public BigDecimal getGk() {
		return gk;
	}

	/**
	 * @param gk
	 *            the gk to set
	 */
	public void setGk(BigDecimal gk) {
		this.gk = gk;
	}

	/**
	 * @return the pe_5
	 */
	public BigDecimal getPe_5() {
		return pe_5;
	}

	/**
	 * @param pe_5
	 *            the pe_5 to set
	 */
	public void setPe_5(BigDecimal pe_5) {
		this.pe_5 = pe_5;
	}

	/**
	 * @return the pe_10
	 */
	public BigDecimal getPe_10() {
		return pe_10;
	}

	/**
	 * @param pe_10
	 *            the pe_10 to set
	 */
	public void setPe_10(BigDecimal pe_10) {
		this.pe_10 = pe_10;
	}

	/**
	 * @return the pe_current
	 */
	public BigDecimal getPe_current() {
		return pe_current;
	}

	/**
	 * @param pe_current
	 *            the pe_current to set
	 */
	public void setPe_current(BigDecimal pe_current) {
		this.pe_current = pe_current;
	}

	/**
	 * @return the pe_ttm
	 */
	public BigDecimal getPe_ttm() {
		return pe_ttm;
	}

	/**
	 * @param pe_ttm
	 *            the pe_ttm to set
	 */
	public void setPe_ttm(BigDecimal pe_ttm) {
		this.pe_ttm = pe_ttm;
	}

	/**
	 * @return the pe_cons
	 */
	public BigDecimal getPe_cons() {
		return pe_cons;
	}

	/**
	 * @param pe_cons
	 *            the pe_cons to set
	 */
	public void setPe_cons(BigDecimal pe_cons) {
		this.pe_cons = pe_cons;
	}

	/**
	 * @return the date_ins
	 */
	public String getDate_ins() {
		return date_ins;
	}

	/**
	 * @param date_ins
	 *            the date_ins to set
	 */
	public void setDate_ins(String date_ins) {
		this.date_ins = date_ins;
	}
}
