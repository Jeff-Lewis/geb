package ru.prbb.agent.model;

/**
 * @author RBr
 */
public class SecForJobRequest {

	public final String code;
	public final String iso;

	public SecForJobRequest(String security_code, String iso) {
		this.code = security_code;
		this.iso = iso;
	}

	@Override
	public String toString() {
		return iso + ':' + code;
	}
}
