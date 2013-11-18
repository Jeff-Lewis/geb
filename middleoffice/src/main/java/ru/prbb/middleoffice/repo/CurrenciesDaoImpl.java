/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 *
 */
@Repository
@Transactional
public class CurrenciesDaoImpl implements CurrenciesDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<SimpleItem> findCombo(String query) {
		// select id, name from dbo.mo_WebGet_ajaxCurrency_v
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
}
