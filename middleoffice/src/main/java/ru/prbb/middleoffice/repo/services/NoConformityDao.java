/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.NoConformityItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Нет соответствия
 * 
 * @author RBr
 */
@Service
public class NoConformityDao
{

	@Autowired
	private EntityManagerService ems;

	public List<NoConformityItem> show(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_DealBlmTickerUnSet_sp}";
		return ems.getSelectList(user, NoConformityItem.class, sql);
	}

	public int[] delete(ArmUserInfo user, Long[] ids) {
		String sql = "{call dbo.mo_WebSet_dTickerUnSetDeals_sp ?}";
		int[] res = new int[ids.length];
		for (int i = 0; i < ids.length; i++) {
			res[i] = ems.executeUpdate(AccessAction.DELETE, user, sql, ids[i]);
		}
		return res;
	}

}
