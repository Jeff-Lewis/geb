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
import ru.prbb.middleoffice.domain.BrokerAccountItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Брокерские счета
 * 
 * @author RBr
 * 
 */
@Repository
public class BrokerAccountsDaoImpl extends BaseDaoImpl implements BrokerAccountsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<BrokerAccountItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SelectAccount_sp}";
		Query q = em.createNativeQuery(sql, BrokerAccountItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public BrokerAccountItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SelectAccount_sp ?}";
		Query q = em.createNativeQuery(sql, BrokerAccountItem.class)
				.setParameter(1, id);
		return (BrokerAccountItem) getSingleResult(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name, String client, String broker, String comment) {
		String sql = "{call dbo.mo_WebSet_putAccount_sp ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, client)
				.setParameter(3, broker)
				.setParameter(4, comment);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, String name, String comment) {
		String sql = "{call dbo.mo_WebSet_udAccount_sp 'u', ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name)
				.setParameter(3, comment);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udAccount_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxAccount_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return getResultList(q, sql);
	}

}
