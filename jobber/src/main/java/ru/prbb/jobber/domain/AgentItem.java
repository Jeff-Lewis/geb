package ru.prbb.jobber.domain;

import java.io.Serializable;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author RBr
 */
public class AgentItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String ONLINE = "ONLINE";
	private static final String TIMEOUT = "TIMEOUT";

	private InetAddress host;
	private int port;
	private long time;
	private String status;

	public AgentItem(InetAddress host, Integer port) {
		this.host = host;
		this.port = port;
		time = System.currentTimeMillis();
		status = ONLINE;
	}

	public String getHost() {
		return host.getHostAddress();
	}

	public int getPort() {
		return port;
	}

	public void setStatus(String status) {
		this.status = (status != null) ? status : ONLINE;
	}

	public String getStatus() {
		return status;
	}

	
	public String getTime() {
		return new SimpleDateFormat().format(new Date(time));
	}

	public void update() {
		time = System.currentTimeMillis();
		if (TIMEOUT.equals(status)) {
			status = ONLINE;
		}
	}

	public boolean isActive() {
		if ((time + (7 * 60 * 1000)) < System.currentTimeMillis()) {
			status = TIMEOUT;
		}
		return !(TIMEOUT.equals(status));
	}

	public boolean isNotBusy() {
		return ONLINE.equals(status);
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

	@Override
	public String toString() {
		return "AgentItem [" + host + ":" + port + "]";
	}

}
