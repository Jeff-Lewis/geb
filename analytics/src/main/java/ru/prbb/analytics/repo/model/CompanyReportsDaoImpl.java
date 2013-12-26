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

import ru.prbb.analytics.domain.CompanyAllItem;
import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Компании и отчёты
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class CompanyReportsDaoImpl implements CompanyReportsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.anca_WebGet_Reports_sp}";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return q.getResultList();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public SimpleItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_Reports_sp ?}";
		Query q = em.createNativeQuery(sql, SimpleItem.class)
				.setParameter(1, id);
		return (SimpleItem) q.getSingleResult();
	}

	@Override
	public int put(String name) {
		String sql = "{call dbo.anca_WebSet_putReports_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name);
		return q.executeUpdate();
	}

	@Override
	public int renameById(Long id, String name) {
		String sql = "{call dbo.anca_WebSet_udReports_sp 'u', ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name);
		return q.executeUpdate();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.anca_WebSet_udReports_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyAllItem> findStaff(Long id) {
		String sql = "{call dbo.anca_WebGet_EquitiesNotInReport_sp ?}";
		Query q = em.createNativeQuery(sql, CompanyAllItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyStaffItem> findStaffReport(Long id) {
		String sql = "{call dbo.anca_WebGet_Security_report_maps_sp ?}";
		Query q = em.createNativeQuery(sql, CompanyStaffItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Override
	public int[] putStaff(Long id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_putSecurity_report_maps_sp ?, ?}";
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

	@Override
	public int[] deleteStaff(Long id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_udSecurity_report_maps_sp ?, ?}";
		int i = 0;
		int[] res = new int[cids.length];
		Query q = em.createNativeQuery(sql);
		for (Long cid : cids) {
			try {
				q.setParameter(1, id);
				q.setParameter(2, cid);
				res[i++] = q.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return res;
	}

}
