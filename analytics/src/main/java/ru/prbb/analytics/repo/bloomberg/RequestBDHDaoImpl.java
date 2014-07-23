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
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * BDH запрос
 * 
 * @author RBr
 */
@Service
public class RequestBDHDaoImpl extends BaseDaoImpl implements RequestBDHDao
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
		for (HistData d : data) {
			q.setParameter(1, d.security);
			q.setParameter(2, d.params);
			q.setParameter(3, d.date);
			q.setParameter(4, d.value);
			q.setParameter(5, d.period);
			q.setParameter(6, d.curncy);
			q.setParameter(7, d.calendar);
			storeSql(sql, q);
			storeSql(sql, q);
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

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(security);
			builder.append("(params=");
			builder.append(params);
			builder.append(", date=");
			builder.append(date);
			builder.append(", value=");
			builder.append(value);
			builder.append(", period=");
			builder.append(period);
			builder.append(", curncy=");
			builder.append(curncy);
			builder.append(", calendar=");
			builder.append(calendar);
			builder.append(")");
			return builder.toString();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams(String query) {
		String sql = "select code from dbo.multy_request_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

}
