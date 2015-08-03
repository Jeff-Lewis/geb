package ru.prbb.jobber.domain;

import java.io.Serializable;

import ru.prbb.Utils;

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

	public DictUsersInfoItem(Object[] arr) {
		setLogin(Utils.toString(arr[0]));
		setName(Utils.toString(arr[1]));
		setObject(Utils.toString(arr[2]));
		setPermission(Utils.toString(arr[3]));
	}

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
