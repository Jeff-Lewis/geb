/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * @author RBr
 */
public class ViewPortfolioTransferItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String dated;
	private String client;
	private String fund;
	private String security_code;
	private Number batch;
	private Number quantity;
	private Number avg_price;
	private Number avg_price_usd;
	private String currency;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDated() {
		return dated;
	}

	public void setDated(String dated) {
		this.dated = dated;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public Number getBatch() {
		return batch;
	}

	public void setBatch(Number batch) {
		this.batch = batch;
	}

	public Number getQuantity() {
		return quantity;
	}

	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}

	public Number getAvg_price() {
		return avg_price;
	}

	public void setAvg_price(Number avg_price) {
		this.avg_price = avg_price;
	}

	public Number getAvg_price_usd() {
		return avg_price_usd;
	}

	public void setAvg_price_usd(Number avg_price_usd) {
		this.avg_price_usd = avg_price_usd;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
