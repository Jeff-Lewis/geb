/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;
import java.util.Map;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 * 
 */
public interface LoadCashFlowDao {

	/**
	 * @param securities
	 * @return
	 */
	List<Map<String, Object>> execute(String[] securities);

}
