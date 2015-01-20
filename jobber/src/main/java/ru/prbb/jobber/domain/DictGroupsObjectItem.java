/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author BrihlyaevRA
 */
@Entity
public class DictGroupsObjectItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "permission_id")
	private Long id;
	@Column(name = "object_id")
	private Long object;
	@Column(name = "object_name")
	private String objectName;
	@Column(name = "permission_comment")
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getObject() {
		return object;
	}

	public void setObject(Long object) {
		this.object = object;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
