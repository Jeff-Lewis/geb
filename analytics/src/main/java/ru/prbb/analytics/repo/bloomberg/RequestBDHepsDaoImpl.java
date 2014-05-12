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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * BDH запрос с EPS
 * 
 * @author RBr
 */
@Service
public class RequestBDHepsDaoImpl implements RequestBDHepsDao
{
	private static final Log log = LogFactory.getLog(RequestBDHepsDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void execute(String[] securities, Map<String, Map<String, Map<String, String>>> answer) {
		final List<EpsData> data = new ArrayList<>();
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
					String[] vs = _value.split(";", 5);
					data.add(new EpsData(security, field, date, vs[0], vs[1], vs[2], vs[3], vs[4]));
				}
			}
		}

		String sql = "{call put_fundamentals_data ?, ?, ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (EpsData d : data) {
			q.setParameter(1, d.security);
			q.setParameter(2, d.params);
			q.setParameter(3, d.value);
			q.setParameter(4, d.date);
			q.setParameter(5, d.rel_date);
			q.setParameter(6, d.currency);
			q.setParameter(7, d.calendar);
			q.setParameter(8, d.period);
			log.info(d);
			q.executeUpdate();
		}
	}

	private class EpsData {

		public final String security;
		public final String params;
		public final String date;
		public final String value;
		public final String period;
		public final String rel_date;
		public final String currency;
		public final String calendar;

		public EpsData(String security, String params, String date, String value, String period,
				String rel_date, String currency, String calendar) {
			this.security = security;
			this.params = params;
			this.date = date;
			this.value = value;
			this.period = period;
			this.rel_date = rel_date;
			this.currency = currency;
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
			builder.append(", rel_date=");
			builder.append(rel_date);
			builder.append(", currency=");
			builder.append(currency);
			builder.append(", calendar=");
			builder.append(calendar);
			builder.append(")");
			return builder.toString();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams(String query) {
		String sql = "select code from dbo.fundamentals_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

}
