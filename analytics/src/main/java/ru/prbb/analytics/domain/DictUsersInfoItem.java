package ru.prbb.analytics.domain;

import java.io.Serializable;

/**
 * @author BrihlyaevRA
 */
public class DictUsersInfoItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private transient String login;
	@SuppressWarnings("unused")
	private transient String name;
	private String object;
	private String permission;

	public void setLogin(String login) {
		this.login = login;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
