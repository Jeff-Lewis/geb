/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import ru.prbb.analytics.domain.BrokersCoverageItem;

/**
 * Покрытие брокеров
 * 
 * @author RBr
 * 
 */
public interface BrokersCoverageDao {

	/**
	 * @return
	 */
	List<BrokersCoverageItem> execute();

	/**
	 * @param id
	 * @param broker
	 * @param value
	 * @return
	 */
	int change(Long id, String broker, Integer value);

}
