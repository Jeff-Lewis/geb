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
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Расчёт EPS по компании
 * 
 * @author RBr
 * 
 */
@Service
public class BuildEPSDaoImpl extends BaseDaoImpl implements BuildEPSDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public BuildEPSItem calculate(Long id) {
		String sql = "{call dbo.main_create_eps_proc ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		Object[] arr = (Object[]) getSingleResult(q, sql);
		BuildEPSItem item = createItem(arr);
		return item;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<BuildEPSItem> calculate() {
		String sql = "{call dbo.main_create_eps_proc}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		final List<BuildEPSItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			BuildEPSItem item = createItem(arr);
			res.add(item);
		}
		return res;
	}

	private BuildEPSItem createItem(Object[] arr) {
		BuildEPSItem item = new BuildEPSItem();
		item.setSecurity_code(Utils.toString(arr[0]));
		item.setPeriodical_eps_status(Utils.toString(arr[1]));
		item.setYearly_eps_status(Utils.toString(arr[2]));
		item.setEps_median_status(Utils.toString(arr[3]));
		item.setPe_median_status(Utils.toString(arr[4]));
		item.setEps_growth_status(Utils.toString(arr[5]));
		item.setBv_growth_rate(Utils.toString(arr[6]));
		item.setPb_median(Utils.toString(arr[7]));
		return item;
	}

}
