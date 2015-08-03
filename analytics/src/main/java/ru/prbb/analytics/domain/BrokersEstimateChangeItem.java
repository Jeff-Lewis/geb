/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import ru.prbb.Utils;

/**
 * @author RBr
 * 
 */
public class BrokersEstimateChangeItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String security;
	private String broker;
	private String targetChange;
	private String pcntChange;
	private String recommendation;
	private String dateInsert;

	
	public BrokersEstimateChangeItem() {
	}

	public BrokersEstimateChangeItem(Object[] arr) {
		setSecurity(Utils.toString(arr[0]));
		setBroker(Utils.toString(arr[1]));
		setTargetChange(Utils.toString(arr[2]));
		setPcntChange(Utils.toString(arr[3]));
		setRecommendation(Utils.toString(arr[4]));
		setDateInsert(Utils.toString(arr[5]));
	}

	/**
	 * @return the security
	 */
	public String getSecurity() {
		return security;
	}

	/**
	 * @param security
	 *            the security to set
	 */
	public void setSecurity(String security) {
		this.security = security;
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
	 * @return the targetChange
	 */
	public String getTargetChange() {
		return targetChange;
	}

	/**
	 * @param targetChange
	 *            the targetChange to set
	 */
	public void setTargetChange(String targetChange) {
		this.targetChange = targetChange;
	}

	/**
	 * @return the pcntChange
	 */
	public String getPcntChange() {
		return pcntChange;
	}

	/**
	 * @param pcntChange
	 *            the pcntChange to set
	 */
	public void setPcntChange(String pcntChange) {
		this.pcntChange = pcntChange;
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
	 * @return the dateInsert
	 */
	public String getDateInsert() {
		return dateInsert;
	}

	/**
	 * @param dateInsert
	 *            the dateInsert to set
	 */
	public void setDateInsert(String dateInsert) {
		this.dateInsert = dateInsert;
	}
}
