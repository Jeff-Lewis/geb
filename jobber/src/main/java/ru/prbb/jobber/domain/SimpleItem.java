/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * {id, name}
 * 
 * @author RBr
 * 
 */
@Entity
public class SimpleItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
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
