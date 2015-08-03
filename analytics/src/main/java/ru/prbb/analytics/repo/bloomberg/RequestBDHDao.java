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
 * BDH запрос
 * 
 * @author RBr
 */
@Service
public class RequestBDHDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	public void execute(ArmUserInfo user, String[] securities, String[] currencies, Map<String, Map<String, Map<String, String>>> answer) {
		for (String security : securities) {
			Map<String, Map<String, String>> datevalues = answer.get(security);
			if (null == datevalues)
				continue;
			for (String currency : currencies) {
				if (security.startsWith(currency)) {
					security = security.substring(currency.length());
					break;
				}
			}
			for (Entry<String, Map<String, String>> dateentry : datevalues.entrySet()) {
				String date = dateentry.getKey();
				Map<String, String> values = dateentry.getValue();
				if (null == values)
					continue;
				for (Entry<String, String> entry : values.entrySet()) {
					try {
						String param = entry.getKey();
						String _value = entry.getValue();
						String[] vs = _value.split(";", 4);
						String value = vs[0];
						String period = vs[1];
						String curncy = vs[2];
						String calendar = vs[3];

						ems.executeUpdate(AccessAction.OTHER, user,
								"{call dbo.put_hist_data ?, ?, ?, ?, ?, ?, ?}",
								security, param, date, value, period, curncy, calendar);
					} catch (Exception e) {
						log.error("put_hist_data " + security, e);
					}
				}
			}
		}
	}

	public List<SimpleItem> findParams(ArmUserInfo user, String query) {
		String sql = "select code from dbo.multy_request_params_v";
		String where = "";
		return ems.getComboListName(sql, where, query);
	}

}
