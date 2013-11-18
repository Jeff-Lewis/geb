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
	 * @return
	 */
	List<SimpleItem> findPeriod();

	/**
	 * @return
	 */
	List<SimpleItem> findCalendar();

}
