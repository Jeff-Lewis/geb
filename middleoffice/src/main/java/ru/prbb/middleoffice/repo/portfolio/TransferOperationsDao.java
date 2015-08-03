/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.TransferOperationsItem;
import ru.prbb.middleoffice.domain.TransferOperationsListItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Список перекидок
 * 
 * @author RBr
 */
@Service
public class TransferOperationsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<TransferOperationsListItem> findAll(ArmUserInfo user, Date begin, Date end, Long security) {
		String sql = "{call dbo.mo_WebGet_SelectTransferOperations_sp ?, ?, ?}";
		return ems.getSelectList(user, TransferOperationsListItem.class, sql, begin, end, security);
	}

	public List<TransferOperationsItem> findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_SelectTransferDeals_sp ?}";
		return ems.getSelectList(user, TransferOperationsItem.class, sql, id);
	}

	public void updateById(ArmUserInfo user, Long[] ids, String field, String value) {
		String sql = "{call dbo.mo_WebSet_setTransferAttributes_sp ?, ?, ?}";
		for (Long id : ids) {
			ems.executeUpdate(AccessAction.UPDATE, user, sql, id, field, value);
		}
	}

	public void deleteById(ArmUserInfo user, Long[] ids) {
		String sql = "{call dbo.mo_WebSet_dTransferDeals_sp ?}";
		for (Long id : ids) {
			ems.executeUpdate(AccessAction.DELETE, user, sql, id);
		}
	}

}
