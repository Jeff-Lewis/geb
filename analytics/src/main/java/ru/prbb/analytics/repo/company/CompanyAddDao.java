/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Добавление компаний
 * 
 * @author RBr
 */
@Service
public class CompanyAddDao
{

	public class InfoItem {

		public final String code;
		public long id;
		public String name;
		public String crncy;

		public InfoItem(String code) {
			this.code = code;
		}

		@Override
		public String toString() {
			return name + '(' + String.valueOf(id) + ", " + crncy + ')';
		}

	}

	@Autowired
	private EntityManagerService ems;

	public List<String[]> execute(ArmUserInfo user, String[] codes, Map<String, Map<String, String>> answer) {
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
				putSecurityData(user, values);
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

	private int putSecurityData(ArmUserInfo user, Map<String, String> values) {
		String sql = "{call dbo.put_equity_proc "
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ " ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				values.get("ID_BB_GLOBAL"),
				values.get("ID_BB"),
				values.get("ID_BB_UNIQUE"),
				values.get("ID_BB_SEC_NUM_DES"),
				values.get("ID_BB_SEC_NUM_SRC"),
				values.get("ID_ISIN"),
				values.get("ID_CUSIP"),
				values.get("PARSEKYABLE_DES"),
				values.get("PARSEKYABLE_DES_SOURCE"),
				values.get("SECURITY_TYP"),
				values.get("MARKET_SECTOR_DES"),
				values.get("FEED_SOURCE"),
				values.get("TICKER"),
				values.get("SECURITY_NAME"),
				values.get("NAME"),
				values.get("SHORT_NAME"),
				values.get("EQY_PRIM_EXCH"),
				values.get("EXCH_CODE"),
				values.get("EQY_FUND_IND"),
				values.get("INDUSTRY_GROUP"),
				values.get("INDUSTRY_SUBGROUP"),
				values.get("ADR_SH_PER_ADR"),
				values.get("CRNCY"),
				values.get("EQY_FUND_CRNCY"),
				values.get("EQY_PRIM_SECURITY_CRNCY"),
				values.get("ADR_CRNCY"),
				values.get("BEST_CRNCY_ISO"),
				values.get("DVD_CRNCY"),
				values.get("EARN_EST_CRNCY"),
				values.get("EQY_FUND_TICKER"),
				values.get("EQY_FISCAL_YR_END"),
				values.get("PRIMARY_PERIODICITY"));
	}

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

	public List<InfoItem> getCompanyInfo(ArmUserInfo user, String[] codes) {
		String sqlEquity = "select id, name from dbo.anca_WebGet_ajaxEquity_v where lower(name) = ?";

		String sqlEquityFilter = "dbo.anca_WebGet_EquityFilter_sp null, 4, ?";

		final List<InfoItem> list = new ArrayList<>(codes.length);
		for (String c : codes) {
			if (c != null && !c.isEmpty()) {
				Object[] arrEquity = ems.getSelectItem(user, Object[].class, sqlEquity, c.toLowerCase());

				Object[] arrEquityFilter = ems.getSelectItem(user, Object[].class, sqlEquityFilter, arrEquity[0]);

				InfoItem item = new InfoItem(c);
				item.id = Utils.toLong(arrEquity[0]);
				item.name = Utils.toString(arrEquity[1]);
				item.crncy = Utils.toString(arrEquityFilter[3]);
				list.add(item);
			}
		}
		return list;
	}

}
