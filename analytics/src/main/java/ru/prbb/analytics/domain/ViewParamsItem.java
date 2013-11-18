/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class ViewParamsItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long param_id;
	private String blm_id;
	private String code;
	private String name;

	/**
	 * @return the param_id
	 */
	public Long getParam_id() {
		return param_id;
	}

	/**
	 * @param param_id
	 *            the param_id to set
	 */
	public void setParam_id(Long param_id) {
		this.param_id = param_id;
	}

	/**
	 * @return the blm_id
	 */
	public String getBlm_id() {
		return blm_id;
	}

	/**
	 * @param blm_id
	 *            the blm_id to set
	 */
	public void setBlm_id(String blm_id) {
		this.blm_id = blm_id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
