/**
 * 
 */
package ru.prbb.agent.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author RBr
 * 
 */
public class SubscriptionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String RUNNING = "Running";
	private static final String STOPPED = "Stopped";
	
	private final Long id;
	private String name;
	private String comment;
	private String status;

	private final Set<SecurityItem> securities = new HashSet<>();

	public SubscriptionItem(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
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

	public Set<SecurityItem> getSecurities() {
		return securities;
	}

	public void start() {
		status = RUNNING;
	}

	public void stop() {
		status = STOPPED;
	}

	public boolean isRunning() {
		if (RUNNING.equals(status))
			return true;
		if (STOPPED.equals(status))
			return false;
		throw new RuntimeException("Unknown subscription status: " + status);
	}

	public boolean isStopped() {
		return !isRunning();
	}

	@Override
	public int hashCode() {
		return (id == null) ? 0 : id.hashCode();
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
