/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import ru.prbb.analytics.domain.SendingItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Рассылка E-mail и SMS
 * 
 * @author RBr
 * 
 */
public interface SendingDao {

	/**
	 * @param text
	 * @param recp
	 * @param recm
	 * @return
	 */
	List<SendingItem> execute(String text, String recp, String recm);

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
