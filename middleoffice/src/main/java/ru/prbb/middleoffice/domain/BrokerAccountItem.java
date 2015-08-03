/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * @author RBr
 */
@Entity
public class BrokerAccountItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String name;
	private String broker;
	private String client;
	private String comment;

	public BrokerAccountItem() {
	}

	public BrokerAccountItem(Object[] arr) {
		int idx = 0;
		id = Utils.toLong(arr[idx++]);
		name = Utils.toString(arr[idx++]);
		broker = Utils.toString(arr[idx++]);
		client = Utils.toString(arr[idx++]);
		comment = Utils.toString(arr[idx++]);
	}

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

	public String getBroker() {
		return broker;
	}

	public void setBroker(String broker) {
		this.broker = broker;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BrokerAccountItem) {
			return id.equals(((BrokerAccountItem) obj).id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return name + '(' + id + ')';
	}
}
