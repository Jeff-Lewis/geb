package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * @author RBr
 */
public class OptionsItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "options_id")
	private Long optionsId;
	@Column(name = "coef_id")
	private Long coefId;
	private String options;
	@Column(name = "TradeSystem")
	private String tradeSystem;
	private Number coefficient;
	private String comment;

	public Long getOptionsId() {
		return optionsId;
	}

	public void setOptionsId(Long optionsId) {
		this.optionsId = optionsId;
	}

	public Long getCoefId() {
		return coefId;
	}

	public void setCoefId(Long coefId) {
		this.coefId = coefId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
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
