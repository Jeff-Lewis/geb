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
import ru.prbb.middleoffice.domain.CurrenciesItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Валюты
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class CurrenciesDaoImpl implements CurrenciesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CurrenciesItem> findAll() {
		String sql = "{call dbo.mo_WebGet_Currency_sp}";
		Query q = em.createNativeQuery(sql, CurrenciesItem.class);
		return q.getResultList();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxCurrency_v";
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
