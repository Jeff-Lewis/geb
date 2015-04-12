/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.AtrLoadDataItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Загрузка ATR
 * 
 * @author RBr
 * 
 */
@Repository
public class LoadATRDaoImpl extends BaseDaoImpl implements LoadATRDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<AtrLoadDataItem> execute(List<AtrLoadDataItem> answer,
			String maType, Integer taPeriod, String period, String calendar) {
		putAtr(answer, taPeriod, maType, period, calendar);
		return answer;
	}

	private void putAtr(List<AtrLoadDataItem> items,
			int atr_period, String algorithm, String period, String calendar) {
		String sql = "{call dbo.mo_WebSet_putATR_sp ?, ?, ?, ?, ?,?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(4, atr_period);
		q.setParameter(5, algorithm);
		q.setParameter(6, "PX_HIGH");
		q.setParameter(7, "PX_LOW");
		q.setParameter(8, "PX_LAST");
		q.setParameter(9, period);
		q.setParameter(10, calendar);
		for (AtrLoadDataItem item : items) {
			q.setParameter(1, item.getSecurity());
			q.setParameter(2, Utils.parseDate(item.getDate()));
			q.setParameter(3, Double.parseDouble(item.getValue()));
			storeSql(sql, q);
			executeUpdate(q, sql);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> getTypeMA(String query) {
		String sql = "select id, algorithm_name as name from dbo.mo_WebGet_ajaxAlgorithm_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(algorithm_name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> getPeriod(String query) {
		String sql = "select period_id as id, name from dbo.period_type";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> getCalendar(String query) {
		String sql = "select calendar_id as id, name from dbo.calendar_type";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}

}
