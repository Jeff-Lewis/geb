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
import ru.prbb.middleoffice.domain.FuturesItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Фьючерсы
 * 
 * @author RBr
 * 
 */
@Repository
public class FuturesDaoImpl extends BaseDaoImpl implements FuturesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<FuturesItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SelectFuturesAlias_sp}";
		Query q = em.createNativeQuery(sql, FuturesItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public FuturesItem findById(Long id) {
		// "{call dbo.mo_WebGet_SelectFuturesAlias_sp ?}"
		throw new IllegalAccessError("Method not implemented.");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name, Double coef, String comment) {
		String sql = "{call dbo.mo_WebSet_putFuturesAlias_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, coef)
				.setParameter(3, comment);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Override
	public int updateById(Long id, String name, String comment) {
		// "{call dbo.mo_WebSet_udFuturesAlias_sp 'u', ?, ?, ?, ?}"
		throw new IllegalAccessError("Method not implemented.");
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udFuturesAlias_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxFuturesAlias_v";
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
