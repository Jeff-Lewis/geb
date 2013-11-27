/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class BrokersEstimateChangeItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String Security;
	private String Broker;
	private String targetChange;
	private String pcntChange;
	private String Recommendation;
	private String DateInsert;

	/**
	 * @return the security
	 */
	public String getSecurity() {
		return Security;
	}

	/**
	 * @param security
	 *            the security to set
	 */
	public void setSecurity(String security) {
		Security = security;
	}

	/**
	 * @return the broker
	 */
	public String getBroker() {
		return Broker;
	}

	/**
	 * @param broker
	 *            the broker to set
	 */
	public void setBroker(String broker) {
		Broker = broker;
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
		return Recommendation;
	}

	/**
	 * @param recommendation
	 *            the recommendation to set
	 */
	public void setRecommendation(String recommendation) {
		Recommendation = recommendation;
	}

	/**
	 * @return the dateInsert
	 */
	public String getDateInsert() {
		return DateInsert;
	}

	/**
	 * @param dateInsert
	 *            the dateInsert to set
	 */
	public void setDateInsert(String dateInsert) {
		DateInsert = dateInsert;
	}
}
