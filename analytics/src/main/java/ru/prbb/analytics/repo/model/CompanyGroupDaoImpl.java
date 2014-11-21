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
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Компании и группы
 * 
 * @author RBr
 * 
 */
@Repository
public class CompanyGroupDaoImpl extends BaseDaoImpl implements CompanyGroupDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.anca_WebGet_PivotGroups_sp}";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public SimpleItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_PivotGroups_sp ?}";
		Query q = em.createNativeQuery(sql, SimpleItem.class)
				.setParameter(1, id);
		return (SimpleItem) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name) {
		String sql = "{call dbo.anca_WebSet_putPivotGroup_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int renameById(Long id, String name) {
		String sql = "{call dbo.anca_WebSet_udPivotGroup_sp 'u', ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.anca_WebSet_udPivotGroup_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyStaffItem> findStaff() {
		String sql = "{call dbo.anca_WebGet_SelectEquitiesNotPivotGroup_sp}";
		Query q = em.createNativeQuery(sql, CompanyStaffItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyStaffItem> findStaff(Long id) {
		String sql = "{call dbo.anca_WebGet_SelectPivotGroupEquities_sp ?}";
		Query q = em.createNativeQuery(sql, CompanyStaffItem.class)
				.setParameter(1, id);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] putStaff(Long group_id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_addEquityPivotGroup_sp ?, ?}";
		int i = 0;
		int[] res = new int[cids.length];
		Query q = em.createNativeQuery(sql);
		q.setParameter(2, group_id);
		for (Long id_sec : cids) {
			try {
				q.setParameter(1, id_sec);
				storeSql(sql, q);
				res[i++] = executeUpdate(q, sql);
			} catch (DataAccessException e) {
				log.error("putStaff", e);
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
		for (Long id_sec : cids) {
			try {
				q.setParameter(1, id_sec);
				storeSql(sql, q);
				res[i++] = executeUpdate(q, sql);
			} catch (DataAccessException e) {
				log.error("deleteStaff", e);
			}
		}
		return res;
	}

}
