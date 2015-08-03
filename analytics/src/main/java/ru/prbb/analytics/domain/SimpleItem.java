/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * {id, name}
 * 
 * @author RBr
 */
@Entity
public class SimpleItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String name;

	public SimpleItem() {
	}

	public SimpleItem(Object[] arr) {
		id = Utils.toLong(arr[0]);
		name = Utils.toString(arr[1]);
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SimpleItem) {
			return id.equals(((SimpleItem) obj).id);
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
