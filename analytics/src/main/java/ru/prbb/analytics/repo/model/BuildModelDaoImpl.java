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
import ru.prbb.analytics.domain.BuildModelItem;
import ru.prbb.analytics.domain.PortfolioWatchListItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Расчёт модели по компании
 * 
 * @author RBr
 */
@Service
public class BuildModelDaoImpl extends BaseDaoImpl implements BuildModelDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public BuildModelItem calculateModel(Long id) {
		String sql = "{call dbo.build_model_proc_p ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		Object[] arr = (Object[]) getSingleResult(q, sql);
		BuildModelItem item = new BuildModelItem();
		item.setSecurity_code(Utils.toString(arr[0]));
		item.setStatus(Utils.toString(arr[1]));
		return item;
	}

	//@Transactional(propagation = Propagation.REQUIRED)
	//@Override
	List<BuildModelItem> calculateSvod() {
		String sql = "{call dbo.build_model_proc}";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = getResultList(q, sql);
		List<BuildModelItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			BuildModelItem item = new BuildModelItem();
			item.setSecurity_code(Utils.toString(arr[1]));
			item.setStatus(Utils.toString(arr[2]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<PortfolioWatchListItem> getPortfolioWatchList() {
		String sql = "select short_name, security_code, period_id from anca_portfolio_watch_lst_v";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> list = getResultList(q, sql);
		List<PortfolioWatchListItem> res = new ArrayList<>(list.size());
		for (Object[] arr : list) {
			PortfolioWatchListItem item = new PortfolioWatchListItem();
			item.setShortName(Utils.toString(arr[0]));
			item.setSecurityCode(Utils.toString(arr[1]));
			item.setPeriod(Utils.toNumber(arr[2]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int calculateModelQ(String securityCode) {
		String sql = "{call dbo.build_model_proc_q ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, securityCode);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int calculateModelHY(String securityCode) {
		String sql = "{call dbo.build_model_proc_hy ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, securityCode);
		return executeUpdate(q, sql);
	}
}
