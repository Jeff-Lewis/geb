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
import ru.prbb.analytics.domain.BrokersCoverageItem;

/**
 * Покрытие брокеров
 * 
 * @author RBr
 * 
 */
@Service
public class BrokersCoverageDaoImpl implements BrokersCoverageDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<BrokersCoverageItem> execute() {
		String sql = "{call dbo.anca_WebGet_EquityBrokerCoverage_sp}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<BrokersCoverageItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			BrokersCoverageItem item = new BrokersCoverageItem();
			item.setId_sec(Utils.toLong(arr[0]));
			item.setSecurity_code(Utils.toString(arr[1]));
			item.setShort_name(Utils.toString(arr[2]));
			item.setPivot_group(Utils.toString(arr[3]));
			item.setCredit_Suisse(Utils.toInteger(arr[4]));
			item.setGoldman_Sachs(Utils.toInteger(arr[5]));
			item.setJp_Morgan(Utils.toInteger(arr[6]));
			item.setUBS(Utils.toInteger(arr[7]));
			item.setMerrill_Lynch(Utils.toInteger(arr[8]));
			item.setMorgan_Stanley(Utils.toInteger(arr[9]));
			item.setDeutsche_Bank(Utils.toInteger(arr[10]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int change(Long id, String broker, Integer value) {
		String sql = "{call dbo.anca_WebSet_setEquityBrokerCoverage_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, broker)
				.setParameter(3, value);
		return q.executeUpdate();
	}
}
