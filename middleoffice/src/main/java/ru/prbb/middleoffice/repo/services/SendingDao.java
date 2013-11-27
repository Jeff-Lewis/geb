/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;
import java.util.Map;

import ru.prbb.middleoffice.domain.SimpleItem;

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
	List<Map<String, Object>> execute(String text, String recp, String recm);

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
