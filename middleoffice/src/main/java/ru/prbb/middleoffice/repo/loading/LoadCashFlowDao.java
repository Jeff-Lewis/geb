/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;
import java.util.Map;

import ru.prbb.middleoffice.domain.SecurityCashFlowItem;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 * 
 */
public interface LoadCashFlowDao {

	/**
	 * @param answer
	 * @return
	 */
	List<Map<String, Object>> execute(List<Map<String, Object>> answer);

	/**
	 * @return
	 */
	List<SecurityCashFlowItem> findAllSecurities();

}
