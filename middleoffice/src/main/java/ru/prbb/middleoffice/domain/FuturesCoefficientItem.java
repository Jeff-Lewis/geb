/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * @author RBr
 */
public class FuturesCoefficientItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "coef_id")
	private Long coefId;
	@Column(name = "futures_alias_id")
	private Long futuresId;
	@Column(name = "fut_name")
	private String futures;
	@Column(name = "sys_id")
	private Long tradeSystemId;
	@Column(name = "TradeSystem")
	private String tradeSystem;
	private Number coefficient;
	private String comment;

	public Long getCoefId() {
		return coefId;
	}

	public void setCoefId(Long coefId) {
		this.coefId = coefId;
	}

	public Long getFuturesId() {
		return futuresId;
	}

	public void setFuturesId(Long futuresId) {
		this.futuresId = futuresId;
	}

	public String getFutures() {
		return futures;
	}

	public void setFutures(String futures) {
		this.futures = futures;
	}

	public Long getTradeSystemId() {
		return tradeSystemId;
	}

	public void setTradeSystemId(Long tradeSystemId) {
		this.tradeSystemId = tradeSystemId;
	}

	public String getTradeSystem() {
		return tradeSystem;
	}

	public void setTradeSystem(String tradeSystem) {
		this.tradeSystem = tradeSystem;
	}

	public Number getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Number coefficient) {
		this.coefficient = coefficient;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
