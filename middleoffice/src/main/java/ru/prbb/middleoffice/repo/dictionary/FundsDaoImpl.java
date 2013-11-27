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

	/**
	 * @return
	 */
	public List<ReferenceItem> findAll() {
		// {call dbo.mo_WebGet_SelectPortfolio_sp}
		//return em.createQuery("{call dbo.anca_WebGet_SelectBrokers_sp}", ReferenceItem.class).getResultList();
		final List<ReferenceItem> list = new ArrayList<ReferenceItem>();
		for (int i = 0; i < 10; i++) {
			final ReferenceItem item = new ReferenceItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			item.setComment("comment" + (i + 1));
			list.add(item);
		}
		return list;
	}

	/**
	 * @param id
	 * @return
	 */
	public ReferenceItem findById(Long id) {
		// {call dbo.mo_WebGet_SelectPortfolio_sp ?}
		final ReferenceItem item = new ReferenceItem();
		item.setId(id);
		item.setName("name" + id);
		item.setComment("comment" + id);
		return item;
	}

	/**
	 * 
	 */
	@Override
	public Long put(ReferenceItem value) {
		// "{call dbo.mo_WebSet_putInvestPortfolio_sp ?, ?}"
		return value.getId();
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, ReferenceItem value) {
		// "{call dbo.mo_WebSet_udInvestPortfolio_sp 'u', ?, ?, ?}"
		return id;
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		// "{call dbo.mo_WebSet_udInvestPortfolio_sp 'd', ?}"
	}

	@Override
	public List<SimpleItem> findCombo(String query) {
		// select id, name from dbo.mo_WebGet_ajaxPortfolio_v
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