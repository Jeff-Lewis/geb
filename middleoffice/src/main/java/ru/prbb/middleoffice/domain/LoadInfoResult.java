/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author RBr
 */
public class LoadInfoResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer totalRecords;
	private Integer totalErrors;
	private List<LoadInfoError> errors;

	public LoadInfoResult(int totalRecords, int totalErrors) {
		this.totalRecords = totalRecords;
		this.totalErrors = totalErrors;
		this.errors = new ArrayList<>(totalErrors);
	}

	public boolean addError(long row, String message) {
		return errors.add(new LoadInfoError(row, message));
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public Integer getTotalErrors() {
		return totalErrors;
	}

	public List<LoadInfoError> getErrors() {
		Collections.sort(errors, new Comparator<LoadInfoError>() {

			@Override
			public int compare(LoadInfoError o1, LoadInfoError o2) {
				return o1.getRow().compareTo(o2.getRow());
			}
		});
		return errors;
	}

}
