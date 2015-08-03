/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.ReferenceItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Фонды
 * 
 * @author RBr
 */
@Service
public class FundsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ReferenceItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SelectPortfolio_sp}";
		return ems.getSelectList(user, ReferenceItem.class, sql);
	}

	public ReferenceItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_SelectPortfolio_sp ?}";
		return ems.getSelectItem(user, ReferenceItem.class, sql, id);
	}

	public int put(ArmUserInfo user, String name, String comment) {
		String sql = "{call dbo.mo_WebSet_putInvestPortfolio_sp ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, name, comment);
	}

	public int updateById(ArmUserInfo user, Long id, String name, String comment) {
		String sql = "{call dbo.mo_WebSet_udInvestPortfolio_sp 'u', ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, name, comment);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_udInvestPortfolio_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxPortfolio_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}
}
