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
	List<BrokersForecastItem> execute(String date_time, Long broker_id, Long id_sec);

	/**
	 * @return
	 */
	List<BrokersForecastDateItem> findBrokerDates();

}
