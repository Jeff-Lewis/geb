/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import ru.prbb.Utils;

/**
 * @author RBr
 * 
 */
@Entity
public class CompaniesFileItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id_doc;
	private String file_type;
	private String file_name;
	private String insert_date;

	public CompaniesFileItem() {
	}

	public CompaniesFileItem(Object[] arr) {
		id_doc = Utils.toLong(arr[0]);
		file_type = Utils.toString(arr[1]);
		file_name = Utils.toString(arr[2]);
		insert_date = Utils.toString(arr[3]);
	}

	/**
	 * @return the id_doc
	 */
	public Long getId_doc() {
		return id_doc;
	}

	/**
	 * @param id_doc
	 *            the id_doc to set
	 */
	public void setId_doc(Long id_doc) {
		this.id_doc = id_doc;
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
	 * @return the insert_date
	 */
	public String getInsert_date() {
		return insert_date;
	}

	/**
	 * @param insert_date
	 *            the insert_date to set
	 */
	public void setInsert_date(String insert_date) {
		this.insert_date = insert_date;
	}

}
