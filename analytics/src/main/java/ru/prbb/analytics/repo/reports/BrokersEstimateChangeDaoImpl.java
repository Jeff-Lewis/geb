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
import ru.prbb.analytics.domain.BrokersEstimateChangeItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Изменение оценок брокеров
 * 
 * @author RBr
 * 
 */
@Service
public class BrokersEstimateChangeDaoImpl extends BaseDaoImpl implements BrokersEstimateChangeDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<BrokersEstimateChangeItem> execute() {
		String sql = "{call dbo.anca_WebGet_BrokerEstimatesChange_sp}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<BrokersEstimateChangeItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			BrokersEstimateChangeItem item = new BrokersEstimateChangeItem();
			item.setSecurity(Utils.toString(arr[0]));
			item.setBroker(Utils.toString(arr[1]));
			item.setTargetChange(Utils.toString(arr[2]));
			item.setPcntChange(Utils.toString(arr[3]));
			item.setRecommendation(Utils.toString(arr[4]));
			item.setDateInsert(Utils.toString(arr[5]));
			res.add(item);
		}
		return res;
	}

}
