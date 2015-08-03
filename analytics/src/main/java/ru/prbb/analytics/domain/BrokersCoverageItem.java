/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;

import ru.prbb.Utils;

/**
 * @author RBr
 * 
 */
public class BrokersCoverageItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id_sec;
	private String security_code;
	private String short_name;
	private String pivot_group;
	@Column(name = "Credit_Suisse")
	private Number credit_Suisse;
	@Column(name = "Goldman_Sachs")
	private Number goldman_Sachs;
	@Column(name = "JP_Morgan")
	private Number jp_Morgan;
	@Column(name = "UBS")
	private Number ubs;
	@Column(name = "Merrill_Lynch")
	private Number merrill_Lynch;
	@Column(name = "Morgan_Stanley")
	private Number morgan_Stanley;
	@Column(name = "Deutsche_Bank")
	private Number deutsche_Bank;

	
	public BrokersCoverageItem() {
	}

	public BrokersCoverageItem(Object[] arr) {
		setId_sec(Utils.toLong(arr[0]));
		setSecurity_code(Utils.toString(arr[1]));
		setShort_name(Utils.toString(arr[2]));
		setPivot_group(Utils.toString(arr[3]));
		setCredit_Suisse(Utils.toInteger(arr[4]));
		setGoldman_Sachs(Utils.toInteger(arr[5]));
		setJp_Morgan(Utils.toInteger(arr[6]));
		setUBS(Utils.toInteger(arr[7]));
		setMerrill_Lynch(Utils.toInteger(arr[8]));
		setMorgan_Stanley(Utils.toInteger(arr[9]));
		setDeutsche_Bank(Utils.toInteger(arr[10]));
	}

	/**
	 * @return the id_sec
	 */
	public Long getId_sec() {
		return id_sec;
	}

	/**
	 * @param id_sec
	 *            the id_sec to set
	 */
	public void setId_sec(Long id_sec) {
		this.id_sec = id_sec;
	}

	/**
	 * @return the security_code
	 */
	public String getSecurity_code() {
		return security_code;
	}

	/**
	 * @param security_code
	 *            the security_code to set
	 */
	public void setSecurity_code(String security_code) {
		this.security_code = security_code;
	}

	/**
	 * @return the short_name
	 */
	public String getShort_name() {
		return short_name;
	}

	/**
	 * @param short_name
	 *            the short_name to set
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	/**
	 * @return the pivot_group
	 */
	public String getPivot_group() {
		return pivot_group;
	}

	/**
	 * @param pivot_group
	 *            the pivot_group to set
	 */
	public void setPivot_group(String pivot_group) {
		this.pivot_group = pivot_group;
	}

	/**
	 * @return the credit_Suisse
	 */
	public Number getCredit_Suisse() {
		return credit_Suisse;
	}

	/**
	 * @param credit_Suisse
	 *            the credit_Suisse to set
	 */
	public void setCredit_Suisse(Number credit_Suisse) {
		this.credit_Suisse = credit_Suisse;
	}

	/**
	 * @return the goldman_Sachs
	 */
	public Number getGoldman_Sachs() {
		return goldman_Sachs;
	}

	/**
	 * @param goldman_Sachs
	 *            the goldman_Sachs to set
	 */
	public void setGoldman_Sachs(Number goldman_Sachs) {
		this.goldman_Sachs = goldman_Sachs;
	}

	/**
	 * @return the jp_Morgan
	 */
	public Number getJp_Morgan() {
		return jp_Morgan;
	}

	/**
	 * @param jp_Morgan
	 *            the jp_Morgan to set
	 */
	public void setJp_Morgan(Number jp_Morgan) {
		this.jp_Morgan = jp_Morgan;
	}

	/**
	 * @return the ubs
	 */
	public Number getUBS() {
		return ubs;
	}

	/**
	 * @param ubs
	 *            the ubs to set
	 */
	public void setUBS(Number ubs) {
		this.ubs = ubs;
	}

	/**
	 * @return the merrill_Lynch
	 */
	public Number getMerrill_Lynch() {
		return merrill_Lynch;
	}

	/**
	 * @param merrill_Lynch
	 *            the merrill_Lynch to set
	 */
	public void setMerrill_Lynch(Number merrill_Lynch) {
		this.merrill_Lynch = merrill_Lynch;
	}

	/**
	 * @return the morgan_Stanley
	 */
	public Number getMorgan_Stanley() {
		return morgan_Stanley;
	}

	/**
	 * @param morgan_Stanley
	 *            the morgan_Stanley to set
	 */
	public void setMorgan_Stanley(Number morgan_Stanley) {
		this.morgan_Stanley = morgan_Stanley;
	}

	/**
	 * @return the deutsche_Bank
	 */
	public Number getDeutsche_Bank() {
		return deutsche_Bank;
	}

	/**
	 * @param deutsche_Bank
	 *            the deutsche_Bank to set
	 */
	public void setDeutsche_Bank(Number deutsche_Bank) {
		this.deutsche_Bank = deutsche_Bank;
	}
}
