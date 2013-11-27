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
public class ViewSubscriptionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_subscr;
	private String subscription_name;
	private String subscription_comment;
	private String subscription_status;

	/**
	 * @return the id_subscr
	 */
	public Long getId_subscr() {
		return id_subscr;
	}

	/**
	 * @param id_subscr
	 *            the id_subscr to set
	 */
	public void setId_subscr(Long id_subscr) {
		this.id_subscr = id_subscr;
	}

	/**
	 * @return the subscription_name
	 */
	public String getSubscription_name() {
		return subscription_name;
	}

	/**
	 * @param subscription_name
	 *            the subscription_name to set
	 */
	public void setSubscription_name(String subscription_name) {
		this.subscription_name = subscription_name;
	}

	/**
	 * @return the subscription_comment
	 */
	public String getSubscription_comment() {
		return subscription_comment;
	}

	/**
	 * @param subscription_comment
	 *            the subscription_comment to set
	 */
	public void setSubscription_comment(String subscription_comment) {
		this.subscription_comment = subscription_comment;
	}

	/**
	 * @return the subscription_status
	 */
	public String getSubscription_status() {
		return subscription_status;
	}

	/**
	 * @param subscription_status
	 *            the subscription_status to set
	 */
	public void setSubscription_status(String subscription_status) {
		this.subscription_status = subscription_status;
	}
}
