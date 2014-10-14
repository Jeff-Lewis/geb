package ru.prbb.middleoffice.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 */
@Entity
public class LoadCurrenciesItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "cur_id")
	private Long id;
	@Column(name = "code")
	private String code;
	@Column(name = "iso")
	private String iso;
	@Column(name = "blm_query_code")
	private String bloomberg_code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getBloomberg_code() {
		return bloomberg_code;
	}

	public void setBloomberg_code(String bloomberg_code) {
		this.bloomberg_code = bloomberg_code;
	}

}
