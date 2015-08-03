/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.ViewDealsItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Список сделок
 * 
 * @author RBr
 */
@Service
public class ViewDealsDao
{

	@Autowired
	private EntityManagerService ems;

	// FIXME @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, timeout = 60000)
	public List<ViewDealsItem> findAll(ArmUserInfo user, Date begin, Date end,
			Long security, Long client, Long funds, Long initiator, Integer batch) {
		String sql = "{call dbo.mo_WebGet_SelectDeals_sp ?, ?, ?, ?, ?, ?, ?}";
		return ems.getSelectList(user, ViewDealsItem.class, sql, begin, end, security, client, funds, initiator, batch);
	}

	public void updateById(ArmUserInfo user, Long[] deals, String field, String value) {
		String sql = "{call dbo.mo_WebSet_setDealsAttr_sp ?, ?, ?}";
		for (Long deal : deals) {
			ems.executeUpdate(AccessAction.UPDATE, user, sql, deal, field, value);
		}
	}

	public void deleteById(ArmUserInfo user, Long[] deals) {
		String sql = "{call dbo.mo_WebSet_dDeals_sp ?}";
		for (Long deal : deals) {
			ems.executeUpdate(AccessAction.DELETE, user, sql, deal);
		}
	}

}
