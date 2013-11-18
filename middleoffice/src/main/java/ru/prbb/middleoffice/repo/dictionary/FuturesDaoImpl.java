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

	/**
	 * @return
	 */
	public List<FuturesItem> findAll() {
		// {call dbo.mo_WebGet_SelectFuturesAlias_sp}
		//return em.createQuery("{call dbo.anca_WebGet_SelectBrokers_sp}", FuturesItem.class).getResultList();
		final List<FuturesItem> list = new ArrayList<FuturesItem>();
		for (int i = 0; i < 10; i++) {
			final FuturesItem item = new FuturesItem();
			item.setId(i + 1L);
			item.setName("name" + (i + 1));
			item.setCoefficient(i / 10.0);
			item.setComment("comment" + (i + 1));
			list.add(item);
		}
		return list;
	}

	/**
	 * @param id
	 * @return
	 */
	public FuturesItem findById(Long id) {
		// "{call dbo.mo_WebGet_SelectFuturesAlias_sp ?}"
		throw new IllegalAccessError("Method not implemented.");
	}

	/**
	 * 
	 */
	@Override
	public Long put(FuturesItem value) {
		// "{call dbo.mo_WebSet_putFuturesAlias_sp ?, ?, ?}"
		return value.getId();
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, FuturesItem value) {
		// "{call dbo.mo_WebSet_udFuturesAlias_sp 'u', ?, ?, ?, ?}"
		throw new IllegalAccessError("Method not implemented.");
	}

	/**
	 * @param id
	 */
	public void deleteById(Long id) {
		// "{call dbo.mo_WebSet_udFuturesAlias_sp 'd', ?}"
	}

	@Override
	public List<SimpleItem> findCombo(String query) {
		// "select id, name from dbo.mo_WebGet_ajaxFuturesAlias_v"
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
