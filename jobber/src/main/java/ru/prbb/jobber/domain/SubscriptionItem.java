/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

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
	@Transient
	private Object subscriptionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Object subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	@Transient
	public boolean isRunning() {
		if ("Running".equals(status))
			return true;
		if ("Stopped".equals(status))
			return false;
		throw new RuntimeException("Unknown subscription status: " + status);
	}

	@Transient
	public boolean isStopped() {
		return !isRunning();
	}

	@Override
	public int hashCode() {
		return (name == null) ? 0 : name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubscriptionItem other = (SubscriptionItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubscriptionItem [id=" + id + ", name=" + name + ", comment="
				+ comment + ", status=" + status + "]";
	}
}
