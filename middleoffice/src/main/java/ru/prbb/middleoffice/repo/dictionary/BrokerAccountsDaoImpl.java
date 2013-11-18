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

import ru.prbb.middleoffice.domain.BrokerAccountItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Брокерские счета
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class BrokerAccountsDaoImpl implements BrokerAccountsDao
{
	@Autowired
	private EntityManager em;

	/**
	 * @return
	 */
	public List<BrokerAccountItem> findAll() {
		// {call dbo.mo_WebGet_SelectAccount_sp}
		//return em.createQuery("{call dbo.anca_WebGet_SelectBrokers_sp}", ReferenceItem.class).getResultList();
		final List<BrokerAccountItem> list = new ArrayList<BrokerAccountItem>();
		for (int i = 0; i < 10; i++) {
			final BrokerAccountItem item = new BrokerAccountItem();
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
	public BrokerAccountItem findById(Long id) {
		// {call dbo.mo_WebGet_SelectAccount_sp ?}
		final BrokerAccountItem item = new BrokerAccountItem();
		item.setId(id);
		item.setName("name" + id);
		item.setComment("comment" + id);
		return item;
	}

	/**
	 * 
	 */
	@Override
	public Long put(BrokerAccountItem value) {
		// "{call dbo.mo_WebSet_putAccount_sp ?, ?, ?, ?}"
		return value.getId();
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, BrokerAccountItem value) {
		// "{call dbo.mo_WebSet_udAccount_sp 'u', ?, ?, ?}"
		return id;
	}

	/**
	 * @param id
	 * @return
	 */
	public void deleteById(Long id) {
		// "{call dbo.mo_WebSet_udAccount_sp 'd', ?}"
	}

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query) {
		// "select id, name from dbo.mo_WebGet_ajaxAccount_v"
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
