/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Добавление компаний
 * 
 * @author RBr
 */
@Service
public class CompanyAddDaoImpl implements CompanyAddDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public List<String[]> execute(String[] codes, Map<String, Map<String, String>> answer) {
		final List<String[]> info = new ArrayList<>(codes.length);
		for (String code : codes) {
			final Map<String, String> values = answer.get(code);

			if ((values == null) || values.isEmpty()) {
				info.add(createInfo(code, "Нет данных BLOOMBERG"));
				// TODO log.warn("No data for " + code);
				continue;
			}

			if (values.containsKey("securityError")) {
				info.add(createInfo(code, "Ошибка BLOOMBERG:" + values.get("securityError")));
				continue;
			}

			try {
				putSecurityData(values);
				info.add(createInfo(values.get("PARSEKYABLE_DES"), "Загружено"));
			} catch (DataAccessException e) {
				info.add(createInfo(code, e.toString()));
			}
		}
		return info;
	}

	private String[] createInfo(String code, String text) {
		return new String[] { code, text };
	}

	private int putSecurityData(Map<String, String> values) {
		String sql = "{call dbo.put_equity_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		int pos = 0;
		Query q = em.createNativeQuery(sql);
		q.setParameter(++pos, values.get("ID_BB_GLOBAL"));
		q.setParameter(++pos, values.get("ID_BB"));
		q.setParameter(++pos, values.get("ID_BB_UNIQUE"));
		q.setParameter(++pos, values.get("ID_BB_SEC_NUM_DES"));
		q.setParameter(++pos, values.get("ID_BB_SEC_NUM_SRC"));
		q.setParameter(++pos, values.get("ID_ISIN"));
		q.setParameter(++pos, values.get("ID_CUSIP"));
		q.setParameter(++pos, values.get("PARSEKYABLE_DES"));
		q.setParameter(++pos, values.get("PARSEKYABLE_DES_SOURCE"));
		q.setParameter(++pos, values.get("SECURITY_TYP"));
		q.setParameter(++pos, values.get("MARKET_SECTOR_DES"));
		q.setParameter(++pos, values.get("FEED_SOURCE"));
		q.setParameter(++pos, values.get("TICKER"));
		q.setParameter(++pos, values.get("SECURITY_NAME"));
		q.setParameter(++pos, values.get("NAME"));
		q.setParameter(++pos, values.get("SHORT_NAME"));
		q.setParameter(++pos, values.get("EQY_PRIM_EXCH"));
		q.setParameter(++pos, values.get("EXCH_CODE"));
		q.setParameter(++pos, values.get("EQY_FUND_IND"));
		q.setParameter(++pos, values.get("INDUSTRY_GROUP"));
		q.setParameter(++pos, values.get("INDUSTRY_SUBGROUP"));
		q.setParameter(++pos, values.get("ADR_SH_PER_ADR"));
		q.setParameter(++pos, values.get("CRNCY"));
		q.setParameter(++pos, values.get("EQY_FUND_CRNCY"));
		q.setParameter(++pos, values.get("EQY_PRIM_SECURITY_CRNCY"));
		q.setParameter(++pos, values.get("ADR_CRNCY"));
		q.setParameter(++pos, values.get("BEST_CRNCY_ISO"));
		q.setParameter(++pos, values.get("DVD_CRNCY"));
		q.setParameter(++pos, values.get("EARN_EST_CRNCY"));
		q.setParameter(++pos, values.get("EQY_FUND_TICKER"));
		q.setParameter(++pos, values.get("EQY_FISCAL_YR_END"));
		return q.executeUpdate();
	}

	@Override
	public Map<String, String> createPeriodicity(Map<String, StringBuilder> info,
			String[] codes, Map<String, Map<String, String>> answer) {
		Map<String, String> periodicity = new HashMap<>();
		for (String code : codes) {
			final Map<String, String> values = answer.get(code);

			if ((values == null) || values.isEmpty()) {
				info.get(code).append("Нет данных BLOOMBERG");
				// TODO log.warn("No data for " + code);
				continue;
			}

			if (values.containsKey("securityError")) {
				info.get(code).append("Ошибка BLOOMBERG:").append(values.get("securityError"));
				continue;
			}

			String pp = values.get("PRIMARY_PERIODICITY");
			periodicity.put(code, pp);
			info.get(code).append(pp).append("; ");
		}

		if (codes.length != periodicity.size()) {
			throw new RuntimeException("Ошибка при загрузке PRIMARY_PERIODICITY.");
		}
		return periodicity;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<InfoItem> getCompanyInfo(String[] codes) {
		String sqlEquity = "select id, name from dbo.anca_WebGet_ajaxEquity_v where lower(name) = ?";
		Query qEquity = em.createNativeQuery(sqlEquity, SimpleItem.class);

		String sqlEquityFilter = "dbo.anca_WebGet_EquityFilter_sp null, 4, ?";
		Query qEquityFilter = em.createNativeQuery(sqlEquityFilter);

		final List<InfoItem> list = new ArrayList<>(codes.length);
		for (String c : codes) {
			if (c != null && !c.isEmpty()) {
				qEquity.setParameter(1, c.toLowerCase());
				SimpleItem rEquity = (SimpleItem) qEquity.getSingleResult();

				qEquityFilter.setParameter(1, rEquity.getId());
				Object[] rEquityFilter = (Object[]) qEquityFilter.getSingleResult();

				InfoItem item = new InfoItem(c);
				item.id = rEquity.getId();
				item.name = rEquity.getName();
				item.crncy = Utils.toString(rEquityFilter[3]);
				list.add(item);
			}
		}
		return list;
	}

}
