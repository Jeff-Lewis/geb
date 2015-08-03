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
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Загрузка ставки по купонам
 * 
 * @author RBr
 */
@Service
public class LoadRateCouponDao
{

	@Autowired
	private EntityManagerService ems;

	public List<Map<String, Object>> execute(ArmUserInfo user, List<Map<String, Object>> answer) {
		String sql = "{call dbo.put_security_coupon_schedule_sp ?, ?, ?}";

		for (Map<String, Object> item : answer) {
			Long id = new Long(item.get("id_sec").toString());
			Date date = Utils.parseDate(item.get("date").toString());
			Number value = new BigDecimal(item.get("value").toString());

			ems.executeUpdate(AccessAction.OTHER, user, sql, id, date, value);
		}

		return answer;
	}

}
