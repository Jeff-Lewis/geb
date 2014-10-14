/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

import java.util.List;
import java.util.Map;

/**
 * Загрузка доходности облигаций
 * 
 * @author RBr
 * 
 */
public interface LoadBondYeildDao {

	/**
	 * @param securities
	 * @param answer
	 * @return
	 */
	List<Map<String, Object>> execute(String[] securities, Map<String, Map<String, Map<String, String>>> answer);

}
