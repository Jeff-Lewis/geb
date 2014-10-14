/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

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
	 * @param answer
	 * @return
	 */
	List<Map<String, Object>> execute(List<Map<String, Object>> answer);

}
