package ru.prbb;

/**
 * @author RBr
 */
public class ArmUserInfo {

	private String name;
	private String host;

	public ArmUserInfo(String name, String host) {
		this.name = name;
		this.host = host;
	}

	public String getName() {
		return name;
	}

	public String getHost() {
		return host;
	}

}
