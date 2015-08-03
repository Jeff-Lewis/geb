/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.ViewDealsREPOItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Сделки РЕПО
 * 
 * @author RBr
 */
@Service
public class ViewDealsREPODao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewDealsREPOItem> findAll(ArmUserInfo user, Date begin, Date end, Long security) {
		String sql = "{call dbo.mo_WebGet_RepoDeals_sp null, ?, ?, ?}";
		return ems.getSelectList(user, ViewDealsREPOItem.class, sql, begin, end, security);
	}

	public ViewDealsREPOItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_RepoDeals_sp ?}";
		return ems.getSelectItem(user, ViewDealsREPOItem.class, sql, id);
	}

	public int updateById(ArmUserInfo user, Long id, Double rate, Integer quantity, Double price, Integer days) {
		String sql = "{call dbo.mo_WebSet_setRepoDeals_sp ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, rate, quantity, price, days);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_dRepoDeals_sp ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

}
