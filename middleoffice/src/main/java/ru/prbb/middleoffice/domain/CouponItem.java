/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.sql.Date;

import ru.prbb.Utils;

/**
 * @author RBr
 */
public class CouponItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id_sec;
	private String security_code;
	private String short_name;
	private String client;
	private String fund;
	private String broker;
	private String account;
	private String currency;
	private Date record_date;
	private Number quantity;
	private Number coupon_per_share;
	private Date receive_date;
	private Number real_coupon_per_share;
	private String status;
	private Number estimate;
	private Number real_coupons;
	private Number extra_costs;
	private Number tax_value;
	private String country;
	private String oper;

	
	public CouponItem() {
	}

	public CouponItem(Object[] arr) {
		int i = 0;
		setId_sec(Utils.toLong(arr[i++]));
		setSecurity_code(Utils.toString(arr[i++]));
		setShort_name(Utils.toString(arr[i++]));
		setClient(Utils.toString(arr[i++]));
		setFund(Utils.toString(arr[i++]));
		setBroker(Utils.toString(arr[i++]));
		setAccount(Utils.toString(arr[i++]));
		setCurrency(Utils.toString(arr[i++]));
		setRecord_date(Utils.toSqlDate(arr[i++]));
		setQuantity(Utils.toInteger(arr[i++]));
		setCoupon_per_share(Utils.toDouble(arr[i++]));
		setReceive_date(Utils.toSqlDate(arr[i++]));
		setReal_coupon_per_share(Utils.toDouble(arr[i++]));
		setStatus(Utils.toString(arr[i++]));
		setEstimate(Utils.toDouble(arr[i++]));
		setReal_coupons(Utils.toDouble(arr[i++]));
		setExtra_costs(Utils.toDouble(arr[i++]));
		setTax_value(Utils.toDouble(arr[i++]));
		setCountry(Utils.toString(arr[i++]));
		setOper(Utils.toString(arr[i++]));
	}
	
	public Long getId_sec() {
		return id_sec;
	}

	public void setId_sec(Long id_sec) {
		this.id_sec = id_sec;
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

	public Date getRecord_date() {
		return record_date;
	}

	public void setRecord_date(Date record_date) {
		this.record_date = record_date;
	}

	public Number getQuantity() {
		return quantity;
	}

	public void setQuantity(Number quantity) {
		this.quantity = quantity;
	}

	public Number getCoupon_per_share() {
		return coupon_per_share;
	}

	public void setCoupon_per_share(Number coupon_per_share) {
		this.coupon_per_share = coupon_per_share;
	}

	public Date getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(Date receive_date) {
		this.receive_date = receive_date;
	}

	public Number getReal_coupon_per_share() {
		return real_coupon_per_share;
	}

	public void setReal_coupon_per_share(Number real_coupon_per_share) {
		this.real_coupon_per_share = real_coupon_per_share;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Number getEstimate() {
		return estimate;
	}

	public void setEstimate(Number estimate) {
		this.estimate = estimate;
	}

	public Number getReal_coupons() {
		return real_coupons;
	}

	public void setReal_coupons(Number real_coupons) {
		this.real_coupons = real_coupons;
	}

	public Number getExtra_costs() {
		return extra_costs;
	}

	public void setExtra_costs(Number extra_costs) {
		this.extra_costs = extra_costs;
	}

	public Number getTax_value() {
		return tax_value;
	}

	public void setTax_value(Number tax_value) {
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
