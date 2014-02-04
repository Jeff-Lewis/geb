/**
 * 
 */
package ru.prbb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class SubscriptionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_subscr")
	private Long id;
	@Column(name = "subscription_name")
	private String name;
	@Column(name = "subscription_comment")
	private String comment;
	@Column(name = "subscription_status")
	private String status;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isRunning() {
		if ("Running".equals(status))
			return true;
		if ("Stopped".equals(status))
			return false;
		throw new RuntimeException("Unknown subscription status: " + status);
	}

	public boolean isStopped() {
		if ("Running".equals(status))
			return false;
		if ("Stopped".equals(status))
			return true;
		throw new RuntimeException("Unknown subscription status: " + status);
	}
}
