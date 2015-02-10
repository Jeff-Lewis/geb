package ru.prbb.jobber.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author RBr
 */
@Entity
public class SendMessageItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "msg_type")
	private Number type;
	@Column(name = "msg_addr")
	private String addrs;
	@Column(name = "msg_subj")
	private String subj;
	@Column(name = "msg_text")
	private String text;

	public Number getType() {
		return type;
	}

	public void setType(Number type) {
		this.type = type;
	}

	public String getAddrs() {
		return addrs;
	}

	public void setAddrs(String addrs) {
		this.addrs = addrs;
	}

	public String getSubj() {
		return subj;
	}

	public void setSubj(String subj) {
		this.subj = subj;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getAddrsArray() {
		return addrs.split(";");
	}
}
