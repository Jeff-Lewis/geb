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

import ru.prbb.middleoffice.domain.CountryTaxItem;

/**
 * Налоги по странам
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class CountryTaxesDaoImpl implements CountryTaxesDao
{
	@Autowired
	private EntityManager em;

	/**
	 * @return
	 */
	public List<CountryTaxItem> findAll() {
		// {call dbo.mo_WebGet_CountryTaxes_sp}
		//return em.createQuery("{call dbo.anca_WebGet_SelectBrokers_sp}", ReferenceItem.class).getResultList();
		final List<CountryTaxItem> list = new ArrayList<CountryTaxItem>();
		for (int i = 0; i < 10; i++) {
			final CountryTaxItem item = new CountryTaxItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			list.add(item);
		}
		return list;
	}

	/**
	 * @param id
	 * @return
	 */
	public CountryTaxItem findById(Long id) {
		// {call dbo.mo_WebGet_CountryTaxes_sp ?}
		final CountryTaxItem item = new CountryTaxItem();
		item.setId(id);
		item.setName("name" + id);
		return item;
	}

	/**
	 * 
	 */
	@Override
	public Long put(CountryTaxItem value) {
		// "{call dbo.mo_WebSet_putCountryTaxes_sp ?, ?, ?, ?, ?}"
		return value.getId();
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, CountryTaxItem value) {
		// "{call dbo.mo_WebSet_udCountryTaxes_sp 'u', ?, ?, ?, ?}"
		return id;
	}

	/**
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id) {
		// "{call dbo.mo_WebSet_udCountryTaxes_sp 'd', ?}"
		return id;
	}

}
