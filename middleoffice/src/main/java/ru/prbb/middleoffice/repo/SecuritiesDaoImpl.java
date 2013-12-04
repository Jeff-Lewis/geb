/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 *
 */
@Repository
@Transactional
public class SecuritiesDaoImpl implements SecuritiesDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityItem> findAll(String filter, Long security) {
		String sql = "{call dbo.mo_WebGet_FilterSecurities_sp ?, ?}";
		Query q = em.createNativeQuery(sql, SecurityItem.class)
				.setParameter(1, filter)
				.setParameter(2, security);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxSecurities_v";
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

	@Override
	public List<SimpleItem> findComboFilter(String query) {
		String sql = "select name from dbo.mo_WebGet_ajaxFilterRequest_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, query.toLowerCase() + '%');
		}
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<SimpleItem> res = new ArrayList<SimpleItem>(list.size());
		long id = 0;
		for (Object object : list) {
			SimpleItem item = new SimpleItem();
			item.setId(++id);
			item.setName(Utils.toString(object));
			res.add(item);
		}
		return res;
	}

}
