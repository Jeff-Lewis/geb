/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.ReferenceItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Фонды
 * 
 * @author RBr
 * 
 */
@Repository
public class FundsDaoImpl extends BaseDaoImpl implements FundsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ReferenceItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SelectPortfolio_sp}";
		Query q = em.createNativeQuery(sql, ReferenceItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public ReferenceItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SelectPortfolio_sp ?}";
		Query q = em.createNativeQuery(sql, ReferenceItem.class)
				.setParameter(1, id);
		return (ReferenceItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name, String comment) {
		String sql = "{call dbo.mo_WebSet_putInvestPortfolio_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, comment);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, String name, String comment) {
		String sql = "{call dbo.mo_WebSet_udInvestPortfolio_sp 'u', ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name)
				.setParameter(3, comment);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udInvestPortfolio_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxPortfolio_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return q.getResultList();
	}
}
