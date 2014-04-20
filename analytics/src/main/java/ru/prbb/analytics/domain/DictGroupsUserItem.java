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
public class DictGroupsUserItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "user_id")
	private Long id;
	@Column(name = "user_name")
	private String name;
	@Column(name = "user_login")
	private String login;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserItem [id=").append(id);
		builder.append(", login=").append(login);
		builder.append(", name=").append(name);
		builder.append("]");
		return builder.toString();
	}
}
