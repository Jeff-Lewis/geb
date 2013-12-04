/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

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
	 * @param securities
	 * @return
	 */
	List<Map<String, Object>> execute(String[] securities);

	/**
	 * @return
	 */
	List<SecurityValuesItem> findAllSecurities();

}
