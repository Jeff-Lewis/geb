/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.SecurityRiscsItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Заданные параметры риска
 * 
 * @author RBr
 */
@Service
public class SecurityRiscsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SecurityRiscsItem> findAll(ArmUserInfo user, Long security_id, Long fund_id, Integer batch, Long p_id, Date date) {
		String sql = "{call dbo.mo_WebGet_SecurityRiscs_sp null, ?, ?, ?, ?, ?}";
		return ems.getSelectList(user, SecurityRiscsItem.class, sql, security_id, fund_id, batch, p_id, date);
	}

	public SecurityRiscsItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_SecurityRiscs_sp ?}";
		return ems.getSelectItem(user, SecurityRiscsItem.class, sql, id);
	}

	public int updateById(ArmUserInfo user, Long id, Long client_id, Long fund_id,
			Integer batch, BigDecimal risk_ath, BigDecimal risk_avg,
			BigDecimal stop_loss, Date date_begin, Date date_end, String comment) {
		String sql = "{call dbo.mo_WebSet_setSecurityRiscs_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql,
				id, client_id, fund_id, batch, risk_ath, risk_avg, stop_loss, date_begin, date_end, comment);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_dSecurityRiscs_sp ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

}
