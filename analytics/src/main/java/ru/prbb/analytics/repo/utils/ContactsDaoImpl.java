/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ContactStaffItem;
import ru.prbb.analytics.domain.SimpleItem;

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
	public int put(String name) {
		String sql = "{call dbo.WebSet_putСontact_sp :name}";
		return em.createQuery(sql).setParameter(1, name).executeUpdate();
	}

	@Override
	public int updateById(Long id, String name) {
		String sql = "{call dbo.WebSet_udContact_sp 'u', :id, :name}";
		return em.createQuery(sql).setParameter(1, id).setParameter(2, name).executeUpdate();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.WebSet_udContact_sp 'd', :id}";
		return em.createQuery(sql).setParameter(1, id).executeUpdate();
	}

	@Override
	public List<ContactStaffItem> findAllStaff(Long id) {
		String sql = "{call dbo.WebGet_SelectContactInfo_sp :id}";
		return em.createQuery(sql, ContactStaffItem.class).setParameter(1, id).getResultList();
	}

	@Override
	public int putStaff(Long id, String name, Integer type) {
		String sql = "{call dbo.WebSet_putContactInfo_sp :id, :name, :type}";
		return em.createQuery(sql).setParameter(1, id).setParameter(2, name).setParameter(3, type).executeUpdate();
	}

	@Override
	public int updateByIdStaff(Long id, Long cid, String name) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'u', :cid, :name}";
		return em.createQuery(sql).setParameter(1, cid).setParameter(2, name).executeUpdate();
	}

	@Override
	public int deleteByIdStaff(Long id, Long cid) {
		String sql = "{call dbo.WebSet_udContactInfo_sp 'd', :cid}";
		return em.createQuery(sql).setParameter(1, cid).executeUpdate();
	}

}
