/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author BrihlyaevRA
 */
@Entity
public class DictGroupItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "group_id")
	private Long id;
	@Column(name = "group_name")
	private String name;
	@Column(name = "group_comment")
	private String comment;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DictGroupItem [id=").append(id);
		builder.append(", name=").append(name);
		builder.append("]");
		return builder.toString();
	}
}
