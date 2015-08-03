/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.GroupAddressItem;
import ru.prbb.jobber.domain.GroupContactsItem;
import ru.prbb.jobber.domain.SimpleItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * Справочник контактов
 * Repository
 * 
 * @author RBr
 */
@Service
public class GroupsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<SimpleItem> findAll() {
		String sql = "{call dbo.WebGet_SelectGroups_sp}";
		return ems.getSelectList(SimpleItem.class, sql);
	}

	public SimpleItem findById(Long id) {
		List<SimpleItem> list = findAll();
		for (SimpleItem item : list) {
			if (id.equals(item.getId())) {
				return item;
			}
		}
		return null;
	}

	public int put(String name) {
		String sql = "{call dbo.WebSet_putGroup_sp ?}";
		return ems.executeUpdate(sql, name);
	}

	public int updateById(Long id, String name) {
		String sql = "{call dbo.WebSet_udGroup_sp 'u', ?, ?}";
		return ems.executeUpdate(sql, id, name);
	}

	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_udGroup_sp 'd', ?}";
		return ems.executeUpdate(sql, id);
	}

	public List<GroupAddressItem> findAllAddresses(Long id) {
		String sql = "{call dbo.WebGet_SelectContactsAddress_sp ?}";
		return ems.getSelectList(GroupAddressItem.class, sql, id);
	}

	public List<GroupContactsItem> findAllContacts(Long id) {
		String sql = "{call dbo.WebGet_SelectGroupContacts_sp ?}";
		return ems.getResultList(GroupContactsItem.class, sql, id);
	}

	public int[] putStaff(Long id, Long[] cids) {
		String sql = "{call dbo.WebSet_mapContactToGroup_sp ?, ?}";
		int[] res = new int[cids.length];
		for (int i = 0; i < cids.length; i++) {
			res[i] = ems.executeUpdate(sql, id, cids[i]);
		}
		return res;
	}

	public int[] deleteStaff(Long id, Long[] cids) {
		String sql = "{call dbo.WebSet_dContactFromGroup_sp ?, ?}";
		int[] res = new int[cids.length];
		for (int i = 0; i < cids.length; i++) {
			res[i] = ems.executeUpdate(sql, id, cids[i]);
		}
		return res;
	}

}
