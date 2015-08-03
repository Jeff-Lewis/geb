/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.SecurityValuesItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Загрузка номинала
 * 
 * @author RBr
 */
@Service
public class LoadValuesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<Map<String, String>> execute(ArmUserInfo user, List<Map<String, String>> answer) {
		String sql = "{call dbo.put_face_amount_sp ?, ?, ?}";
		for (Map<String, String> item : answer) {
			Long id = new Long(item.get("id_sec"));
			Date date = Utils.parseDate(item.get("date"));
			Number value = new BigDecimal(item.get("value"));

			ems.executeUpdate(AccessAction.OTHER, user, sql, id, date, value);
		}

		return answer;
	}

	public List<SecurityValuesItem> findAllSecurities(ArmUserInfo user) {
		String sql = "select * from dbo.mo_WebGet_bonds_sinkable_v";
		return ems.getSelectList(user, SecurityValuesItem.class, sql);
	}

}
