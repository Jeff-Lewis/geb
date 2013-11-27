/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import ru.prbb.analytics.domain.BrokersForecastDateItem;
import ru.prbb.analytics.domain.BrokersForecastItem;

/**
 * Прогнозы по брокерам
 * 
 * @author RBr
 * 
 */
public interface BrokersForecastDao {

	/**
	 * @param date
	 * @param broker
	 * @param equity
	 * @return
	 */
	List<BrokersForecastItem> execute(String date, String broker, String equity);

	/**
	 * @return
	 */
	List<BrokersForecastDateItem> findBrokerDates();

}
