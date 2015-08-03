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
import ru.prbb.middleoffice.domain.SecurityCashFlowItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 */
@Service
public class LoadCashFlowDao
{

	@Autowired
	private EntityManagerService ems;

	public List<Map<String, String>> execute(ArmUserInfo user, List<Map<String, String>> answer) {
		String sql = "{call dbo.put_security_cash_flow_sp ?, ?, ?, ?}";

		for (Map<String, String> item : answer) {
			Long security_id = new Long(item.get("id_sec"));
			Date maturity_date = Utils.parseDate(item.get("date"));
			Number coupon_cash_flow = new BigDecimal(item.get("value"));
			Number principal_cash_flow = new BigDecimal(item.get("value2"));

			ems.executeUpdate(AccessAction.OTHER, user, sql,
					security_id, maturity_date, coupon_cash_flow, principal_cash_flow);
		}

		return answer;
	}

	public List<SecurityCashFlowItem> findAllSecurities(ArmUserInfo user) {
		String sql = "select * from dbo.mo_WebGet_bonds_v";
		return ems.getSelectList(user, SecurityCashFlowItem.class, sql);
	}

}
