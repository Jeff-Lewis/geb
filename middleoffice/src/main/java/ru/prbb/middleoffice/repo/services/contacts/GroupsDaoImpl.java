/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.GroupItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class GroupsDaoImpl implements GroupsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.WebGet_SelectContacts_sp}";
		ArrayList<SimpleItem> list = new ArrayList<SimpleItem>();
		for (long i = 1; i < 11; i++) {
			SimpleItem e = new SimpleItem();
			e.setId(i);
			e.setName("name" + i);
			list.add(e);
		}
		return list;
	}

	@Override
	public SimpleItem findById(Long id) {
		String sql = "{call dbo.WebGet_SelectContactInfo_sp ?}";
		return null;
	}

	@Override
	public void put(String name) {
		String sql = "{call dbo.WebSet_putСontact_sp ?}";

	}

	@Override
	public void updateById(Long id, String name) {
		String sql = "{call dbo.WebSet_udContact_sp 'u', ?, ?}";

	}

	@Override
	public void deleteById(Long id) {
		String sql = "{call dbo.WebSet_udContact_sp 'd', ?}";

	}

	@Override
	public List<GroupItem> findAllAddresses(Long id) {
		String sql = "{call dbo.WebGet_SelectContactsAddress_sp ?}";
		return null;
	}

	@Override
	public List<GroupItem> getContacts(Long id) {
		String sql = "{call dbo.WebGet_SelectGroupContacts_sp ?}";
		return null;
	}

	@Override
	public void putStaff(Long id, Long[] cids) {
		String sql = "{call dbo.WebSet_mapContactToGroup_sp ?, ?}";

	}

	@Override
	public void deleteStaff(Long id, Long[] cids) {
		String sql = "{call dbo.WebSet_dContactFromGroup_sp ?, ?}";

	}

}
