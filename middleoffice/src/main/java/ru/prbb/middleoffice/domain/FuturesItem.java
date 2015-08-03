/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;

import ru.prbb.Utils;

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

	
	public FuturesItem() {
	}

	public FuturesItem(Object[] arr) {
		setFuturesId(Utils.toLong(arr[0]));
		setCoefId(Utils.toLong(arr[1]));
		setFutures(Utils.toString(arr[2]));
		setTradeSystem(Utils.toString(arr[3]));
		setCoefficient(Utils.toDouble(arr[4]));
		setComment(Utils.toString(arr[5]));
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
