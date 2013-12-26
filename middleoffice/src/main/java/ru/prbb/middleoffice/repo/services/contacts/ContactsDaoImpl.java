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

import ru.prbb.middleoffice.domain.ContactStaffItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ContactsDaoImpl implements ContactsDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.WebGet_SelectContacts_sp}";
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
	public List<ContactStaffItem> findAllStaff(Long id) {
		String sql = "{call dbo.WebGet_SelectContactInfo_sp ?}";
		Query q = em.createNativeQuery(sql, ContactStaffItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Override
	public int putStaff(Long id, String name, Integer type) {
		String sql = "{call dbo.WebSet_putContactInfo_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name)
				.setParameter(3, type);
		return q.executeUpdate();
	}

	@Override
	public int updateByIdStaff(Long id, Long cid, String name) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'u', ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, cid)
				.setParameter(2, name);
		return q.executeUpdate();
	}

	@Override
	public int deleteByIdStaff(Long id, Long cid) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

}
