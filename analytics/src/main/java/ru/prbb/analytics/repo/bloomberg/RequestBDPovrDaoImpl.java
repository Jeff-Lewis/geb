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
 * BDP с override
 * 
 * @author RBr
 * 
 */
@Service
public class RequestBDPovrDaoImpl extends BaseDaoImpl implements RequestBDPovrDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void execute(String[] securities, String over, Map<String, Map<String, Map<String, String>>> answer) {
		final List<OverrideData> data = new ArrayList<>();
		for (String security : securities) {
			security = security.substring(0, security.indexOf('|'));
			Map<String, Map<String, String>> periodvalues = answer.get(security);
			if (null == periodvalues)
				continue;
			for (Entry<String, Map<String, String>> pv : periodvalues.entrySet()) {
				String period = pv.getKey();
				Map<String, String> values = pv.getValue();
				for (Entry<String, String> entry : values.entrySet()) {
					String field = entry.getKey();
					String value = entry.getValue();
					data.add(new OverrideData(security, field, value, period, over));
				}
			}
		}

		String sql = "{call dbo.put_override_data ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (OverrideData d : data) {
			q.setParameter(1, d.tempOrg);
			q.setParameter(2, d.param);
			q.setParameter(3, d.value);
			q.setParameter(4, d.period);
			q.setParameter(5, d.blm_data_src_over);
			storeSql(sql, q);
			q.executeUpdate();
		}
	}

	private class OverrideData {

		public final String tempOrg;
		public final String param;
		public final String value;
		public final String period;
		public final String blm_data_src_over;

		public OverrideData(String tempOrg, String param, String value, String period, String blm_data_src_over) {
			this.tempOrg = tempOrg;
			this.param = param;
			this.value = value;
			this.period = period;
			this.blm_data_src_over = blm_data_src_over;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append(tempOrg);
			builder.append("(param=");
			builder.append(param);
			builder.append(", value=");
			builder.append(value);
			builder.append(", period=");
			builder.append(period);
			builder.append(", blm_data_src_over=");
			builder.append(blm_data_src_over);
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> comboFilterOverride(String query) {
		String sql = "select code from dbo.blm_datasource_ovr";
		Query q = em.createNativeQuery(sql);
		return Utils.toSimpleItem(q.getResultList());
	}
}
