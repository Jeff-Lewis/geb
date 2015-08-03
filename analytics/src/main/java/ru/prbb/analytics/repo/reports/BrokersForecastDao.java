/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.BrokersForecastDateItem;
import ru.prbb.analytics.domain.BrokersForecastItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Прогнозы по брокерам
 * 
 * @author RBr
 */
@Service
public class BrokersForecastDao
{

	@Autowired
	private EntityManagerService ems;

	public List<BrokersForecastItem> execute(ArmUserInfo user, String date_time, Long broker_id, Long id_sec) {
		String sql = "{call dbo.anca_WebGet_BrokerForecastData_sp ?, ?, ?}";
		return ems.getSelectList(user, BrokersForecastItem.class, sql, date_time, id_sec, broker_id);
	}

	public List<BrokersForecastDateItem> findBrokerDates(ArmUserInfo user) {
		String sql = "select value, display from dbo.anca_WebGet_ajaxBrokerDates_v" +
				" order by value desc";
		return ems.getSelectList(user, BrokersForecastDateItem.class, sql);
	}

}
