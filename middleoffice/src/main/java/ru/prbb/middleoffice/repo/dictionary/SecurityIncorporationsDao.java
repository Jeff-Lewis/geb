/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.SecurityIncorporationItem;
import ru.prbb.middleoffice.domain.SecurityIncorporationListItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Регистрация инструментов
 * 
 * @author RBr
 */
@Service
public class SecurityIncorporationsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SecurityIncorporationListItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SecurityIncorporations_sp}";
		return ems.getSelectList(user, SecurityIncorporationListItem.class, sql);
	}

	public SecurityIncorporationItem findById(ArmUserInfo user, Long id) {
		String sql = "select * from dbo.mo_WebGet_SecurityIncorporations_v where id = ?";
		return ems.getSelectItem(user, SecurityIncorporationItem.class, sql, id);
	}

	public int put(ArmUserInfo user, Long security, Long country, Date dateBegin) {
		String sql = "{call dbo.mo_WebSet_putSecurityIncorporations_sp ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, security, country, dateBegin);
	}

	public int updateById(ArmUserInfo user, Long id, Date dateBegin, Date dateEnd) {
		String sql = "{call dbo.mo_WebSet_udSecurityIncorporations_sp 'u', ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, dateBegin, dateEnd);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_udSecurityIncorporations_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

}
