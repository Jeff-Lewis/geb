/**
 * 
 */
package ru.prbb.analytics.repo.params;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.NewParamItem;
import ru.prbb.analytics.repo.BloombergServicesA;
import ru.prbb.analytics.repo.UserHistory.AccessAction;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Ввод нового параметра<br>
 * Ввод нового параметра blm_datasource_ovr
 * 
 * @author RBr
 */
@Service
public class NewParamDao
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntityManagerService ems;

	@Autowired
	private BloombergServicesA bs;

	public NewParamItem setup(ArmUserInfo user, String code) {
		NewParamItem res = null;

		try {
			String sql = "select field_mnemonic, field_id, description from dbo.bloomberg_dl_fields"
					+ " where field_mnemonic=?";
			res = ems.getSelectItem(user, NewParamItem.class, sql, code);
		} catch (Exception e) {
			log.error("setup:" + code, e);
		}

		if (null == res) {
			Map<String, String> answer = bs.executeFieldInfoRequest("Ввод нового параметра", code);
			res = new NewParamItem();
			res.setCode(answer.get("CODE"));
			res.setBlmId(answer.get("BLM_ID"));
			res.setName(answer.get("NAME"));
		}

		return res;
	}

	public int save(ArmUserInfo user, String blm_id, String code, String name) {
		String sql = "{call dbo.put_params_data ?, ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql, blm_id, code, name);
	}

	public int saveOvr(ArmUserInfo user, String code, String broker) {
		String sql = "insert into dbo.blm_datasource_ovr (code, brocker) values (?, ?)";
		return ems.executeUpdate(AccessAction.OTHER, user, sql, code, broker);
	}

}
