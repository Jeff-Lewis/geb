/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.CountryItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Страны
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class CountriesDaoImpl implements CountriesDao
{
	@Autowired
	private EntityManager em;

	/**
	 * @return
	 */
	@Override
	public List<CountryItem> findAll() {
		// {call dbo.mo_WebGet_Countries_sp}
		final List<CountryItem> list = new ArrayList<CountryItem>();
		for (int i = 0; i < 10; i++) {
			final CountryItem item = new CountryItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	@Override
	public List<SimpleItem> findCombo(String query) {
		// select id, name from dbo.mo_WebGet_ajaxCountries_v
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
