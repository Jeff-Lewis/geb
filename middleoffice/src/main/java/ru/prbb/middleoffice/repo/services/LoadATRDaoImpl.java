/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Загрузка ATR
 * 
 * @author RBr
 * 
 */
@Repository
public class LoadATRDaoImpl implements LoadATRDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<Map<String, Object>> execute(List<Map<String, Object>> answer,
			String maType, Integer taPeriod, String period, String calendar) {
		List<AtrData> data = new ArrayList<>(answer.size());
		for (Map<String, Object> item : answer) {
			String security = item.get("security").toString();
			String date = (String) item.get("date");
			double value = Double.parseDouble(item.get("value").toString());

			data.add(new AtrData(security, Utils.parseDate(date), value));
		}
		putAtr(data, taPeriod, maType, period, calendar);

		return answer;
	}

	private class AtrData {

		public final String security;
		public final Date date_time;
		public final double atr_value;

		public AtrData(String security, Date date_time, Double atr_value) {
			this.security = security;
			this.date_time = date_time;
			this.atr_value = atr_value;
		}
	}

	private void putAtr(final List<AtrData> items,
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
		for (AtrData item : items) {
			q.setParameter(1, item.security);
			q.setParameter(2, item.date_time);
			q.setParameter(3, item.atr_value);
			q.executeUpdate();
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
		return q.getResultList();
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
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> getCalendar(String query) {
		String sql = "select calendar_id as id, name from calendar_type";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return q.getResultList();
	}

}
