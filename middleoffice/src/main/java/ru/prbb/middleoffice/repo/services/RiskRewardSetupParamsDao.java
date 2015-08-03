/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.RiskRewardSetupParamsItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class RiskRewardSetupParamsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<RiskRewardSetupParamsItem> findAll(ArmUserInfo user, Long security, Date date) {
		String sql = "{call dbo.mo_WebGet_SecurityParams_sp null, ?, ?}";
		return ems.getSelectList(user, RiskRewardSetupParamsItem.class, sql, security, date);
	}

	public RiskRewardSetupParamsItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_SecurityParams_sp ?}";
		return ems.getSelectItem(user, RiskRewardSetupParamsItem.class, sql, id);
	}

}
