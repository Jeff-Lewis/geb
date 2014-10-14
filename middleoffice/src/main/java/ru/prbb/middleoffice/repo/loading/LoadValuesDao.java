/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

import java.util.List;
import java.util.Map;

import ru.prbb.middleoffice.domain.SecurityValuesItem;

/**
 * Загрузка номинала
 * 
 * @author RBr
 * 
 */
public interface LoadValuesDao {

	/**
	 * @param answer
	 * @return
	 */
	List<Map<String, Object>> execute(List<Map<String, Object>> answer);

	/**
	 * @return
	 */
	List<SecurityValuesItem> findAllSecurities();

}
