/**
 * 
 */
package ru.prbb.jobber.domain;

import java.sql.Date;

/**
 * @author RBr
 * 
 */
public class CashFlowData {

	public final Long id;
	public final String name;
	public final Date date;

	public CashFlowData(Long id, String name, Date date) {
		this.id = id;
		this.name = name;
		this.date = date;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CashFlowData other = (CashFlowData) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
