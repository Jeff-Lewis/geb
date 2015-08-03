/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.RiskRewardPrice1Item;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class RiskRewardPrice1Dao
{

	@Autowired
	private EntityManagerService ems;

	public List<RiskRewardPrice1Item> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SecuritiesAttributes_sp}";
		return ems.getSelectList(user, RiskRewardPrice1Item.class, sql);
	}

	public RiskRewardPrice1Item findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_SecuritiesAttributes_sp ?}";
		return ems.getSelectItem(user, RiskRewardPrice1Item.class, sql, id);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_dSecuritiesAttributes_sp ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

}
