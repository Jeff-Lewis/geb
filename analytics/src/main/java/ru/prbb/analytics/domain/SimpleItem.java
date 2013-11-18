/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

/**
 * {id, name}
 * 
 * @author RBr
 * 
 */
public class SimpleItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;

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
