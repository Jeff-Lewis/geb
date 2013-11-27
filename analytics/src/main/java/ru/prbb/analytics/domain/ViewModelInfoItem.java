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
public class ViewModelInfoItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id_sec;
	private Double factEPS1Q;
	private Double factEPS2Q;
	private Double factEPS3Q;
	private Double factEPS4Q;
	private Double TargetPriceCons12M;
	private Double TargetPriceDecCons;
	private Double TargetPrice;
	private Double BestPrice;
	private Double r;
	private Double teta;
	private Double PriceMedian;
	private Double LastYearAvgWhtPrice;
	private Double M1Q;
	private Double M2Q;
	private Double M3Q;
	private Double M4Q;
	private Double forecastEPS2Q;
	private Double forecastEPS3Q;
	private Double forecastEPS4Q;
	private Double forecastEPS;
	private Double forecastEPS12M;
	private Double forecastEPS_NextYear;
	private Double forecastEPS1QNext;
	private Double forecastEPS2QNext;
	private Double forecastEPS3QNext;
	private Double forecastEPS4QNext;
	private Double forecastEPScons;
	private Double forecastEPScons12M;
	private Double EPSttm;
	private Double g5;
	private Double g10;
	private Double gk;
	private Double PE_5;
	private Double PE_10;
	private Double PE_current;
	private Double PE_ttm;
	private Double PE_cons;
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
	public Double getFactEPS1Q() {
		return factEPS1Q;
	}

	/**
	 * @param factEPS1Q
	 *            the factEPS1Q to set
	 */
	public void setFactEPS1Q(Double factEPS1Q) {
		this.factEPS1Q = factEPS1Q;
	}

	/**
	 * @return the factEPS2Q
	 */
	public Double getFactEPS2Q() {
		return factEPS2Q;
	}

	/**
	 * @param factEPS2Q
	 *            the factEPS2Q to set
	 */
	public void setFactEPS2Q(Double factEPS2Q) {
		this.factEPS2Q = factEPS2Q;
	}

	/**
	 * @return the factEPS3Q
	 */
	public Double getFactEPS3Q() {
		return factEPS3Q;
	}

	/**
	 * @param factEPS3Q
	 *            the factEPS3Q to set
	 */
	public void setFactEPS3Q(Double factEPS3Q) {
		this.factEPS3Q = factEPS3Q;
	}

	/**
	 * @return the factEPS4Q
	 */
	public Double getFactEPS4Q() {
		return factEPS4Q;
	}

	/**
	 * @param factEPS4Q
	 *            the factEPS4Q to set
	 */
	public void setFactEPS4Q(Double factEPS4Q) {
		this.factEPS4Q = factEPS4Q;
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
	 * @return the targetPriceDecCons
	 */
	public Double getTargetPriceDecCons() {
		return TargetPriceDecCons;
	}

	/**
	 * @param targetPriceDecCons
	 *            the targetPriceDecCons to set
	 */
	public void setTargetPriceDecCons(Double targetPriceDecCons) {
		TargetPriceDecCons = targetPriceDecCons;
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
	 * @return the r
	 */
	public Double getR() {
		return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setR(Double r) {
		this.r = r;
	}

	/**
	 * @return the teta
	 */
	public Double getTeta() {
		return teta;
	}

	/**
	 * @param teta
	 *            the teta to set
	 */
	public void setTeta(Double teta) {
		this.teta = teta;
	}

	/**
	 * @return the priceMedian
	 */
	public Double getPriceMedian() {
		return PriceMedian;
	}

	/**
	 * @param priceMedian
	 *            the priceMedian to set
	 */
	public void setPriceMedian(Double priceMedian) {
		PriceMedian = priceMedian;
	}

	/**
	 * @return the lastYearAvgWhtPrice
	 */
	public Double getLastYearAvgWhtPrice() {
		return LastYearAvgWhtPrice;
	}

	/**
	 * @param lastYearAvgWhtPrice
	 *            the lastYearAvgWhtPrice to set
	 */
	public void setLastYearAvgWhtPrice(Double lastYearAvgWhtPrice) {
		LastYearAvgWhtPrice = lastYearAvgWhtPrice;
	}

	/**
	 * @return the m1Q
	 */
	public Double getM1Q() {
		return M1Q;
	}

	/**
	 * @param m1q
	 *            the m1Q to set
	 */
	public void setM1Q(Double m1q) {
		M1Q = m1q;
	}

	/**
	 * @return the m2Q
	 */
	public Double getM2Q() {
		return M2Q;
	}

	/**
	 * @param m2q
	 *            the m2Q to set
	 */
	public void setM2Q(Double m2q) {
		M2Q = m2q;
	}

	/**
	 * @return the m3Q
	 */
	public Double getM3Q() {
		return M3Q;
	}

	/**
	 * @param m3q
	 *            the m3Q to set
	 */
	public void setM3Q(Double m3q) {
		M3Q = m3q;
	}

	/**
	 * @return the m4Q
	 */
	public Double getM4Q() {
		return M4Q;
	}

	/**
	 * @param m4q
	 *            the m4Q to set
	 */
	public void setM4Q(Double m4q) {
		M4Q = m4q;
	}

	/**
	 * @return the forecastEPS2Q
	 */
	public Double getForecastEPS2Q() {
		return forecastEPS2Q;
	}

	/**
	 * @param forecastEPS2Q
	 *            the forecastEPS2Q to set
	 */
	public void setForecastEPS2Q(Double forecastEPS2Q) {
		this.forecastEPS2Q = forecastEPS2Q;
	}

	/**
	 * @return the forecastEPS3Q
	 */
	public Double getForecastEPS3Q() {
		return forecastEPS3Q;
	}

	/**
	 * @param forecastEPS3Q
	 *            the forecastEPS3Q to set
	 */
	public void setForecastEPS3Q(Double forecastEPS3Q) {
		this.forecastEPS3Q = forecastEPS3Q;
	}

	/**
	 * @return the forecastEPS4Q
	 */
	public Double getForecastEPS4Q() {
		return forecastEPS4Q;
	}

	/**
	 * @param forecastEPS4Q
	 *            the forecastEPS4Q to set
	 */
	public void setForecastEPS4Q(Double forecastEPS4Q) {
		this.forecastEPS4Q = forecastEPS4Q;
	}

	/**
	 * @return the forecastEPS
	 */
	public Double getForecastEPS() {
		return forecastEPS;
	}

	/**
	 * @param forecastEPS
	 *            the forecastEPS to set
	 */
	public void setForecastEPS(Double forecastEPS) {
		this.forecastEPS = forecastEPS;
	}

	/**
	 * @return the forecastEPS12M
	 */
	public Double getForecastEPS12M() {
		return forecastEPS12M;
	}

	/**
	 * @param forecastEPS12M
	 *            the forecastEPS12M to set
	 */
	public void setForecastEPS12M(Double forecastEPS12M) {
		this.forecastEPS12M = forecastEPS12M;
	}

	/**
	 * @return the forecastEPS_NextYear
	 */
	public Double getForecastEPS_NextYear() {
		return forecastEPS_NextYear;
	}

	/**
	 * @param forecastEPS_NextYear
	 *            the forecastEPS_NextYear to set
	 */
	public void setForecastEPS_NextYear(Double forecastEPS_NextYear) {
		this.forecastEPS_NextYear = forecastEPS_NextYear;
	}

	/**
	 * @return the forecastEPS1QNext
	 */
	public Double getForecastEPS1QNext() {
		return forecastEPS1QNext;
	}

	/**
	 * @param forecastEPS1QNext
	 *            the forecastEPS1QNext to set
	 */
	public void setForecastEPS1QNext(Double forecastEPS1QNext) {
		this.forecastEPS1QNext = forecastEPS1QNext;
	}

	/**
	 * @return the forecastEPS2QNext
	 */
	public Double getForecastEPS2QNext() {
		return forecastEPS2QNext;
	}

	/**
	 * @param forecastEPS2QNext
	 *            the forecastEPS2QNext to set
	 */
	public void setForecastEPS2QNext(Double forecastEPS2QNext) {
		this.forecastEPS2QNext = forecastEPS2QNext;
	}

	/**
	 * @return the forecastEPS3QNext
	 */
	public Double getForecastEPS3QNext() {
		return forecastEPS3QNext;
	}

	/**
	 * @param forecastEPS3QNext
	 *            the forecastEPS3QNext to set
	 */
	public void setForecastEPS3QNext(Double forecastEPS3QNext) {
		this.forecastEPS3QNext = forecastEPS3QNext;
	}

	/**
	 * @return the forecastEPS4QNext
	 */
	public Double getForecastEPS4QNext() {
		return forecastEPS4QNext;
	}

	/**
	 * @param forecastEPS4QNext
	 *            the forecastEPS4QNext to set
	 */
	public void setForecastEPS4QNext(Double forecastEPS4QNext) {
		this.forecastEPS4QNext = forecastEPS4QNext;
	}

	/**
	 * @return the forecastEPScons
	 */
	public Double getForecastEPScons() {
		return forecastEPScons;
	}

	/**
	 * @param forecastEPScons
	 *            the forecastEPScons to set
	 */
	public void setForecastEPScons(Double forecastEPScons) {
		this.forecastEPScons = forecastEPScons;
	}

	/**
	 * @return the forecastEPScons12M
	 */
	public Double getForecastEPScons12M() {
		return forecastEPScons12M;
	}

	/**
	 * @param forecastEPScons12M
	 *            the forecastEPScons12M to set
	 */
	public void setForecastEPScons12M(Double forecastEPScons12M) {
		this.forecastEPScons12M = forecastEPScons12M;
	}

	/**
	 * @return the ePSttm
	 */
	public Double getEPSttm() {
		return EPSttm;
	}

	/**
	 * @param ePSttm
	 *            the ePSttm to set
	 */
	public void setEPSttm(Double ePSttm) {
		EPSttm = ePSttm;
	}

	/**
	 * @return the g5
	 */
	public Double getG5() {
		return g5;
	}

	/**
	 * @param g5
	 *            the g5 to set
	 */
	public void setG5(Double g5) {
		this.g5 = g5;
	}

	/**
	 * @return the g10
	 */
	public Double getG10() {
		return g10;
	}

	/**
	 * @param g10
	 *            the g10 to set
	 */
	public void setG10(Double g10) {
		this.g10 = g10;
	}

	/**
	 * @return the gk
	 */
	public Double getGk() {
		return gk;
	}

	/**
	 * @param gk
	 *            the gk to set
	 */
	public void setGk(Double gk) {
		this.gk = gk;
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
	 * @return the pE_ttm
	 */
	public Double getPE_ttm() {
		return PE_ttm;
	}

	/**
	 * @param pE_ttm
	 *            the pE_ttm to set
	 */
	public void setPE_ttm(Double pE_ttm) {
		PE_ttm = pE_ttm;
	}

	/**
	 * @return the pE_cons
	 */
	public Double getPE_cons() {
		return PE_cons;
	}

	/**
	 * @param pE_cons
	 *            the pE_cons to set
	 */
	public void setPE_cons(Double pE_cons) {
		PE_cons = pE_cons;
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
