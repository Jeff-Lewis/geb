/**
 * 
 */
package ru.prbb.analytics.domain;

import java.io.Serializable;

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
	private String Credit_Suisse;
	private String Goldman_Sachs;
	private String JP_Morgan;
	private String UBS;
	private String Merrill_Lynch;
	private String Morgan_Stanley;
	private String Deutsche_Bank;

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
	public String getCredit_Suisse() {
		return Credit_Suisse;
	}

	/**
	 * @param credit_Suisse
	 *            the credit_Suisse to set
	 */
	public void setCredit_Suisse(String credit_Suisse) {
		Credit_Suisse = credit_Suisse;
	}

	/**
	 * @return the goldman_Sachs
	 */
	public String getGoldman_Sachs() {
		return Goldman_Sachs;
	}

	/**
	 * @param goldman_Sachs
	 *            the goldman_Sachs to set
	 */
	public void setGoldman_Sachs(String goldman_Sachs) {
		Goldman_Sachs = goldman_Sachs;
	}

	/**
	 * @return the jP_Morgan
	 */
	public String getJP_Morgan() {
		return JP_Morgan;
	}

	/**
	 * @param jP_Morgan
	 *            the jP_Morgan to set
	 */
	public void setJP_Morgan(String jP_Morgan) {
		JP_Morgan = jP_Morgan;
	}

	/**
	 * @return the uBS
	 */
	public String getUBS() {
		return UBS;
	}

	/**
	 * @param uBS
	 *            the uBS to set
	 */
	public void setUBS(String uBS) {
		UBS = uBS;
	}

	/**
	 * @return the merrill_Lynch
	 */
	public String getMerrill_Lynch() {
		return Merrill_Lynch;
	}

	/**
	 * @param merrill_Lynch
	 *            the merrill_Lynch to set
	 */
	public void setMerrill_Lynch(String merrill_Lynch) {
		Merrill_Lynch = merrill_Lynch;
	}

	/**
	 * @return the morgan_Stanley
	 */
	public String getMorgan_Stanley() {
		return Morgan_Stanley;
	}

	/**
	 * @param morgan_Stanley
	 *            the morgan_Stanley to set
	 */
	public void setMorgan_Stanley(String morgan_Stanley) {
		Morgan_Stanley = morgan_Stanley;
	}

	/**
	 * @return the deutsche_Bank
	 */
	public String getDeutsche_Bank() {
		return Deutsche_Bank;
	}

	/**
	 * @param deutsche_Bank
	 *            the deutsche_Bank to set
	 */
	public void setDeutsche_Bank(String deutsche_Bank) {
		Deutsche_Bank = deutsche_Bank;
	}
}
