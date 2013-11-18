/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.BrokersForecastItem;

/**
 * Прогнозы по брокерам
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class BrokersForecastDaoImpl implements BrokersForecastDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<BrokersForecastItem> execute(String date, String broker, String equity) {
		final ArrayList<BrokersForecastItem> list = new ArrayList<BrokersForecastItem>();
		for (long i = 1; i < 11; i++) {
			final BrokersForecastItem item = new BrokersForecastItem();
			item.setId_sec(i);
			item.setSecurity_code("SECURITY_CODE_" + i);
			item.setShort_name("SHORT_NAME_" + i);
			item.setPivot_group("PIVOT_GROUP_" + (i % 3));
			list.add(item);
		}
		return list;
	}

}
