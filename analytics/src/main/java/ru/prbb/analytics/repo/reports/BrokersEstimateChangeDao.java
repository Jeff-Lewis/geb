/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import ru.prbb.analytics.domain.BrokersEstimateChangeItem;

/**
 * Изменение оценок брокеров
 * 
 * @author RBr
 * 
 */
public interface BrokersEstimateChangeDao {

	/**
	 * @return
	 */
	List<BrokersEstimateChangeItem> execute();

}
