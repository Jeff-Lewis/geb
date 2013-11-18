/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;
import java.util.Map;

/**
 * Загрузка ставки по купонам
 * 
 * @author RBr
 * 
 */
public interface LoadRateCouponDao {

	/**
	 * @param securities
	 * @return
	 */
	List<Map<String, Object>> execute(String[] securities);

}
