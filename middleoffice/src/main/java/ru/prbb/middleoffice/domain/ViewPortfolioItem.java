/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author RBr
 * 
 */
public class ViewPortfolioItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String report_date;
	private String client;
	private String fund;
	private String security_code;
	private String short_name;
	private Integer batch;
	private String usd_funding;
	private String currency;
	private Integer quantity;
	private BigDecimal avg_price;
	private BigDecimal last_price;
	private BigDecimal nkd;
	private BigDecimal position;
	private BigDecimal position_rep_date;
	private BigDecimal revaluation;

	/**
	 * @return the report_date
	 */
	public String getReport_date() {
		return report_date;
	}

	/**
	 * @param report_date
	 *            the report_date to set
	 */
	public void setReport_date(String report_date) {
		this.report_date = report_date;
	}

	/**
	 * @return the client
	 */
	public String getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(String client) {
		this.client = client;
	}

	/**
	 * @return the fund
	 */
	public String getFund() {
		return fund;
	}

	/**
	 * @param fund
	 *            the fund to set
	 */
	public void setFund(String fund) {
		this.fund = fund;
	}

	/**
	 * @return the security_code
	 */
	public String getSecurity_code() {
		return security_code;
	}

	/**
	 * @param security_code
	 *            the security_code to set
	 */
	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	/**
	 * @return the short_name
	 */
	public String getShort_name() {
		return short_name;
	}

	/**
	 * @param short_name
	 *            the short_name to set
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	/**
	 * @return the batch
	 */
	public Integer getBatch() {
		return batch;
	}

	/**
	 * @param batch
	 *            the batch to set
	 */
	public void setBatch(Integer batch) {
		this.batch = batch;
	}

	/**
	 * @return the usd_funding
	 */
	public String getUsd_funding() {
		return usd_funding;
	}

	/**
	 * @param usd_funding
	 *            the usd_funding to set
	 */
	public void setUsd_funding(String usd_funding) {
		this.usd_funding = usd_funding;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the avg_price
	 */
	public BigDecimal getAvg_price() {
		return avg_price;
	}

	/**
	 * @param avg_price
	 *            the avg_price to set
	 */
	public void setAvg_price(BigDecimal avg_price) {
		this.avg_price = avg_price;
	}

	/**
	 * @return the last_price
	 */
	public BigDecimal getLast_price() {
		return last_price;
	}

	/**
	 * @param last_price
	 *            the last_price to set
	 */
	public void setLast_price(BigDecimal last_price) {
		this.last_price = last_price;
	}

	/**
	 * @return the nkd
	 */
	public BigDecimal getNkd() {
		return nkd;
	}

	/**
	 * @param nkd
	 *            the nkd to set
	 */
	public void setNkd(BigDecimal nkd) {
		this.nkd = nkd;
	}

	/**
	 * @return the position
	 */
	public BigDecimal getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(BigDecimal position) {
		this.position = position;
	}

	/**
	 * @return the position_rep_date
	 */
	public BigDecimal getPosition_rep_date() {
		return position_rep_date;
	}

	/**
	 * @param position_rep_date
	 *            the position_rep_date to set
	 */
	public void setPosition_rep_date(BigDecimal position_rep_date) {
		this.position_rep_date = position_rep_date;
	}

	/**
	 * @return the revaluation
	 */
	public BigDecimal getRevaluation() {
		return revaluation;
	}

	/**
	 * @param revaluation
	 *            the revaluation to set
	 */
	public void setRevaluation(BigDecimal revaluation) {
		this.revaluation = revaluation;
	}
}
