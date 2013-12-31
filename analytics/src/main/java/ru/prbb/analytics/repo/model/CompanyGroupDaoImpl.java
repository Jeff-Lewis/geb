/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Компании и группы
 * 
 * @author RBr
 * 
 */
@Repository
public class CompanyGroupDaoImpl implements CompanyGroupDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.anca_WebGet_PivotGroups_sp}";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public SimpleItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_PivotGroups_sp ?}";
		Query q = em.createNativeQuery(sql, SimpleItem.class)
				.setParameter(1, id);
		return (SimpleItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name) {
		String sql = "{call dbo.anca_WebSet_putPivotGroup_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int renameById(Long id, String name) {
		String sql = "{call dbo.anca_WebSet_udPivotGroup_sp 'u', ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.anca_WebSet_udPivotGroup_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyStaffItem> findStaff() {
		String sql = "{call dbo.anca_WebGet_SelectEquitiesNotPivotGroup_sp}";
		Query q = em.createNativeQuery(sql, CompanyStaffItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyStaffItem> findStaff(Long id) {
		String sql = "{call dbo.anca_WebGet_SelectPivotGroupEquities_sp ?}";
		Query q = em.createNativeQuery(sql, CompanyStaffItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] putStaff(Long id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_addEquityPivotGroup_sp ?, ?}";
		int i = 0;
		int[] res = new int[cids.length];
		Query q = em.createNativeQuery(sql);
		for (Long cid : cids) {
			try {
				q.setParameter(1, id);
				q.setParameter(2, cid);
				res[i++] = q.executeUpdate();
			} catch (DataAccessException e) {
				// TODO: handle exception
			}
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] deleteStaff(Long _id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_removeEquityPivotGroup_sp ?}";
		int i = 0;
		int[] res = new int[cids.length];
		Query q = em.createNativeQuery(sql);
		for (Long cid : cids) {
			try {
				q.setParameter(1, cid);
				res[i++] = q.executeUpdate();
			} catch (DataAccessException e) {
				// TODO: handle exception
			}
		}
		return res;
	}

}
