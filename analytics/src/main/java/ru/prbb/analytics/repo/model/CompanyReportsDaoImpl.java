/**
 * 
 */
package ru.prbb.analytics.repo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.CompanyAllItem;
import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.BaseDaoImpl;

/**
 * Компании и отчёты
 * 
 * @author RBr
 * 
 */
@Repository
public class CompanyReportsDaoImpl extends BaseDaoImpl implements CompanyReportsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findAll() {
		String sql = "{call dbo.anca_WebGet_Reports_sp}";
		Query q = em.createNativeQuery(sql, SimpleItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public SimpleItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_Reports_sp ?}";
		Query q = em.createNativeQuery(sql, SimpleItem.class)
				.setParameter(1, id);
		return (SimpleItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name) {
		String sql = "{call dbo.anca_WebSet_putReports_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int renameById(Long id, String name) {
		String sql = "{call dbo.anca_WebSet_udReports_sp 'u', ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.anca_WebSet_udReports_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyAllItem> findStaff(Long id) {
		String sql = "{call dbo.anca_WebGet_EquitiesNotInReport_sp ?}";
		Query q = em.createNativeQuery(sql, CompanyAllItem.class)
				.setParameter(1, id);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<CompanyStaffItem> findStaffReport(Long id) {
		String sql = "{call dbo.anca_WebGet_Security_report_maps_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<CompanyStaffItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			CompanyStaffItem item = new CompanyStaffItem();
			item.setId(Utils.toLong(arr[0]));
			item.setSecurity_code(Utils.toString(arr[2]));
			item.setShort_name(Utils.toString(arr[3]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] putStaff(Long report_id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_putSecurity_report_maps_sp ?, ?}";
		int i = 0;
		int[] res = new int[cids.length];
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, report_id);
		for (Long security_id : cids) {
			try {
				q.setParameter(2, security_id);
				storeSql(sql, q);
				res[i++] = q.executeUpdate();
			} catch (DataAccessException e) {
				log.error("putStaff", e);
			}
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] deleteStaff(Long report_id, Long[] cids) {
		String sql = "{call dbo.anca_WebSet_udSecurity_report_maps_sp 'd', ?}";
		int i = 0;
		int[] res = new int[cids.length];
		Query q = em.createNativeQuery(sql);
		for (Long cid : cids) {
			try {
				q.setParameter(1, cid);
				//q.setParameter(2, report_id);
				storeSql(sql, q);
				res[i++] = q.executeUpdate();
			} catch (Exception e) {
				log.error("deleteStaff", e);
			}
		}
		return res;
	}

}
