/**
 * 
 */
package ru.prbb.analytics.repo.bloomberg;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * BDP с override
 * 
 * @author RBr
 */
@Service
public class RequestBDPovrDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	public void execute(ArmUserInfo user, String[] securities, String[] currencies, String blm_data_src_over,
			Map<String, Map<String, Map<String, String>>> answer) {
		for (String security : securities) {
			for (String currency : currencies) {
				if (security.startsWith(currency)) {
					security = security.substring(currency.length());
					break;
				}
			}
			Map<String, Map<String, String>> periodvalues = answer.get(security);
			if (null == periodvalues)
				continue;
			for (Entry<String, Map<String, String>> pv : periodvalues.entrySet()) {
				String period = pv.getKey();
				Map<String, String> values = pv.getValue();
				for (Entry<String, String> entry : values.entrySet()) {
					String param = entry.getKey();
					String value = entry.getValue();
					try {
						ems.executeUpdate(AccessAction.OTHER, user,
								"{call dbo.put_override_data ?, ?, ?, ?, ?}",
								security, param, value, period, blm_data_src_over);
					} catch (Exception e) {
						log.error("put_override_data " + security, e);
					}
				}
			}
		}
	}

	public List<SimpleItem> findParams(ArmUserInfo user, String query) {
		String sql = "select code from dbo.fundamentals_params_v";
		String where = "";
		return ems.getComboListName(sql, where, query);
	}

	public List<SimpleItem> comboFilterOverride(ArmUserInfo user, String query) {
		String sql = "select code from dbo.blm_datasource_ovr";
		String where = "";
		return ems.getComboListName(sql, where, query);
	}
}
