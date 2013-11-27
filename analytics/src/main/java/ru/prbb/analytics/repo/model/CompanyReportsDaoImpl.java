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

	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.anca_WebGet_Reports_sp}";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

	@Override
	public SimpleItem findById(Long id) {
		SimpleItem item = new SimpleItem();
		item.setId(id);
		item.setName("NAME_" + id);
		return item;
	}

	@Override
	public void put(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renameById(Long id, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CompanyStaffItem> findStaff(Long id) {
		String sql = "{call dbo.anca_WebGet_EquitiesNotInReport_sp :id}";
		return em.createQuery(sql, CompanyStaffItem.class).setParameter(1, id).getResultList();
	}

	@Override
	public List<CompanyStaffItem> findStaffReport(Long id) {
		String sql = "{call dbo.anca_WebGet_Security_report_maps_sp :id}";
		return em.createQuery(sql, CompanyStaffItem.class).setParameter(1, id).getResultList();
	}

	@Override
	public int[] putStaff(Long id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_putSecurity_report_maps_sp :id, :cid}";
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
		String sql = "{call dbo.anca_WebSet_udSecurity_report_maps_sp :id, :cid}";
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
