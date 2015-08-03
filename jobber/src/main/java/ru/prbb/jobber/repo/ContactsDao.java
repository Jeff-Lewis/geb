/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.ContactStaffItem;
import ru.prbb.jobber.domain.SimpleItem;
import ru.prbb.jobber.services.EntityManagerService;

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

	public List<SimpleItem> findAll() {
		String sql = "{call dbo.WebGet_SelectContacts_sp}";
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
		String sql = "{call dbo.WebSet_putСontact_sp ?}";
		return ems.executeUpdate(sql, name);
	}

	public int updateById(Long id, String name) {
		String sql = "{call dbo.WebSet_udContact_sp 'u', ?, ?}";
		return ems.executeUpdate(sql, id, name);
	}

	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_udContact_sp 'd', ?}";
		return ems.executeUpdate(sql, id);
	}

	public List<ContactStaffItem> findAllStaff(Long id) {
		String sql = "{call dbo.WebGet_SelectContactInfo_sp ?}";
		return ems.getSelectList(ContactStaffItem.class, sql, id);
	}

	public int putStaff(Long id, String name, Integer type) {
		String sql = "{call dbo.WebSet_putContactInfo_sp ?, ?, ?}";
		return ems.executeUpdate(sql, id, name, type);
	}

	public int updateByIdStaff(Long id, Long cid, String name) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'u', ?, ?}";
		return ems.executeUpdate(sql, cid, name);
	}

	public int deleteByIdStaff(Long id, Long cid) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'd', ?}";
		return ems.executeUpdate(sql, cid);
	}

}
