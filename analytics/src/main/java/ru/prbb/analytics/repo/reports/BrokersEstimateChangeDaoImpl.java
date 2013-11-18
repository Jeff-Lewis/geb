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

import ru.prbb.analytics.domain.BrokersEstimateChangeItem;

/**
 * Изменение оценок брокеров
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class BrokersEstimateChangeDaoImpl implements BrokersEstimateChangeDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<BrokersEstimateChangeItem> execute() {
		final ArrayList<BrokersEstimateChangeItem> list = new ArrayList<BrokersEstimateChangeItem>();
		for (int i = 0; i < 10; i++) {
			final BrokersEstimateChangeItem map = new BrokersEstimateChangeItem();
			list.add(map);
			map.setSecurity("SECURITY_" + (i + 1));
			map.setBroker("Broker_" + (i + 1));
			map.setRecommendation("Recommendation_" + (i + 1));
		}
		return list;
	}

}
