/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.ContactStaffItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Справочник контактов
 * 
 * @author RBr
 */
@Service
public class ContactsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SimpleItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.WebGet_SelectContacts_sp}";
		return ems.getSelectList(user, SimpleItem.class, sql);
	}

	public SimpleItem findById(ArmUserInfo user, Long id) {
		List<SimpleItem> list = findAll(user);
		for (SimpleItem item : list) {
			if (id.equals(item.getId())) {
				return item;
			}
		}
		return null;
	}

	public int put(ArmUserInfo user, String name) {
		String sql = "{call dbo.WebSet_putСontact_sp ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, name);
	}

	public int updateById(ArmUserInfo user, Long id, String name) {
		String sql = "{call dbo.WebSet_udContact_sp 'u', ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, name);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.WebSet_udContact_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<ContactStaffItem> findAllStaff(ArmUserInfo user, Long id) {
		String sql = "{call dbo.WebGet_SelectContactInfo_sp ?}";
		return ems.getSelectList(user, ContactStaffItem.class, sql, id);
	}

	public int putStaff(ArmUserInfo user, Long id, String name, Integer type) {
		String sql = "{call dbo.WebSet_putContactInfo_sp ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, id, name, type);
	}

	public int updateByIdStaff(ArmUserInfo user, Long id, Long cid, String name) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'u', ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, cid, name);
	}

	public int deleteByIdStaff(ArmUserInfo user, Long id, Long cid) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, cid);
	}

}
