/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.GroupItem;
import ru.prbb.analytics.domain.SimpleItem;

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
	public List<GroupItem> findAllAddresses(Long id) {
		String sql = "{call dbo.WebGet_SelectContactsAddress_sp :id}";
		return em.createQuery(sql, GroupItem.class).setParameter(1, id).getResultList();
	}

	@Override
	public List<GroupItem> getContacts(Long id) {
		String sql = "{call dbo.WebGet_SelectGroupContacts_sp ?}";
		return em.createQuery(sql, GroupItem.class).setParameter(1, id).getResultList();
	}

	@Override
	public int[] putStaff(Long id, Long[] cids) {
		String sql = "{call dbo.WebSet_mapContactToGroup_sp :id, :cid}";
		int i = 0;
		int[] res = new int[cids.length];
		for (Long cid : cids) {
			try {
				res[i++] = em.createQuery(sql).setParameter(1, id).setParameter(2, cid).executeUpdate();
			} catch (DataAccessException e) {
				// TODO: handle exception
			}
		}
		return res;
	}

	@Override
	public int[] deleteStaff(Long id, Long[] cids) {
		String sql = "{call dbo.WebSet_dContactFromGroup_sp :id, :cid}";
		int i = 0;
		int[] res = new int[cids.length];
		for (Long cid : cids) {
			try {
				res[i++] = em.createQuery(sql).setParameter(1, id).setParameter(2, cid).executeUpdate();
			} catch (DataAccessException e) {
				// TODO: handle exception
			}
		}
		return res;
	}

}
