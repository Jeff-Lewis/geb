/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Загрузка котировок
 * 
 * @author RBr
 * 
 */
@Service
public class LoadQuotesDaoImpl extends BaseDaoImpl implements LoadQuotesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<Map<String, Object>> execute(String[] securities, Map<String, Map<String, Map<String, String>>> answer) {
		final List<QuotesRecord> datas = new ArrayList<>();
		List<Map<String, Object>> info = new ArrayList<>();

		for (String security : securities) {
			final Map<String, Map<String, String>> datevalues = answer.get(security);

			for (String date : datevalues.keySet()) {
				final Map<String, String> values = datevalues.get(date);
				final String value = values.get(PX_LAST);
				final Map<String, Object> infoItem = new HashMap<>();
				infoItem.put("security", security);
				infoItem.put("params", PX_LAST);
				infoItem.put("date", date);
				try {
					datas.add(new QuotesRecord(security, Double.parseDouble(value), date));

					infoItem.put("value", value);
				} catch (Exception e) {
					infoItem.put("value", e.toString());
				}
				info.add(infoItem);
			}
		}

		putQuotes(datas);

		return info;
	}

	private static final String PX_LAST = "PX_LAST";

	private class QuotesRecord {

		public final String security;
		public final double value;
		public final String date;

		public QuotesRecord(String security, double value, String date) {
			this.security = security;
			this.value = value;
			this.date = date;
		}
	}

	private void putQuotes(final List<QuotesRecord> items) {
		//		Collections.sort(records, new Comparator<QuotesRecord>() {
		//
		//			@Override
		//			public int compare(QuotesRecord o1, QuotesRecord o2) {
		//				final int r1 = o1.security.compareTo(o2.security);
		//				if (0 != r1) {
		//					return r1;
		//				}
		//				return o1.date.compareTo(o2.date);
		//			}
		//		});
		String sql = "{call put_quotes ? ,?, ?}";
		Query q = em.createNativeQuery(sql);
		for (QuotesRecord item : items) {
			q.setParameter(1, item.security);
			q.setParameter(2, item.value);
			q.setParameter(3, item.date);
			storeSql(sql, q);
			q.executeUpdate();
		}
	}
}
