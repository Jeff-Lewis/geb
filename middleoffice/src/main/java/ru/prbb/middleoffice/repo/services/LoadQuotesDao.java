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
	 * @param securities
	 * @param answer
	 * @return
	 */
	List<Map<String, Object>> execute(String[] securities, Map<String, Map<String, Map<String, String>>> answer);

}
