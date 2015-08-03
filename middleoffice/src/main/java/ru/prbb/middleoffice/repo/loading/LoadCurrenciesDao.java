package ru.prbb.middleoffice.repo.loading;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.LoadCurrenciesItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Загрузка курсов валют
 * 
 * @author RBr
 */
@Service
public class LoadCurrenciesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<LoadCurrenciesItem> findAll(ArmUserInfo user) {
		String sql = "select * from dbo.mo_job_LoadCurrency_rate_cbr_v";
		return ems.getSelectList(user, LoadCurrenciesItem.class, sql);
	}

	public List<Map<String, Object>> execute(ArmUserInfo user, String[] securities, Map<String, Map<String, Map<String, String>>> answer) {
		String sql = "{call dbo.put_currency_rate_cbr_sp null, null, ?, null, null, ?, ?}";

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
				Map<String, String> values = datevalues.get(date);

				Number value = new BigDecimal(values.get("PX_LAST"));

				Map<String, Object> infoItem = new HashMap<>(4, 1);
				infoItem.put("security", security);
				infoItem.put("date", date);
				try {
					ems.executeUpdate(AccessAction.OTHER, user, sql, security, value, date);

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
