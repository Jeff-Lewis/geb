/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

/**
 * @author RBr
 * 
 */
public class CompaniesExceptionItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String exception;
	private String comment;

	/**
	 * @return the exception
	 */
	public String getException() {
		return exception;
	}

	/**
	 * @param exception
	 *            the exception to set
	 */
	public void setException(String exception) {
		this.exception = exception;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
