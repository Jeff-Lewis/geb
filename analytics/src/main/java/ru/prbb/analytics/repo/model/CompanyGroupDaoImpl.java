/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
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
@Transactional
public class CompanyGroupDaoImpl implements CompanyGroupDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.anca_WebGet_PivotGroups_sp}";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

	@Override
	public SimpleItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_PivotGroups_sp :id}";
		return em.createQuery(sql, SimpleItem.class).setParameter(1, id).getSingleResult();
	}

	@Override
	public int put(String name) {
		String sql = "{call dbo.anca_WebSet_putPivotGroup_sp :name}";
		return em.createQuery(sql).setParameter(1, name).executeUpdate();
	}

	@Override
	public int renameById(Long id, String name) {
		String sql = "{call dbo.anca_WebSet_udPivotGroup_sp 'u', :id, :name}";
		return em.createQuery(sql).setParameter(1, id).setParameter(2, name).executeUpdate();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.anca_WebSet_udPivotGroup_sp 'd', :id}";
		return em.createQuery(sql).setParameter(1, id).executeUpdate();
	}

	@Override
	public List<CompanyStaffItem> findStaff() {
		String sql = "{call dbo.anca_WebGet_SelectEquitiesNotPivotGroup_sp}";
		return em.createQuery(sql, CompanyStaffItem.class).getResultList();
	}

	@Override
	public List<CompanyStaffItem> findStaff(Long id) {
		String sql = "{call dbo.anca_WebGet_SelectPivotGroupEquities_sp :id}";
		return em.createQuery(sql, CompanyStaffItem.class).setParameter(1, id).getResultList();
	}

	@Override
	public int[] putStaff(Long id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_addEquityPivotGroup_sp :id, :cid}";
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
	public int[] deleteStaff(Long _id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_removeEquityPivotGroup_sp :cid}";
		int i = 0;
		int[] res = new int[cids.length];
		for (Long cid : cids) {
			try {
				res[i++] = em.createQuery(sql).setParameter(1, cid).executeUpdate();
			} catch (DataAccessException e) {
				// TODO: handle exception
			}
		}
		return res;
	}

}
