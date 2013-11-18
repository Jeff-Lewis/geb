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
 * Клиенты
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ClientsDaoImpl implements ClientsDao
{
	@Autowired
	private EntityManager em;

	/**
	 * @return
	 */
	public List<ReferenceItem> findAll() {
		// {call dbo.mo_WebGet_SelectClients_sp}
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
		// {call dbo.mo_WebGet_SelectClients_sp ?}
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
		// "{call dbo.mo_WebSet_putClients_sp ?, ?}"
		return value.getId();
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, ReferenceItem value) {
		// "{call dbo.mo_WebSet_udClients_sp 'u', ?, ?, ?}"
		return id;
	}

	/**
	 * @param id
	 */
	@Override
	public void deleteById(Long id) {
		// "{call dbo.mo_WebSet_udClients_sp 'd', ?}"
	}

	/**
	 * @param query
	 */
	@Override
	public List<SimpleItem> findCombo(String query) {
		// select * from dbo.mo_WebGet_ajaxClient_v
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
