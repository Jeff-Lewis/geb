package ru.prbb.jobber.domain;

import java.io.Serializable;

public class AgentItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String host;

	public AgentItem(String host) {
		this.host = host;
		// TODO Auto-generated constructor stub
	}

	public String getHost() {
		return host;
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int hashCode() {
		return host.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentItem other = (AgentItem) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		return true;
	}

}
