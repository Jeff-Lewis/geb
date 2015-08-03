/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.ClientsItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Клиенты
 * 
 * @author RBr
 */
@Service
public class ClientsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ClientsItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_SelectClients_sp}";
		return ems.getSelectList(user, ClientsItem.class, sql);
	}

	public ClientsItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_SelectClients_sp ?}";
		return ems.getSelectItem(user, ClientsItem.class, sql, id);
	}

	public int put(ArmUserInfo user, String name, String comment, Long country_id, Date date_begin, Date date_end) {
		String sql = "{call dbo.mo_WebSet_putClients_sp ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, name, comment, country_id, date_begin, date_end);
	}

	public int updateById(ArmUserInfo user, Long id, String name, String comment, Long country_id, Date date_begin, Date date_end) {
		String sql = "{call dbo.mo_WebSet_udClients_sp 'u', ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, name, comment, country_id, date_begin, date_end);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_udClients_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxClient_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}
}
