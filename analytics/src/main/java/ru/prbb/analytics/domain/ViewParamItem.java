/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author RBr
 * 
 */
@Entity
public class ViewParamItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String field_id;
	private String field_mnemonic;
	private String description;
	private String data_license_category;
	private String category;
	private String definition;
	private String comdty;
	private String equity;
	private String muni;
	private String pfd;
	private String m_mkt;
	private String govt;
	private String corp;
	private String indx;
	private String curncy;
	private String mtge;
	private String standard_width;
	private String standard_decimal_places;
	private String field_type;
	private String back_office;
	private String extended_back_office;
	private String production_date;
	private String current_maximum_width;
	private String bval;
	private String bval_blocked;
	private String getfundamentals;
	private String gethistory;
	private String getcompany;
	private String old_mnemonic;
	private String data_license_category_2;
	private String psboopt;

	/**
	 * @return the field_id
	 */
	public String getField_id() {
		return field_id;
	}

	/**
	 * @param field_id
	 *            the field_id to set
	 */
	public void setField_id(String field_id) {
		this.field_id = field_id;
	}

	/**
	 * @return the field_mnemonic
	 */
	public String getField_mnemonic() {
		return field_mnemonic;
	}

	/**
	 * @param field_mnemonic
	 *            the field_mnemonic to set
	 */
	public void setField_mnemonic(String field_mnemonic) {
		this.field_mnemonic = field_mnemonic;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the data_license_category
	 */
	public String getData_license_category() {
		return data_license_category;
	}

	/**
	 * @param data_license_category
	 *            the data_license_category to set
	 */
	public void setData_license_category(String data_license_category) {
		this.data_license_category = data_license_category;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the definition
	 */
	public String getDefinition() {
		return definition;
	}

	/**
	 * @param definition
	 *            the definition to set
	 */
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	/**
	 * @return the comdty
	 */
	public String getComdty() {
		return comdty;
	}

	/**
	 * @param comdty
	 *            the comdty to set
	 */
	public void setComdty(String comdty) {
		this.comdty = comdty;
	}

	/**
	 * @return the equity
	 */
	public String getEquity() {
		return equity;
	}

	/**
	 * @param equity
	 *            the equity to set
	 */
	public void setEquity(String equity) {
		this.equity = equity;
	}

	/**
	 * @return the muni
	 */
	public String getMuni() {
		return muni;
	}

	/**
	 * @param muni
	 *            the muni to set
	 */
	public void setMuni(String muni) {
		this.muni = muni;
	}

	/**
	 * @return the pfd
	 */
	public String getPfd() {
		return pfd;
	}

	/**
	 * @param pfd
	 *            the pfd to set
	 */
	public void setPfd(String pfd) {
		this.pfd = pfd;
	}

	/**
	 * @return the m_mkt
	 */
	public String getM_mkt() {
		return m_mkt;
	}

	/**
	 * @param m_mkt
	 *            the m_mkt to set
	 */
	public void setM_mkt(String m_mkt) {
		this.m_mkt = m_mkt;
	}

	/**
	 * @return the govt
	 */
	public String getGovt() {
		return govt;
	}

	/**
	 * @param govt
	 *            the govt to set
	 */
	public void setGovt(String govt) {
		this.govt = govt;
	}

	/**
	 * @return the corp
	 */
	public String getCorp() {
		return corp;
	}

	/**
	 * @param corp
	 *            the corp to set
	 */
	public void setCorp(String corp) {
		this.corp = corp;
	}

	/**
	 * @return the indx
	 */
	public String getIndx() {
		return indx;
	}

	/**
	 * @param indx
	 *            the indx to set
	 */
	public void setIndx(String indx) {
		this.indx = indx;
	}

	/**
	 * @return the curncy
	 */
	public String getCurncy() {
		return curncy;
	}

	/**
	 * @param curncy
	 *            the curncy to set
	 */
	public void setCurncy(String curncy) {
		this.curncy = curncy;
	}

	/**
	 * @return the mtge
	 */
	public String getMtge() {
		return mtge;
	}

	/**
	 * @param mtge
	 *            the mtge to set
	 */
	public void setMtge(String mtge) {
		this.mtge = mtge;
	}

	/**
	 * @return the standard_width
	 */
	public String getStandard_width() {
		return standard_width;
	}

	/**
	 * @param standard_width
	 *            the standard_width to set
	 */
	public void setStandard_width(String standard_width) {
		this.standard_width = standard_width;
	}

	/**
	 * @return the standard_decimal_places
	 */
	public String getStandard_decimal_places() {
		return standard_decimal_places;
	}

	/**
	 * @param standard_decimal_places
	 *            the standard_decimal_places to set
	 */
	public void setStandard_decimal_places(String standard_decimal_places) {
		this.standard_decimal_places = standard_decimal_places;
	}

	/**
	 * @return the field_type
	 */
	public String getField_type() {
		return field_type;
	}

	/**
	 * @param field_type
	 *            the field_type to set
	 */
	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	/**
	 * @return the back_office
	 */
	public String getBack_office() {
		return back_office;
	}

	/**
	 * @param back_office
	 *            the back_office to set
	 */
	public void setBack_office(String back_office) {
		this.back_office = back_office;
	}

	/**
	 * @return the extended_back_office
	 */
	public String getExtended_back_office() {
		return extended_back_office;
	}

	/**
	 * @param extended_back_office
	 *            the extended_back_office to set
	 */
	public void setExtended_back_office(String extended_back_office) {
		this.extended_back_office = extended_back_office;
	}

	/**
	 * @return the production_date
	 */
	public String getProduction_date() {
		return production_date;
	}

	/**
	 * @param production_date
	 *            the production_date to set
	 */
	public void setProduction_date(String production_date) {
		this.production_date = production_date;
	}

	/**
	 * @return the current_maximum_width
	 */
	public String getCurrent_maximum_width() {
		return current_maximum_width;
	}

	/**
	 * @param current_maximum_width
	 *            the current_maximum_width to set
	 */
	public void setCurrent_maximum_width(String current_maximum_width) {
		this.current_maximum_width = current_maximum_width;
	}

	/**
	 * @return the bval
	 */
	public String getBval() {
		return bval;
	}

	/**
	 * @param bval
	 *            the bval to set
	 */
	public void setBval(String bval) {
		this.bval = bval;
	}

	/**
	 * @return the bval_blocked
	 */
	public String getBval_blocked() {
		return bval_blocked;
	}

	/**
	 * @param bval_blocked
	 *            the bval_blocked to set
	 */
	public void setBval_blocked(String bval_blocked) {
		this.bval_blocked = bval_blocked;
	}

	/**
	 * @return the getfundamentals
	 */
	public String getGetfundamentals() {
		return getfundamentals;
	}

	/**
	 * @param getfundamentals
	 *            the getfundamentals to set
	 */
	public void setGetfundamentals(String getfundamentals) {
		this.getfundamentals = getfundamentals;
	}

	/**
	 * @return the gethistory
	 */
	public String getGethistory() {
		return gethistory;
	}

	/**
	 * @param gethistory
	 *            the gethistory to set
	 */
	public void setGethistory(String gethistory) {
		this.gethistory = gethistory;
	}

	/**
	 * @return the getcompany
	 */
	public String getGetcompany() {
		return getcompany;
	}

	/**
	 * @param getcompany
	 *            the getcompany to set
	 */
	public void setGetcompany(String getcompany) {
		this.getcompany = getcompany;
	}

	/**
	 * @return the old_mnemonic
	 */
	public String getOld_mnemonic() {
		return old_mnemonic;
	}

	/**
	 * @param old_mnemonic
	 *            the old_mnemonic to set
	 */
	public void setOld_mnemonic(String old_mnemonic) {
		this.old_mnemonic = old_mnemonic;
	}

	/**
	 * @return the data_license_category_2
	 */
	public String getData_license_category_2() {
		return data_license_category_2;
	}

	/**
	 * @param data_license_category_2
	 *            the data_license_category_2 to set
	 */
	public void setData_license_category_2(String data_license_category_2) {
		this.data_license_category_2 = data_license_category_2;
	}

	/**
	 * @return the psboopt
	 */
	public String getPsboopt() {
		return psboopt;
	}

	/**
	 * @param psboopt
	 *            the psboopt to set
	 */
	public void setPsboopt(String psboopt) {
		this.psboopt = psboopt;
	}
}
