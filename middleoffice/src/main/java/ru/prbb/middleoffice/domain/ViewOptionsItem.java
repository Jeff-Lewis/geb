package ru.prbb.middleoffice.domain;

import java.io.Serializable;

/**
 * Редактирование опционов
 * 
 * @author RBr
 */
public class ViewOptionsItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id_sec;
	private String ticker;
	private String deal_name;
	private String name;
	private String date_insert;

	public Long getId_sec() {
		return id_sec;
	}

	public void setId_sec(Long id_sec) {
		this.id_sec = id_sec;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getDeal_name() {
		return deal_name;
	}

	public void setDeal_name(String deal_name) {
		this.deal_name = deal_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate_insert() {
		return date_insert;
	}

	public void setDate_insert(String date_insert) {
		this.date_insert = date_insert;
	}

}
