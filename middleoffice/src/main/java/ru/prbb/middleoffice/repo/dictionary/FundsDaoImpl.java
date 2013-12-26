/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.ReferenceItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Фонды
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class FundsDaoImpl implements FundsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ReferenceItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SelectPortfolio_sp}";
		Query q = em.createNativeQuery(sql, ReferenceItem.class);
		return q.getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public ReferenceItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SelectPortfolio_sp ?}";
		Query q = em.createNativeQuery(sql, ReferenceItem.class)
				.setParameter(1, id);
		return (ReferenceItem) q.getSingleResult();
	}

	@Override
	public int put(String name, String comment) {
		String sql = "{call dbo.mo_WebSet_putInvestPortfolio_sp ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, comment);
		return q.executeUpdate();
	}

	@Override
	public int updateById(Long id, String name, String comment) {
		String sql = "{call dbo.mo_WebSet_udInvestPortfolio_sp 'u', ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name)
				.setParameter(3, comment);
		return q.executeUpdate();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udBrokers_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

	@Transactional(readOnly = true)
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
