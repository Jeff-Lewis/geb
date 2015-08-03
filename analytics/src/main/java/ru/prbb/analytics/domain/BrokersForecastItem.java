/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * @author RBr
 * 
 */
//@Entity
public class BrokersForecastItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_sec;
	private String security_code;
	private String short_name;
	private String pivot_group;
	private String broker;
	private Number EPS1Q;
	private Number EPS2Q;
	private Number EPS3Q;
	private Number EPS4Q;
	private Number EPS1CY;
	private Number EPS2CY;
	private Number TargetConsensus12m;
	private Number TargetConsensus;
	private String recommendation;
	private String period;
	private String target_date;
	private String currency;
	private String date_insert;

	
	public BrokersForecastItem() {
	}

	public BrokersForecastItem(Object[] arr) {
		setId_sec(Utils.toLong(arr[0]));
		setSecurity_code(Utils.toString(arr[1]));
		setShort_name(Utils.toString(arr[2]));
		setPivot_group(Utils.toString(arr[3]));
		setBroker(Utils.toString(arr[4]));
		setEPS1Q(Utils.toDouble(arr[5]));
		setEPS2Q(Utils.toDouble(arr[6]));
		setEPS3Q(Utils.toDouble(arr[7]));
		setEPS4Q(Utils.toDouble(arr[8]));
		setEPS1CY(Utils.toDouble(arr[9]));
		setEPS2CY(Utils.toDouble(arr[10]));
		setTargetConsensus12m(Utils.toDouble(arr[11]));
		setTargetConsensus(Utils.toDouble(arr[12]));
		setRecommendation(Utils.toString(arr[13]));
		setPeriod(Utils.toString(arr[14]));
		setTarget_date(Utils.toDate(arr[15]));
		setCurrency(Utils.toString(arr[16]));
		setDate_insert(Utils.toString(arr[17]));
	}

	/**
	 * @return the id_sec
	 */
	public Long getId_sec() {
		return id_sec;
	}

	/**
	 * @param id_sec
	 *            the id_sec to set
	 */
	public void setId_sec(Long id_sec) {
		this.id_sec = id_sec;
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
	 * @return the pivot_group
	 */
	public String getPivot_group() {
		return pivot_group;
	}

	/**
	 * @param pivot_group
	 *            the pivot_group to set
	 */
	public void setPivot_group(String pivot_group) {
		this.pivot_group = pivot_group;
	}

	/**
	 * @return the broker
	 */
	public String getBroker() {
		return broker;
	}

	/**
	 * @param broker
	 *            the broker to set
	 */
	public void setBroker(String broker) {
		this.broker = broker;
	}

	/**
	 * @return the ePS1Q
	 */
	public Number getEPS1Q() {
		return EPS1Q;
	}

	/**
	 * @param ePS1Q
	 *            the ePS1Q to set
	 */
	public void setEPS1Q(Number ePS1Q) {
		EPS1Q = ePS1Q;
	}

	/**
	 * @return the ePS2Q
	 */
	public Number getEPS2Q() {
		return EPS2Q;
	}

	/**
	 * @param ePS2Q
	 *            the ePS2Q to set
	 */
	public void setEPS2Q(Number ePS2Q) {
		EPS2Q = ePS2Q;
	}

	/**
	 * @return the ePS3Q
	 */
	public Number getEPS3Q() {
		return EPS3Q;
	}

	/**
	 * @param ePS3Q
	 *            the ePS3Q to set
	 */
	public void setEPS3Q(Number ePS3Q) {
		EPS3Q = ePS3Q;
	}

	/**
	 * @return the ePS4Q
	 */
	public Number getEPS4Q() {
		return EPS4Q;
	}

	/**
	 * @param ePS4Q
	 *            the ePS4Q to set
	 */
	public void setEPS4Q(Number ePS4Q) {
		EPS4Q = ePS4Q;
	}

	/**
	 * @return the ePS1CY
	 */
	public Number getEPS1CY() {
		return EPS1CY;
	}

	/**
	 * @param ePS1CY
	 *            the ePS1CY to set
	 */
	public void setEPS1CY(Number ePS1CY) {
		EPS1CY = ePS1CY;
	}

	/**
	 * @return the ePS2CY
	 */
	public Number getEPS2CY() {
		return EPS2CY;
	}

	/**
	 * @param ePS2CY
	 *            the ePS2CY to set
	 */
	public void setEPS2CY(Number ePS2CY) {
		EPS2CY = ePS2CY;
	}

	/**
	 * @return the targetConsensus12m
	 */
	public Number getTargetConsensus12m() {
		return TargetConsensus12m;
	}

	/**
	 * @param targetConsensus12m
	 *            the targetConsensus12m to set
	 */
	public void setTargetConsensus12m(Number targetConsensus12m) {
		TargetConsensus12m = targetConsensus12m;
	}

	/**
	 * @return the targetConsensus
	 */
	public Number getTargetConsensus() {
		return TargetConsensus;
	}

	/**
	 * @param targetConsensus
	 *            the targetConsensus to set
	 */
	public void setTargetConsensus(Number targetConsensus) {
		TargetConsensus = targetConsensus;
	}

	/**
	 * @return the recommendation
	 */
	public String getRecommendation() {
		return recommendation;
	}

	/**
	 * @param recommendation
	 *            the recommendation to set
	 */
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * @param period
	 *            the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * @return the target_date
	 */
	public String getTarget_date() {
		return target_date;
	}

	/**
	 * @param target_date
	 *            the target_date to set
	 */
	public void setTarget_date(String target_date) {
		this.target_date = target_date;
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
	 * @return the date_insert
	 */
	public String getDate_insert() {
		return date_insert;
	}

	/**
	 * @param date_insert
	 *            the date_insert to set
	 */
	public void setDate_insert(String date_insert) {
		this.date_insert = date_insert;
	}
}
