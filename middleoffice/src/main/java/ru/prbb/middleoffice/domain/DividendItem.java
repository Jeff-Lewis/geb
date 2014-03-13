/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class DividendItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String security_code;
	private String short_name;
	private String client;
	private String fund;
	private String broker;
	private String account;
	private String currency;
	private String record_date;
	private String quantity;
	private String dividend_per_share;
	private String receive_date;
	private String real_dividend_per_share;
	private String status;
	private String estimate;
	private String real_dividends;
	private String extra_costs_per_share;
	private String tax_value;
	private String country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDividend_per_share() {
		return dividend_per_share;
	}

	public void setDividend_per_share(String dividend_per_share) {
		this.dividend_per_share = dividend_per_share;
	}

	public String getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}

	public String getReal_dividend_per_share() {
		return real_dividend_per_share;
	}

	public void setReal_dividend_per_share(String real_dividend_per_share) {
		this.real_dividend_per_share = real_dividend_per_share;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEstimate() {
		return estimate;
	}

	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}

	public String getReal_dividends() {
		return real_dividends;
	}

	public void setReal_dividends(String real_dividends) {
		this.real_dividends = real_dividends;
	}

	public String getExtra_costs_per_share() {
		return extra_costs_per_share;
	}

	public void setExtra_costs_per_share(String extra_costs_per_share) {
		this.extra_costs_per_share = extra_costs_per_share;
	}

	public String getTax_value() {
		return tax_value;
	}

	public void setTax_value(String tax_value) {
		this.tax_value = tax_value;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DividendItem) {
			return id.equals(((DividendItem) obj).id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return security_code + '(' + id + ')';
	}
}
