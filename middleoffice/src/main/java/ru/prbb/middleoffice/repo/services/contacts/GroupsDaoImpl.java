/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.GroupAddressItem;
import ru.prbb.middleoffice.domain.GroupContactsItem;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.WebGet_SelectGroups_sp}";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return q.getResultList();
	}

	@Override
	public SimpleItem findById(Long id) {
		List<SimpleItem> list = findAll();
		for (SimpleItem item : list) {
			if (id.equals(item.getId())) {
				return item;
			}
		}
		return null;
	}

	@Override
	public int put(String name) {
		String sql = "{call dbo.WebSet_putСontact_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name);
		return q.executeUpdate();
	}

	@Override
	public int updateById(Long id, String name) {
		String sql = "{call dbo.WebSet_udContact_sp 'u', ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name);
		return q.executeUpdate();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_udContact_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupAddressItem> findAllAddresses(Long id) {
		String sql = "{call dbo.WebGet_SelectContactsAddress_sp ?}";
		Query q = em.createNativeQuery(sql, GroupAddressItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GroupContactsItem> findAllContacts(Long id) {
		String sql = "{call dbo.WebGet_SelectGroupContacts_sp ?}";
		Query q = em.createNativeQuery(sql, GroupContactsItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Override
	public int[] putStaff(Long id, Long[] cids) {
		int[] res = new int[cids.length];
		String sql = "{call dbo.WebSet_mapContactToGroup_sp ?, ?}";
		for (int i = 0; i < cids.length; i++) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, id)
					.setParameter(2, cids[i]);
			res[i] = q.executeUpdate();
		}
		return res;
	}

	@Override
	public int[] deleteStaff(Long id, Long[] cids) {
		int[] res = new int[cids.length];
		String sql = "{call dbo.WebSet_dContactFromGroup_sp ?, ?}";
		for (int i = 0; i < cids.length; i++) {
			Query q = em.createNativeQuery(sql)
					.setParameter(1, id)
					.setParameter(2, cids[i]);
			res[i] = q.executeUpdate();
		}
		return res;
	}

}
