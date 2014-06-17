/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

/**
 * @author RBr
 */
public class ResultCode implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final ResultCode LOGIN = new ResultCode(false, "login");
	public static final ResultCode LOGIN_ERROR = new ResultCode(true, "login_error");

	private final boolean success;
	private final String code;

	private ResultCode(boolean success, String code) {
		this.success = success;
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getCode() {
		return code;
	}

}
