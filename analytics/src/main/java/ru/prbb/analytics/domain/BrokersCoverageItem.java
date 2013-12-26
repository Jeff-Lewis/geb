/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

import javax.persistence.Column;

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
	private Integer credit_Suisse;
	@Column(name = "Goldman_Sachs")
	private Integer goldman_Sachs;
	@Column(name = "JP_Morgan")
	private Integer jp_Morgan;
	@Column(name = "UBS")
	private Integer ubs;
	@Column(name = "Merrill_Lynch")
	private Integer merrill_Lynch;
	@Column(name = "Morgan_Stanley")
	private Integer morgan_Stanley;
	@Column(name = "Deutsche_Bank")
	private Integer deutsche_Bank;

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
	public Integer getCredit_Suisse() {
		return credit_Suisse;
	}

	/**
	 * @param credit_Suisse
	 *            the credit_Suisse to set
	 */
	public void setCredit_Suisse(Integer credit_Suisse) {
		this.credit_Suisse = credit_Suisse;
	}

	/**
	 * @return the goldman_Sachs
	 */
	public Integer getGoldman_Sachs() {
		return goldman_Sachs;
	}

	/**
	 * @param goldman_Sachs
	 *            the goldman_Sachs to set
	 */
	public void setGoldman_Sachs(Integer goldman_Sachs) {
		this.goldman_Sachs = goldman_Sachs;
	}

	/**
	 * @return the jp_Morgan
	 */
	public Integer getJp_Morgan() {
		return jp_Morgan;
	}

	/**
	 * @param jp_Morgan
	 *            the jp_Morgan to set
	 */
	public void setJp_Morgan(Integer jp_Morgan) {
		this.jp_Morgan = jp_Morgan;
	}

	/**
	 * @return the ubs
	 */
	public Integer getUBS() {
		return ubs;
	}

	/**
	 * @param ubs
	 *            the ubs to set
	 */
	public void setUBS(Integer ubs) {
		this.ubs = ubs;
	}

	/**
	 * @return the merrill_Lynch
	 */
	public Integer getMerrill_Lynch() {
		return merrill_Lynch;
	}

	/**
	 * @param merrill_Lynch
	 *            the merrill_Lynch to set
	 */
	public void setMerrill_Lynch(Integer merrill_Lynch) {
		this.merrill_Lynch = merrill_Lynch;
	}

	/**
	 * @return the morgan_Stanley
	 */
	public Integer getMorgan_Stanley() {
		return morgan_Stanley;
	}

	/**
	 * @param morgan_Stanley
	 *            the morgan_Stanley to set
	 */
	public void setMorgan_Stanley(Integer morgan_Stanley) {
		this.morgan_Stanley = morgan_Stanley;
	}

	/**
	 * @return the deutsche_Bank
	 */
	public Integer getDeutsche_Bank() {
		return deutsche_Bank;
	}

	/**
	 * @param deutsche_Bank
	 *            the deutsche_Bank to set
	 */
	public void setDeutsche_Bank(Integer deutsche_Bank) {
		this.deutsche_Bank = deutsche_Bank;
	}
}
