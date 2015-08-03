/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.GroupAddressItem;
import ru.prbb.middleoffice.domain.GroupContactsItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Справочник контактов
 * 
 * @author RBr
 */
@Service
public class GroupsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SimpleItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.WebGet_SelectGroups_sp}";
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
		String sql = "{call dbo.WebSet_putGroup_sp ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, name);
	}

	public int updateById(ArmUserInfo user, Long id, String name) {
		String sql = "{call dbo.WebSet_udGroup_sp 'u', ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, name);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.WebSet_udGroup_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

	public List<GroupAddressItem> findAllAddresses(ArmUserInfo user, Long id) {
		String sql = "{call dbo.WebGet_SelectContactsAddress_sp ?}";
		return ems.getSelectList(user, GroupAddressItem.class, sql, id);
	}

	public List<GroupContactsItem> findAllContacts(ArmUserInfo user, Long id) {
		String sql = "{call dbo.WebGet_SelectGroupContacts_sp ?}";
		return ems.getSelectList(user, GroupContactsItem.class, sql, id);
	}

	public int[] putStaff(ArmUserInfo user, Long id, Long[] cids) {
		String sql = "{call dbo.WebSet_mapContactToGroup_sp ?, ?}";
		int[] res = new int[cids.length];
		for (int i = 0; i < cids.length; i++) {
			res[i] = ems.executeUpdate(AccessAction.UPDATE, user, sql, id, cids[i]);
		}
		return res;
	}

	public int[] deleteStaff(ArmUserInfo user, Long id, Long[] cids) {
		String sql = "{call dbo.WebSet_dContactFromGroup_sp ?, ?}";
		int[] res = new int[cids.length];
		for (int i = 0; i < cids.length; i++) {
			res[i] = ems.executeUpdate(AccessAction.DELETE, user, sql, id, cids[i]);
		}
		return res;
	}

}
