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
 * BDP запрос
 * 
 * @author RBr
 */
@Service
public class RequestBDPDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	public void execute(ArmUserInfo user, String[] securities, Map<String, Map<String, String>> answer) {
		for (String security : securities) {
			Map<String, String> values = answer.get(security);
			if (null == values)
				continue;
			for (Entry<String, String> entry : values.entrySet()) {
				try {
					String param = entry.getKey();
					String value = entry.getValue();
					ems.executeUpdate(AccessAction.OTHER, user,
							"{call dbo.put_current_data ?, ?, ?}",
							security, param, value);
				} catch (Exception e) {
					log.error("put_current_data " + security, e);
				}
			}
		}
	}

	public List<SimpleItem> findParams(ArmUserInfo user, String query) {
		String sql = "select code from dbo.cur_request_params_v";
		String where = "";
		return ems.getComboListName(sql, where, query);
	}

}
