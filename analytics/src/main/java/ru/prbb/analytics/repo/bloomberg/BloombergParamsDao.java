/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;

import ru.prbb.analytics.domain.SimpleItem;

/**
 * Параметры запросов
 * 
 * @author RBr
 * 
 */
public interface BloombergParamsDao {

	/**
	 * @param query 
	 * @return
	 */
	List<SimpleItem> findPeriod(String query);

	/**
	 * @param query 
	 * @return
	 */
	List<SimpleItem> findCalendar(String query);

}
