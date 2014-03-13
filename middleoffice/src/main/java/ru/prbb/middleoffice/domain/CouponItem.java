/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 */
@Entity
public class CouponItem implements Serializable {

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
	private Integer quantity;
	private BigDecimal coupon_per_share;
	private String receive_date;
	private BigDecimal real_coupon_per_share;
	private String status;
	private BigDecimal estimate;
	private BigDecimal real_coupons;
	private BigDecimal extra_costs_per_share;
	private BigDecimal tax_value;
	private String country;
	private String oper;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getCoupon_per_share() {
		return coupon_per_share;
	}

	public void setCoupon_per_share(BigDecimal coupon_per_share) {
		this.coupon_per_share = coupon_per_share;
	}

	public String getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}

	public BigDecimal getReal_coupon_per_share() {
		return real_coupon_per_share;
	}

	public void setReal_coupon_per_share(BigDecimal real_coupon_per_share) {
		this.real_coupon_per_share = real_coupon_per_share;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getEstimate() {
		return estimate;
	}

	public void setEstimate(BigDecimal estimate) {
		this.estimate = estimate;
	}

	public BigDecimal getReal_coupons() {
		return real_coupons;
	}

	public void setReal_coupons(BigDecimal real_coupons) {
		this.real_coupons = real_coupons;
	}

	public BigDecimal getExtra_costs_per_share() {
		return extra_costs_per_share;
	}

	public void setExtra_costs_per_share(BigDecimal extra_costs_per_share) {
		this.extra_costs_per_share = extra_costs_per_share;
	}

	public BigDecimal getTax_value() {
		return tax_value;
	}

	public void setTax_value(BigDecimal tax_value) {
		this.tax_value = tax_value;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
}
