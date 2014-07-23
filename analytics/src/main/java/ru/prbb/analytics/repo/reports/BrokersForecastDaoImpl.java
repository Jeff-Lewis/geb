/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.BrokersForecastDateItem;
import ru.prbb.analytics.domain.BrokersForecastItem;

/**
 * Прогнозы по брокерам
 * 
 * @author RBr
 * 
 */
@Service
public class BrokersForecastDaoImpl implements BrokersForecastDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<BrokersForecastItem> execute(String date_time, Long broker_id, Long id_sec) {
		String sql = "{call dbo.anca_WebGet_BrokerForecastData_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, date_time)
				.setParameter(2, id_sec)
				.setParameter(3, broker_id);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<BrokersForecastItem> res = new ArrayList<>();
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			BrokersForecastItem item = new BrokersForecastItem();
			item.setId_sec(Utils.toLong(arr[0]));
			item.setSecurity_code(Utils.toString(arr[1]));
			item.setShort_name(Utils.toString(arr[2]));
			item.setPivot_group(Utils.toString(arr[3]));
			item.setBroker(Utils.toString(arr[4]));
			item.setEPS1Q(Utils.toDouble(arr[5]));
			item.setEPS2Q(Utils.toDouble(arr[6]));
			item.setEPS3Q(Utils.toDouble(arr[7]));
			item.setEPS4Q(Utils.toDouble(arr[8]));
			item.setEPS1CY(Utils.toDouble(arr[9]));
			item.setEPS2CY(Utils.toDouble(arr[10]));
			item.setTargetConsensus12m(Utils.toDouble(arr[11]));
			item.setTargetConsensus(Utils.toDouble(arr[12]));
			item.setRecommendation(Utils.toString(arr[13]));
			item.setPeriod(Utils.toString(arr[14]));
			item.setTarget_date(Utils.toDate(arr[15]));
			item.setCurrency(Utils.toString(arr[16]));
			item.setDate_insert(Utils.toDate(arr[17]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<BrokersForecastDateItem> findBrokerDates() {
		String sql = "select value, display from dbo.anca_WebGet_ajaxBrokerDates_v" +
				" order by value desc";
		Query q = em.createNativeQuery(sql, BrokersForecastDateItem.class);
		return q.getResultList();
	}

}
