/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * @author RBr
 */
public class FuturesCoefficientItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "coef_id")
	private Number coefId;
	@Column(name = "futures_alias_id")
	private Number futuresId;
	@Column(name = "fut_name")
	private String futures;
	@Column(name = "sys_id")
	private Number tradeSystemId;
	@Column(name = "TradeSystem")
	private String tradeSystem;
	private Number coefficient;
	private String comment;

	public FuturesCoefficientItem() {
	}
	
	public FuturesCoefficientItem(Object[] arr) {
		int idx = 0;
		coefId = Utils.toLong(arr[idx++]);
		futuresId = Utils.toLong(arr[idx++]);
		tradeSystemId = Utils.toLong(arr[idx++]);
		futures = Utils.toString(arr[idx++]);
		tradeSystem = Utils.toString(arr[idx++]);
		coefficient = Utils.toDouble(arr[idx++]);
		comment = Utils.toString(arr[idx++]);
	}

	public Number getCoefId() {
		return coefId;
	}

	public void setCoefId(Number coefId) {
		this.coefId = coefId;
	}

	public Number getFuturesId() {
		return futuresId;
	}

	public void setFuturesId(Number futuresId) {
		this.futuresId = futuresId;
	}

	public String getFutures() {
		return futures;
	}

	public void setFutures(String futures) {
		this.futures = futures;
	}

	public Number getTradeSystemId() {
		return tradeSystemId;
	}

	public void setTradeSystemId(Number tradeSystemId) {
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
