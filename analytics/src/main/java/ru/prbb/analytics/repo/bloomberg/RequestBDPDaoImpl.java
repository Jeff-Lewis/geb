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
 * BDP запрос
 * 
 * @author RBr
 */
@Service
public class RequestBDPDaoImpl implements RequestBDPDao
{
	private static final Log log = LogFactory.getLog(RequestBDPDaoImpl.class);

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void execute(String[] securities, Map<String, Map<String, String>> answer) {
		final List<CurrentDailyData> data = new ArrayList<>();
		for (String security : securities) {
			Map<String, String> values = answer.get(security);
			if (null == values)
				continue;
			for (Entry<String, String> entry : values.entrySet()) {
				String field = entry.getKey();
				String value = entry.getValue();
				data.add(new CurrentDailyData(security, field, value));
			}
		}

		String sql = "{call dbo.put_current_data ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (CurrentDailyData d : data) {
			q.setParameter(1, d.security);
			q.setParameter(2, d.param);
			q.setParameter(3, d.value);
			log.info(d);
			q.executeUpdate();
		}
	}

	private class CurrentDailyData {

		public final String security;
		public final String param;
		public final String value;

		public CurrentDailyData(String security, String param, String value) {
			this.security = security;
			this.param = param;
			this.value = value;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(40);
			builder.append(security);
			builder.append('(');
			builder.append(param);
			builder.append('=');
			builder.append(value);
			builder.append(')');
			return builder.toString();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findParams() {
		String sql = "select code from dbo.cur_request_params_v";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}

}
