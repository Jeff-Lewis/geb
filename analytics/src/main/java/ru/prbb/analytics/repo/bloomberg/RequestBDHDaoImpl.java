/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDH запрос
 * 
 * @author RBr
 */
@Service
public class RequestBDHDaoImpl implements RequestBDHDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void execute(String[] securities, Map<String, Map<String, Map<String, String>>> answer) {
		final List<HistData> data = new ArrayList<>();
		for (String security : securities) {
			Map<String, Map<String, String>> datevalues = answer.get(security);
			if (null == datevalues)
				continue;
			security = security.substring(0, security.indexOf('|'));
			for (Entry<String, Map<String, String>> dateentry : datevalues.entrySet()) {
				String date = dateentry.getKey();
				Map<String, String> values = dateentry.getValue();
				if (null == values)
					continue;
				for (Entry<String, String> entry : values.entrySet()) {
					String field = entry.getKey();
					String _value = entry.getValue();
					String[] vs = _value.split(";", 4);
					data.add(new HistData(security, field, date, vs[0], vs[1], vs[2], vs[3]));
				}
			}
		}

		String sql = "{call put_hist_data ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (HistData item : data) {
			q.setParameter(1, item.security);
			q.setParameter(2, item.params);
			q.setParameter(3, item.date);
			q.setParameter(4, item.value);
			q.setParameter(5, item.period);
			q.setParameter(6, item.curncy);
			q.setParameter(7, item.calendar);
			q.executeUpdate();
		}
	}

	private class HistData {

		public final String security;
		public final String params;
		public final String date;
		public final String value;
		public final String period;
		public final String curncy;
		public final String calendar;

		public HistData(String security, String params, String date, String value, String period,
				String curncy, String calendar) {
			this.security = security;
			this.params = params;
			this.date = date;
			this.value = value;
			this.period = period;
			this.curncy = curncy;
			this.calendar = calendar;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code from multy_request_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

}
