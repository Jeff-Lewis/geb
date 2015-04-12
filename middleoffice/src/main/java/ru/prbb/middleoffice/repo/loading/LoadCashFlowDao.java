/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

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
	List<Map<String, String>> execute(List<Map<String, String>> answer);

	/**
	 * @return
	 */
	List<SecurityCashFlowItem> findAllSecurities();

}
