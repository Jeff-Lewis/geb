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
	List<Map<String, String>> execute(List<Map<String, String>> answer);

	/**
	 * @return
	 */
	List<SecurityValuesItem> findAllSecurities();

}
