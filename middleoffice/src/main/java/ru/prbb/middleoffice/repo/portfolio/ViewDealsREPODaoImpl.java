/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.ViewDealsREPOItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Сделки РЕПО
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewDealsREPODaoImpl extends BaseDaoImpl implements ViewDealsREPODao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewDealsREPOItem> findAll(Date begin, Date end, Long security) {
		String sql = "{call dbo.mo_WebGet_RepoDeals_sp null, ?, ?, ?}";
		Query q = em.createNativeQuery(sql, ViewDealsREPOItem.class)
				.setParameter(1, begin)
				.setParameter(2, end)
				.setParameter(3, security);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public ViewDealsREPOItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_RepoDeals_sp ?}";
		Query q = em.createNativeQuery(sql, ViewDealsREPOItem.class)
				.setParameter(1, id);
		return (ViewDealsREPOItem) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, Double rate, Integer quantity, Double price, Integer days) {
		String sql = "{call dbo.mo_WebSet_setRepoDeals_sp ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, rate)
				.setParameter(3, quantity)
				.setParameter(4, price)
				.setParameter(5, days);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_dRepoDeals_sp ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

}
