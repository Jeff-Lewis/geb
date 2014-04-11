/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.SendingItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Рассылка E-mail и SMS
 * 
 * @author RBr
 */
public interface SendingDao {

	/**
	 * @param group
	 * @return
	 */
	List<String> getMailByGroup(String group);

	/**
	 * @param group
	 * @return
	 */
	List<String> getPhoneByGroup(String group);

	/**
	 * @param text
	 * @param email
	 * @return
	 */
	SendingItem sendMail(String text, String email);

	/**
	 * @param text
	 * @param phone
	 * @return
	 */
	SendingItem sendSms(String text, String phone);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboPhone(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findComboMail(String query);

	/**
	 * @return
	 */
	String getAnalitic();

	/**
	 * @return
	 */
	String getTrader();

}
