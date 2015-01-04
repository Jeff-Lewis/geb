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
public class LogUserActionItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "hist_id")
	private Long id;
	@Column(name = "user_login")
	private String userLogin;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "user_ip")
	private String userIp;
	@Column(name = "user_email")
	private String userEmail;
	@Column(name = "command")
	private String command;
	@Column(name = "date_insert")
	private String dateInsert;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserLogin() {
		return userLogin;
	}
	
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserIp() {
		return userIp;
	}
	
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getDateInsert() {
		return dateInsert;
	}
	
	public void setDateInsert(String dateInsert) {
		this.dateInsert = dateInsert;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(80);
		builder.append("LogUserActionItem [id=").append(id);
		builder.append(", dateInsert=").append(dateInsert);
		builder.append(", userLogin=").append(userLogin);
		builder.append(", command=").append(command).append("]");
		return builder.toString();
	}
}
