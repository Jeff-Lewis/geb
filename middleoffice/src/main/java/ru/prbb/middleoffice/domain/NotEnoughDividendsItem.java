package ru.prbb.middleoffice.domain;

import java.io.Serializable;

public class NotEnoughDividendsItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String security_code;
	private String short_name;
	private String client;
	private String fund;
	private String broker;
	private String account;
	private String currency;
	private String record_date;
	private Number quantity;
	private Number dividend_per_share;
	private String receive_date;

	public String getSecurity_code() {
		return security_code;
	}

	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
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

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRecord_date() {
		return record_date;
	}

	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}

	public Number getQuantity() {
		return quantity;
	}

	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}

	public Number getDividend_per_share() {
		return dividend_per_share;
	}

	public void setDividend_per_share(Number dividend_per_share) {
		this.dividend_per_share = dividend_per_share;
	}

	public String getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}

}
