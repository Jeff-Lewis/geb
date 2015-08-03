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
 * 
 */
//@Entity
public class ViewModelInfoItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id_sec;
	private Number factEPS1Q;
	private Number factEPS2Q;
	private Number factEPS3Q;
	private Number factEPS4Q;
	@Column(name = "TargetPriceCons12M")
	private Number targetPriceCons12M;
	@Column(name = "TargetPriceDecCons")
	private Number targetPriceDecCons;
	@Column(name = "TargetPrice")
	private Number targetPrice;
	@Column(name = "BestPrice")
	private Number bestPrice;
	private Number r;
	private Number teta;
	@Column(name = "PriceMedian")
	private Number priceMedian;
	@Column(name = "LastYearAvgWhtPrice")
	private Number lastYearAvgWhtPrice;
	private Number m1Q;
	private Number m2Q;
	private Number m3Q;
	private Number m4Q;
	private Number forecastEPS2Q;
	private Number forecastEPS3Q;
	private Number forecastEPS4Q;
	private Number forecastEPS;
	private Number forecastEPS12M;
	private Number forecastEPS_NextYear;
	private Number forecastEPS1QNext;
	private Number forecastEPS2QNext;
	private Number forecastEPS3QNext;
	private Number forecastEPS4QNext;
	private Number forecastEPScons;
	private Number forecastEPScons12M;
	@Column(name = "EPSttm")
	private Number eps_ttm;
	private Number g5;
	private Number g10;
	private Number gk;
	@Column(name = "PE_5")
	private Number pe_5;
	@Column(name = "PE_10")
	private Number pe_10;
	@Column(name = "PE_current")
	private Number pe_current;
	@Column(name = "PE_ttm")
	private Number pe_ttm;
	@Column(name = "PE_cons")
	private Number pe_cons;
	private String date_ins;

	
	public ViewModelInfoItem() {
	}

	public ViewModelInfoItem(Object[] arr) {
		int idx = 0;
		id_sec = Utils.toString(arr[idx++]);
		factEPS1Q = Utils.toNumber(arr[idx++]);
		factEPS2Q = Utils.toNumber(arr[idx++]);
		factEPS3Q = Utils.toNumber(arr[idx++]);
		factEPS4Q = Utils.toNumber(arr[idx++]);
		targetPriceCons12M = Utils.toNumber(arr[idx++]);
		targetPriceDecCons = Utils.toNumber(arr[idx++]);
		targetPrice = Utils.toNumber(arr[idx++]);
		bestPrice = Utils.toNumber(arr[idx++]);
		r = Utils.toNumber(arr[idx++]);
		teta = Utils.toNumber(arr[idx++]);
		priceMedian = Utils.toNumber(arr[idx++]);
		lastYearAvgWhtPrice = Utils.toNumber(arr[idx++]);
		m1Q = Utils.toNumber(arr[idx++]);
		m2Q = Utils.toNumber(arr[idx++]);
		m3Q = Utils.toNumber(arr[idx++]);
		m4Q = Utils.toNumber(arr[idx++]);
		forecastEPS2Q = Utils.toNumber(arr[idx++]);
		forecastEPS3Q = Utils.toNumber(arr[idx++]);
		forecastEPS4Q = Utils.toNumber(arr[idx++]);
		forecastEPS = Utils.toNumber(arr[idx++]);
		forecastEPS12M = Utils.toNumber(arr[idx++]);
		forecastEPS_NextYear = Utils.toNumber(arr[idx++]);
		forecastEPS1QNext = Utils.toNumber(arr[idx++]);
		forecastEPS2QNext = Utils.toNumber(arr[idx++]);
		forecastEPS3QNext = Utils.toNumber(arr[idx++]);
		forecastEPS4QNext = Utils.toNumber(arr[idx++]);
		forecastEPScons = Utils.toNumber(arr[idx++]);
		forecastEPScons12M = Utils.toNumber(arr[idx++]);
		eps_ttm = Utils.toNumber(arr[idx++]);
		g5 = Utils.toNumber(arr[idx++]);
		g10 = Utils.toNumber(arr[idx++]);
		gk = Utils.toNumber(arr[idx++]);
		pe_5 = Utils.toNumber(arr[idx++]);
		pe_10 = Utils.toNumber(arr[idx++]);
		pe_current = Utils.toNumber(arr[idx++]);
		pe_ttm = Utils.toNumber(arr[idx++]);
		pe_cons = Utils.toNumber(arr[idx++]);
		date_ins = Utils.toString(arr[idx++]);
	}

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
	public Number getFactEPS1Q() {
		return factEPS1Q;
	}

	/**
	 * @param factEPS1Q
	 *            the factEPS1Q to set
	 */
	public void setFactEPS1Q(Number factEPS1Q) {
		this.factEPS1Q = factEPS1Q;
	}

	/**
	 * @return the factEPS2Q
	 */
	public Number getFactEPS2Q() {
		return factEPS2Q;
	}

	/**
	 * @param factEPS2Q
	 *            the factEPS2Q to set
	 */
	public void setFactEPS2Q(Number factEPS2Q) {
		this.factEPS2Q = factEPS2Q;
	}

	/**
	 * @return the factEPS3Q
	 */
	public Number getFactEPS3Q() {
		return factEPS3Q;
	}

	/**
	 * @param factEPS3Q
	 *            the factEPS3Q to set
	 */
	public void setFactEPS3Q(Number factEPS3Q) {
		this.factEPS3Q = factEPS3Q;
	}

	/**
	 * @return the factEPS4Q
	 */
	public Number getFactEPS4Q() {
		return factEPS4Q;
	}

	/**
	 * @param factEPS4Q
	 *            the factEPS4Q to set
	 */
	public void setFactEPS4Q(Number factEPS4Q) {
		this.factEPS4Q = factEPS4Q;
	}

	/**
	 * @return the targetPriceCons12M
	 */
	public Number getTargetPriceCons12M() {
		return targetPriceCons12M;
	}

	/**
	 * @param targetPriceCons12M
	 *            the targetPriceCons12M to set
	 */
	public void setTargetPriceCons12M(Number targetPriceCons12M) {
		this.targetPriceCons12M = targetPriceCons12M;
	}

	/**
	 * @return the targetPriceDecCons
	 */
	public Number getTargetPriceDecCons() {
		return targetPriceDecCons;
	}

	/**
	 * @param targetPriceDecCons
	 *            the targetPriceDecCons to set
	 */
	public void setTargetPriceDecCons(Number targetPriceDecCons) {
		this.targetPriceDecCons = targetPriceDecCons;
	}

	/**
	 * @return the targetPrice
	 */
	public Number getTargetPrice() {
		return targetPrice;
	}

	/**
	 * @param targetPrice
	 *            the targetPrice to set
	 */
	public void setTargetPrice(Number targetPrice) {
		this.targetPrice = targetPrice;
	}

	/**
	 * @return the bestPrice
	 */
	public Number getBestPrice() {
		return bestPrice;
	}

	/**
	 * @param bestPrice
	 *            the bestPrice to set
	 */
	public void setBestPrice(Number bestPrice) {
		this.bestPrice = bestPrice;
	}

	/**
	 * @return the r
	 */
	public Number getR() {
		return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setR(Number r) {
		this.r = r;
	}

	/**
	 * @return the teta
	 */
	public Number getTeta() {
		return teta;
	}

	/**
	 * @param teta
	 *            the teta to set
	 */
	public void setTeta(Number teta) {
		this.teta = teta;
	}

	/**
	 * @return the priceMedian
	 */
	public Number getPriceMedian() {
		return priceMedian;
	}

	/**
	 * @param priceMedian
	 *            the priceMedian to set
	 */
	public void setPriceMedian(Number priceMedian) {
		this.priceMedian = priceMedian;
	}

	/**
	 * @return the lastYearAvgWhtPrice
	 */
	public Number getLastYearAvgWhtPrice() {
		return lastYearAvgWhtPrice;
	}

	/**
	 * @param lastYearAvgWhtPrice
	 *            the lastYearAvgWhtPrice to set
	 */
	public void setLastYearAvgWhtPrice(Number lastYearAvgWhtPrice) {
		this.lastYearAvgWhtPrice = lastYearAvgWhtPrice;
	}

	/**
	 * @return the m1Q
	 */
	public Number getM1Q() {
		return m1Q;
	}

	/**
	 * @param m1q
	 *            the m1Q to set
	 */
	public void setM1Q(Number m1q) {
		m1Q = m1q;
	}

	/**
	 * @return the m2Q
	 */
	public Number getM2Q() {
		return m2Q;
	}

	/**
	 * @param m2q
	 *            the m2Q to set
	 */
	public void setM2Q(Number m2q) {
		m2Q = m2q;
	}

	/**
	 * @return the m3Q
	 */
	public Number getM3Q() {
		return m3Q;
	}

	/**
	 * @param m3q
	 *            the m3Q to set
	 */
	public void setM3Q(Number m3q) {
		m3Q = m3q;
	}

	/**
	 * @return the m4Q
	 */
	public Number getM4Q() {
		return m4Q;
	}

	/**
	 * @param m4q
	 *            the m4Q to set
	 */
	public void setM4Q(Number m4q) {
		m4Q = m4q;
	}

	/**
	 * @return the forecastEPS2Q
	 */
	public Number getForecastEPS2Q() {
		return forecastEPS2Q;
	}

	/**
	 * @param forecastEPS2Q
	 *            the forecastEPS2Q to set
	 */
	public void setForecastEPS2Q(Number forecastEPS2Q) {
		this.forecastEPS2Q = forecastEPS2Q;
	}

	/**
	 * @return the forecastEPS3Q
	 */
	public Number getForecastEPS3Q() {
		return forecastEPS3Q;
	}

	/**
	 * @param forecastEPS3Q
	 *            the forecastEPS3Q to set
	 */
	public void setForecastEPS3Q(Number forecastEPS3Q) {
		this.forecastEPS3Q = forecastEPS3Q;
	}

	/**
	 * @return the forecastEPS4Q
	 */
	public Number getForecastEPS4Q() {
		return forecastEPS4Q;
	}

	/**
	 * @param forecastEPS4Q
	 *            the forecastEPS4Q to set
	 */
	public void setForecastEPS4Q(Number forecastEPS4Q) {
		this.forecastEPS4Q = forecastEPS4Q;
	}

	/**
	 * @return the forecastEPS
	 */
	public Number getForecastEPS() {
		return forecastEPS;
	}

	/**
	 * @param forecastEPS
	 *            the forecastEPS to set
	 */
	public void setForecastEPS(Number forecastEPS) {
		this.forecastEPS = forecastEPS;
	}

	/**
	 * @return the forecastEPS12M
	 */
	public Number getForecastEPS12M() {
		return forecastEPS12M;
	}

	/**
	 * @param forecastEPS12M
	 *            the forecastEPS12M to set
	 */
	public void setForecastEPS12M(Number forecastEPS12M) {
		this.forecastEPS12M = forecastEPS12M;
	}

	/**
	 * @return the forecastEPS_NextYear
	 */
	public Number getForecastEPS_NextYear() {
		return forecastEPS_NextYear;
	}

	/**
	 * @param forecastEPS_NextYear
	 *            the forecastEPS_NextYear to set
	 */
	public void setForecastEPS_NextYear(Number forecastEPS_NextYear) {
		this.forecastEPS_NextYear = forecastEPS_NextYear;
	}

	/**
	 * @return the forecastEPS1QNext
	 */
	public Number getForecastEPS1QNext() {
		return forecastEPS1QNext;
	}

	/**
	 * @param forecastEPS1QNext
	 *            the forecastEPS1QNext to set
	 */
	public void setForecastEPS1QNext(Number forecastEPS1QNext) {
		this.forecastEPS1QNext = forecastEPS1QNext;
	}

	/**
	 * @return the forecastEPS2QNext
	 */
	public Number getForecastEPS2QNext() {
		return forecastEPS2QNext;
	}

	/**
	 * @param forecastEPS2QNext
	 *            the forecastEPS2QNext to set
	 */
	public void setForecastEPS2QNext(Number forecastEPS2QNext) {
		this.forecastEPS2QNext = forecastEPS2QNext;
	}

	/**
	 * @return the forecastEPS3QNext
	 */
	public Number getForecastEPS3QNext() {
		return forecastEPS3QNext;
	}

	/**
	 * @param forecastEPS3QNext
	 *            the forecastEPS3QNext to set
	 */
	public void setForecastEPS3QNext(Number forecastEPS3QNext) {
		this.forecastEPS3QNext = forecastEPS3QNext;
	}

	/**
	 * @return the forecastEPS4QNext
	 */
	public Number getForecastEPS4QNext() {
		return forecastEPS4QNext;
	}

	/**
	 * @param forecastEPS4QNext
	 *            the forecastEPS4QNext to set
	 */
	public void setForecastEPS4QNext(Number forecastEPS4QNext) {
		this.forecastEPS4QNext = forecastEPS4QNext;
	}

	/**
	 * @return the forecastEPScons
	 */
	public Number getForecastEPScons() {
		return forecastEPScons;
	}

	/**
	 * @param forecastEPScons
	 *            the forecastEPScons to set
	 */
	public void setForecastEPScons(Number forecastEPScons) {
		this.forecastEPScons = forecastEPScons;
	}

	/**
	 * @return the forecastEPScons12M
	 */
	public Number getForecastEPScons12M() {
		return forecastEPScons12M;
	}

	/**
	 * @param forecastEPScons12M
	 *            the forecastEPScons12M to set
	 */
	public void setForecastEPScons12M(Number forecastEPScons12M) {
		this.forecastEPScons12M = forecastEPScons12M;
	}

	/**
	 * @return the eps_ttm
	 */
	public Number getEps_ttm() {
		return eps_ttm;
	}

	/**
	 * @param eps_ttm
	 *            the eps_ttm to set
	 */
	public void setEps_ttm(Number eps_ttm) {
		this.eps_ttm = eps_ttm;
	}

	/**
	 * @return the g5
	 */
	public Number getG5() {
		return g5;
	}

	/**
	 * @param g5
	 *            the g5 to set
	 */
	public void setG5(Number g5) {
		this.g5 = g5;
	}

	/**
	 * @return the g10
	 */
	public Number getG10() {
		return g10;
	}

	/**
	 * @param g10
	 *            the g10 to set
	 */
	public void setG10(Number g10) {
		this.g10 = g10;
	}

	/**
	 * @return the gk
	 */
	public Number getGk() {
		return gk;
	}

	/**
	 * @param gk
	 *            the gk to set
	 */
	public void setGk(Number gk) {
		this.gk = gk;
	}

	/**
	 * @return the pe_5
	 */
	public Number getPe_5() {
		return pe_5;
	}

	/**
	 * @param pe_5
	 *            the pe_5 to set
	 */
	public void setPe_5(Number pe_5) {
		this.pe_5 = pe_5;
	}

	/**
	 * @return the pe_10
	 */
	public Number getPe_10() {
		return pe_10;
	}

	/**
	 * @param pe_10
	 *            the pe_10 to set
	 */
	public void setPe_10(Number pe_10) {
		this.pe_10 = pe_10;
	}

	/**
	 * @return the pe_current
	 */
	public Number getPe_current() {
		return pe_current;
	}

	/**
	 * @param pe_current
	 *            the pe_current to set
	 */
	public void setPe_current(Number pe_current) {
		this.pe_current = pe_current;
	}

	/**
	 * @return the pe_ttm
	 */
	public Number getPe_ttm() {
		return pe_ttm;
	}

	/**
	 * @param pe_ttm
	 *            the pe_ttm to set
	 */
	public void setPe_ttm(Number pe_ttm) {
		this.pe_ttm = pe_ttm;
	}

	/**
	 * @return the pe_cons
	 */
	public Number getPe_cons() {
		return pe_cons;
	}

	/**
	 * @param pe_cons
	 *            the pe_cons to set
	 */
	public void setPe_cons(Number pe_cons) {
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
