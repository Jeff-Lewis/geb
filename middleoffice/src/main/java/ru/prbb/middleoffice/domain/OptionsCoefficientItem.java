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
public class OptionsCoefficientItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "coef_id")
	private Long coefId;
	@Column(name = "options_alias_id")
	private Long optionsId;
	@Column(name = "opt_name")
	private String options;
	@Column(name = "sys_id")
	private Long tradeSystemId;
	@Column(name = "TradeSystem")
	private String tradeSystem;
	private Number coefficient;
	private String comment;

	
	public OptionsCoefficientItem() {
	}

	public OptionsCoefficientItem(Object[] arr) {
		setCoefId(Utils.toLong(arr[0]));
		setOptionsId(Utils.toLong(arr[1]));
		setTradeSystemId(Utils.toLong(arr[2]));
		setOptions(Utils.toString(arr[3]));
		setTradeSystem(Utils.toString(arr[4]));
		setCoefficient(Utils.toDouble(arr[5]));
		setComment(Utils.toString(arr[6]));
	}

	public Long getCoefId() {
		return coefId;
	}

	public void setCoefId(Long coefId) {
		this.coefId = coefId;
	}

	public Long getOptionsId() {
		return optionsId;
	}

	public void setOptionsId(Long optionsId) {
		this.optionsId = optionsId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
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
