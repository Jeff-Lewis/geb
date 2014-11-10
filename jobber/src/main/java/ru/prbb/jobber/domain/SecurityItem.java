/**
 * 
 */
package ru.prbb.jobber.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class SecurityItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_sec")
	private Long id;
	@Column(name = "security_code")
	private String code;
	@Column(name = "security_name")
	private String name;

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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SecurityItem [");
		builder.append("id=").append(id).append(", ");
		builder.append("code=").append(code).append(", ");
		if (name != null) {
			builder.append("name=").append(name);
		}
		builder.append("]");
		return builder.toString();
	}
}
