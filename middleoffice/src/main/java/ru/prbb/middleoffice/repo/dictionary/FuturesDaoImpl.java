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
import ru.prbb.middleoffice.domain.FuturesItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Фьючерсы
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class FuturesDaoImpl implements FuturesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<FuturesItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SelectFuturesAlias_sp}";
		Query q = em.createNativeQuery(sql, FuturesItem.class);
		return q.getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public FuturesItem findById(Long id) {
		// "{call dbo.mo_WebGet_SelectFuturesAlias_sp ?}"
		throw new IllegalAccessError("Method not implemented.");
	}

	@Override
	public int put(String name, Double coef, String comment) {
		String sql = "{call dbo.mo_WebSet_putFuturesAlias_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, coef)
				.setParameter(3, comment);
		return q.executeUpdate();
	}

	@Override
	public int updateById(Long id, String name, String comment) {
		// "{call dbo.mo_WebSet_udFuturesAlias_sp 'u', ?, ?, ?, ?}"
		throw new IllegalAccessError("Method not implemented.");
	}

	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udFuturesAlias_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

	@Transactional(readOnly = true)
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
		return q.getResultList();
	}
}
