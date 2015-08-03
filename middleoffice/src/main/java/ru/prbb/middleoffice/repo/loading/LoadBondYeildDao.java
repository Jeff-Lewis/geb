/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Загрузка доходности облигаций
 * 
 * @author RBr
 */
@Service
public class LoadBondYeildDao
{

	private static final String YLD_CNV_MID = "YLD_CNV_MID";

	@Autowired
	private EntityManagerService ems;

	public List<Map<String, Object>> execute(ArmUserInfo user, String[] securities, Map<String, Map<String, Map<String, String>>> answer) {
		String sql = "{call dbo.put_bond_yield_proc ? ,?, ?, ?}";

		List<Map<String, Object>> info = new ArrayList<>();

		for (String security : securities) {
			final Map<String, Map<String, String>> datevalues = answer.get(security);

			for (String date : datevalues.keySet()) {
				Map<String, String> values = datevalues.get(date);
				String value = values.get(YLD_CNV_MID);

				Map<String, Object> infoItem = new HashMap<>(4, 1);
				infoItem.put("security", security);
				infoItem.put("params", YLD_CNV_MID);
				infoItem.put("date", date);
				try {
					ems.executeUpdate(AccessAction.OTHER, user, sql, security, YLD_CNV_MID, date, value);

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
