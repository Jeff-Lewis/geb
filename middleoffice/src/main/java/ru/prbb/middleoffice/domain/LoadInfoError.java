package ru.prbb.middleoffice.domain;

import java.io.Serializable;

public class LoadInfoError implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Long row;
	private final String error;

	public LoadInfoError(long row, String message) {
		this.row = row;
		this.error = message;
	}

	public Long getRow() {
		return row;
	}

	public String getError() {
		return error;
	}

	@Override
	public int hashCode() {
		return row.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof LoadInfoError)) {
			return false;
		}
		LoadInfoError other = (LoadInfoError) obj;
		return row.equals(other.row);
	}

}
