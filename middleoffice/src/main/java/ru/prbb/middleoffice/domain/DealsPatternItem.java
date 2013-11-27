/**
 * 
 */
package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class DealsPatternItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String file_name;
	private String file_type;
	private String date_insert;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the file_name
	 */
	public String getFile_name() {
		return file_name;
	}

	/**
	 * @param file_name
	 *            the file_name to set
	 */
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	/**
	 * @return the file_type
	 */
	public String getFile_type() {
		return file_type;
	}

	/**
	 * @param file_type
	 *            the file_type to set
	 */
	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	/**
	 * @return the date_insert
	 */
	public String getDate_insert() {
		return date_insert;
	}

	/**
	 * @param date_insert
	 *            the date_insert to set
	 */
	public void setDate_insert(String date_insert) {
		this.date_insert = date_insert;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DealsPatternItem) {
			return id.equals(((DealsPatternItem) obj).id);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return file_name + '(' + id + ')';
	}
}
