/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.ViewDetailedFinrezItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Текущий финрез
 * 
 * @author RBr
 */
@Service
public class ViewDetailedFinrezDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewDetailedFinrezItem> executeSelect(ArmUserInfo user, Long security, Date begin, Date end, Long client, Long fund, Long initiator) {
		String sql = "{call dbo.mo_WebGet_SelectDetailedFinRez_sp ?, ?, ?, ?, ?, ?}";
		return ems.getSelectList(user, ViewDetailedFinrezItem.class, sql, security, begin, end, client, fund, initiator);
	}

}
