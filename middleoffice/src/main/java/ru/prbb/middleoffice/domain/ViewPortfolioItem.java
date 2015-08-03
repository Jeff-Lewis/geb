/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import ru.prbb.Utils;

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
	private Number batch;
	private String usd_funding;
	private String currency;
	private Number quantity;
	private Number avg_price;
	private Number last_price;
	private Number nkd;
	private Number position;
	private Number position_rep_date;
	private Number revaluation;

	
	public ViewPortfolioItem() {
	}

	public ViewPortfolioItem(Object[] arr) {
		setReport_date(Utils.toDate(arr[0]));
		setClient(Utils.toString(arr[1]));
		setFund(Utils.toString(arr[2]));
		setSecurity_code(Utils.toString(arr[3]));
		setShort_name(Utils.toString(arr[4]));
		setBatch(Utils.toInteger(arr[5]));
		setUsd_funding(Utils.toString(arr[6]));
		setCurrency(Utils.toString(arr[7]));
		setQuantity(Utils.toInteger(arr[8]));
		setAvg_price(Utils.toDouble(arr[9]));
		setLast_price(Utils.toDouble(arr[10]));
		setNkd(Utils.toDouble(arr[11]));
		setPosition(Utils.toDouble(arr[12]));
		setPosition_rep_date(Utils.toDouble(arr[13]));
		setRevaluation(Utils.toDouble(arr[14]));
	}

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
	public Number getBatch() {
		return batch;
	}

	/**
	 * @param batch
	 *            the batch to set
	 */
	public void setBatch(Number batch) {
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
	public Number getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the avg_price
	 */
	public Number getAvg_price() {
		return avg_price;
	}

	/**
	 * @param avg_price
	 *            the avg_price to set
	 */
	public void setAvg_price(Number avg_price) {
		this.avg_price = avg_price;
	}

	/**
	 * @return the last_price
	 */
	public Number getLast_price() {
		return last_price;
	}

	/**
	 * @param last_price
	 *            the last_price to set
	 */
	public void setLast_price(Number last_price) {
		this.last_price = last_price;
	}

	/**
	 * @return the nkd
	 */
	public Number getNkd() {
		return nkd;
	}

	/**
	 * @param nkd
	 *            the nkd to set
	 */
	public void setNkd(Number nkd) {
		this.nkd = nkd;
	}

	/**
	 * @return the position
	 */
	public Number getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(Number position) {
		this.position = position;
	}

	/**
	 * @return the position_rep_date
	 */
	public Number getPosition_rep_date() {
		return position_rep_date;
	}

	/**
	 * @param position_rep_date
	 *            the position_rep_date to set
	 */
	public void setPosition_rep_date(Number position_rep_date) {
		this.position_rep_date = position_rep_date;
	}

	/**
	 * @return the revaluation
	 */
	public Number getRevaluation() {
		return revaluation;
	}

	/**
	 * @param revaluation
	 *            the revaluation to set
	 */
	public void setRevaluation(Number revaluation) {
		this.revaluation = revaluation;
	}
}
