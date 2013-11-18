/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;
import java.util.Map;

/**
 * Загрузка котировок
 * 
 * @author RBr
 * 
 */
public interface LoadQuotesDao {

	/**
	 * @param dateStart
	 * @param dateEnd
	 * @param securities
	 * @return
	 */
	List<Map<String, Object>> execute(String dateStart, String dateEnd, String[] securities);

}
