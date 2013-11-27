/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.List;

import javax.persistence.EntityManager;

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

	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.WebGet_SelectContacts_sp}";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

	@Override
	public SimpleItem findById(Long id) {
		String sql = "{call dbo.WebGet_SelectContactInfo_sp :id}";
		return em.createQuery(sql, SimpleItem.class).setParameter(1, id).getSingleResult();
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
	public List<ContactStaffItem> findAllStaff(Long id) {
		String sql = "{call dbo.WebGet_SelectContactInfo_sp ?}";
		return em.createQuery(sql, ContactStaffItem.class).setParameter(1, id).getResultList();
	}

	@Override
	public void putStaff(Long id, String name, Integer type) {
		String sql = "{call dbo.WebSet_putContactInfo_sp ?, ?, ?}";

	}

	@Override
	public void updateByIdStaff(Long id, Long cid, String name) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'u', ?, ?}";

	}

	@Override
	public void deleteByIdStaff(Long id, Long cid) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'd', ?}";

	}

}
