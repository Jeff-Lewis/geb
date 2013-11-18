/**
 * 
 */
package ru.prbb.analytics.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.EquityItem;
import ru.prbb.analytics.domain.SimpleItem;

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

	@Override
	public List<SimpleItem> getFilter(String query) {
		String sql = "select null as id, name from dbo.anca_WebGet_ajaxEquityFilter_v";
		return em.createQuery(sql, SimpleItem.class).getResultList();
	}

	@Override
	public List<SimpleItem> getSecurities(String query) {
		// "select id, name from dbo.anca_WebGet_ajaxEquity_v"
		// " where lower(name) like ?"
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (int i = 0; i < 10; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	@Override
	public List<EquityItem> getSecurities(String filter, Long security, Integer fundamentals) {
		// "{call dbo.anca_WebGet_EquityFilter_sp}"
		// "{call dbo.anca_WebGet_EquityFilter_sp ?, ?, ?}"
		final List<EquityItem> list = new ArrayList<EquityItem>();
		for (long i = 1; i < 11; ++i) {
			final EquityItem item = new EquityItem();
			item.setId_sec(i);
			item.setSecurity_code("security_code" + i);
			item.setShort_name("short_name" + i);
			list.add(item);
		}
		return list;
	}

}
