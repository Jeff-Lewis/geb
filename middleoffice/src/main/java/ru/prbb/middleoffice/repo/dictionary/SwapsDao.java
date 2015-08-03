/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.SwapItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Свопы
 * 
 * @author RBr
 */
@Service
public class SwapsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SwapItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_TrsContracts_sp}";
		return ems.getSelectList(user, SwapItem.class, sql);
	}

	public SwapItem findById(ArmUserInfo user, Long id) {
		// String sql = "{call dbo.mo_WebGet_TrsContracts_sp ?}";
		throw new IllegalAccessError("Method not implemented.");
	}

	public int put(ArmUserInfo user, String swap, Long security) {
		String sql = "{call dbo.mo_WebSet_putTrsContract_sp ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, swap, security);
	}

	public int updateById(ArmUserInfo user, Long id, String swap) {
		String sql = "{call dbo.mo_WebSet_udTrsContracts_sp 'u', ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, swap);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_udTrsContracts_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

}
