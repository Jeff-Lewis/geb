/**
 * 
 */
package ru.prbb.agent.bloomberg.model;

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
}