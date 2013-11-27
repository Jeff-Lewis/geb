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
public class ViewModelPriceItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String equity_fund_ticker;
	private String company_short_name;
	private String firm_name;
	private String bloomberg_code;
	private Integer firm_rating;
	private Double target_price;
	private String price_date;
	private String price_period;
	private Integer TR;

	/**
	 * @return the equity_fund_ticker
	 */
	public String getEquity_fund_ticker() {
		return equity_fund_ticker;
	}

	/**
	 * @param equity_fund_ticker
	 *            the equity_fund_ticker to set
	 */
	public void setEquity_fund_ticker(String equity_fund_ticker) {
		this.equity_fund_ticker = equity_fund_ticker;
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
	 * @return the firm_name
	 */
	public String getFirm_name() {
		return firm_name;
	}

	/**
	 * @param firm_name
	 *            the firm_name to set
	 */
	public void setFirm_name(String firm_name) {
		this.firm_name = firm_name;
	}

	/**
	 * @return the bloomberg_code
	 */
	public String getBloomberg_code() {
		return bloomberg_code;
	}

	/**
	 * @param bloomberg_code
	 *            the bloomberg_code to set
	 */
	public void setBloomberg_code(String bloomberg_code) {
		this.bloomberg_code = bloomberg_code;
	}

	/**
	 * @return the firm_rating
	 */
	public Integer getFirm_rating() {
		return firm_rating;
	}

	/**
	 * @param firm_rating
	 *            the firm_rating to set
	 */
	public void setFirm_rating(Integer firm_rating) {
		this.firm_rating = firm_rating;
	}

	/**
	 * @return the target_price
	 */
	public Double getTarget_price() {
		return target_price;
	}

	/**
	 * @param target_price
	 *            the target_price to set
	 */
	public void setTarget_price(Double target_price) {
		this.target_price = target_price;
	}

	/**
	 * @return the price_date
	 */
	public String getPrice_date() {
		return price_date;
	}

	/**
	 * @param price_date
	 *            the price_date to set
	 */
	public void setPrice_date(String price_date) {
		this.price_date = price_date;
	}

	/**
	 * @return the price_period
	 */
	public String getPrice_period() {
		return price_period;
	}

	/**
	 * @param price_period
	 *            the price_period to set
	 */
	public void setPrice_period(String price_period) {
		this.price_period = price_period;
	}

	/**
	 * @return the tR
	 */
	public Integer getTR() {
		return TR;
	}

	/**
	 * @param tR
	 *            the tR to set
	 */
	public void setTR(Integer tR) {
		TR = tR;
	}
}
