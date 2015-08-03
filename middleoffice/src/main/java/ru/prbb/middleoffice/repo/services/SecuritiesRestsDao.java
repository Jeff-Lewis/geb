/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.SecuritiesRestsItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class SecuritiesRestsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SecuritiesRestsItem> execute(ArmUserInfo user, Long security, Long client, Long fund, Integer batch, Date date) {
		String sql = "{call dbo.mo_WebGet_securities_rests_sp null, ?, ?, ?, ?, ?}";
		return ems.getSelectList(user, SecuritiesRestsItem.class, sql, security, fund, batch, client, date);
	}

	public int updateById(ArmUserInfo user, Long id, Byte checkFlag) {
		String sql = "{call dbo.mo_WebSet_set_securities_rests_sp ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, checkFlag);
	}

}
