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
 * Загрузка доходности облигаций
 * 
 * @author RBr
 * 
 */
@Service
public class LoadBondYeildDaoImpl extends BaseDaoImpl implements LoadBondYeildDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<Map<String, Object>> execute(String[] securities, Map<String, Map<String, Map<String, String>>> answer) {
		final List<BondYeildRecord> datas = new ArrayList<>();
		List<Map<String, Object>> info = new ArrayList<>();

		for (String security : securities) {
			final Map<String, Map<String, String>> datevalues = answer.get(security);

			for (String date : datevalues.keySet()) {
				final Map<String, String> values = datevalues.get(date);
				final String value = values.get(YLD_CNV_MID);
				final Map<String, Object> infoItem = new HashMap<>();
				infoItem.put("security", security);
				infoItem.put("params", YLD_CNV_MID);
				infoItem.put("date", date);
				try {
					datas.add(new BondYeildRecord(security, YLD_CNV_MID, date, value));

					infoItem.put("value", value);
				} catch (Exception e) {
					infoItem.put("value", e.toString());
				}
				info.add(infoItem);
			}
		}

		putBondYeild(datas);

		return info;
	}

	private static final String YLD_CNV_MID = "YLD_CNV_MID";

	private class BondYeildRecord {

		public final String security;
		public final String params;
		public final String date;
		public final String char_value;

		public BondYeildRecord(String security, String params, String date, String char_value) {
			this.security = security;
			this.params = params;
			this.date = date;
			this.char_value = char_value;
		}
	}

	private void putBondYeild(final List<BondYeildRecord> items) {
		String sql = "{call dbo.put_bond_yield_proc ? ,?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (BondYeildRecord item : items) {
			q.setParameter(1, item.security);
			q.setParameter(2, item.params);
			q.setParameter(3, item.date);
			q.setParameter(4, item.char_value);
			storeSql(sql, q);
			q.executeUpdate();
		}
	}
}
