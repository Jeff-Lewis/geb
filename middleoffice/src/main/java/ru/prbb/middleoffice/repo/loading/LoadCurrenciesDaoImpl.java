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

import ru.prbb.middleoffice.domain.LoadCurrenciesItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Загрузка курсов валют
 * 
 * @author RBr
 */
@Service
public class LoadCurrenciesDaoImpl extends BaseDaoImpl implements LoadCurrenciesDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<LoadCurrenciesItem> findAll() {
		String sql = "select * from dbo.mo_job_LoadCurrency_rate_cbr_v";
		Query q = em.createNativeQuery(sql, LoadCurrenciesItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<Map<String, Object>> execute(String[] securities, Map<String, Map<String, Map<String, String>>> answer) {
		String sql = "{call dbo.put_currency_rate_cbr_sp null, null, ?, null, null, ?, ?}";
		Query q = em.createNativeQuery(sql);
		
		List<Map<String, Object>> info = new ArrayList<>();

		for (String security : securities) {
			final Map<String, Map<String, String>> datevalues = answer.get(security);

			if (datevalues.keySet().isEmpty()) {
				final Map<String, Object> infoItem = new HashMap<>(4, 1);
				infoItem.put("security", security);
				infoItem.put("date", null);
				infoItem.put("value", "Нет данных");
				info.add(infoItem);
			}

			for (String date : datevalues.keySet()) {
				final Map<String, String> values = datevalues.get(date);

				final String value = values.get("PX_LAST");

				final Map<String, Object> infoItem = new HashMap<>(4, 1);
				infoItem.put("security", security);
				infoItem.put("date", date);
				try {
					q.setParameter(1, security);
					q.setParameter(2, Double.valueOf(value));
					q.setParameter(3, date);
					storeSql(sql, q);
					q.executeUpdate();

					infoItem.put("value", value);
				} catch (Exception e) {
					infoItem.put("value", e.toString());
				}
				info.add(infoItem);
			}
		}

		return info;
	}
}
