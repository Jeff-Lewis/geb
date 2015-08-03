package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import ru.prbb.Utils;

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

	
	public NotEnoughDividendsItem() {
	}

	public NotEnoughDividendsItem(Object[] arr) {
		setSecurity_code(Utils.toString(arr[0]));
		setShort_name(Utils.toString(arr[1]));
		setClient(Utils.toString(arr[2]));
		setFund(Utils.toString(arr[3]));
		setBroker(Utils.toString(arr[4]));
		setAccount(Utils.toString(arr[5]));
		setCurrency(Utils.toString(arr[6]));
		setRecord_date(Utils.toDate(arr[7]));
		setQuantity(Utils.toInteger(arr[8]));
		setDividend_per_share(Utils.toDouble(arr[9]));
		setReceive_date(Utils.toDate(arr[10]));
	}

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
