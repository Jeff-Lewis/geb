/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.BuildEPSItem;

/**
 * Расчёт EPS по компании
 * 
 * @author RBr
 * 
 */
@Service
public class BuildEPSDaoImpl implements BuildEPSDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<BuildEPSItem> calculate(Long[] ids) {
		final List<BuildEPSItem> list = new ArrayList<>();
		String sql = "{call dbo.main_create_eps_proc ?}";
		Query q = em.createNativeQuery(sql);
		for (Long id : ids) {
			BuildEPSItem item = new BuildEPSItem();
			try {
				q.setParameter(1, id);
				Object[] arr = (Object[]) q.getSingleResult();
				item.setSecurity_code(Utils.toString(arr[0]));
				item.setPeriodical_eps_status(Utils.toString(arr[1]));
				item.setYearly_eps_status(Utils.toString(arr[2]));
				item.setEps_median_status(Utils.toString(arr[3]));
				item.setPe_median_status(Utils.toString(arr[4]));
				item.setEps_growth_status(Utils.toString(arr[5]));
				item.setBv_growth_rate(Utils.toString(arr[6]));
				item.setPb_median(Utils.toString(arr[7]));
			} catch (Exception e) {
				item.setSecurity_code(id.toString());
			}
			list.add(item);
		}
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public BuildEPSItem calculateEps(Long id) {
		String sql = "{call dbo.main_create_eps_proc ?}";
		Query q = em.createNativeQuery(sql, BuildEPSItem.class)
				.setParameter(1, id);
		return (BuildEPSItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	@Override
	public List<BuildEPSItem> calculate() {
		String sql = "{call dbo.main_create_eps_proc}";
		Query q = em.createNativeQuery(sql, BuildEPSItem.class);
		return q.getResultList();
	}

}
