/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * @author RBr
 */
public class FuturesItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "futures_id")
	private Long futuresId;
	private String futures;
	@Column(name = "coef_id")
	private Long coefId;
	private Number coefficient;
	@Column(name = "TradeSystem")
	private String tradeSystem;
	private String comment;

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

	public Long getCoefId() {
		return coefId;
	}

	public void setCoefId(Long coefId) {
		this.coefId = coefId;
	}

	public Number getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Number coefficient) {
		this.coefficient = coefficient;
	}

	public String getTradeSystem() {
		return tradeSystem;
	}

	public void setTradeSystem(String tradeSystem) {
		this.tradeSystem = tradeSystem;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
