/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.BrokerAccountItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Брокерские счета
 * 
 * @author RBr
 * 
 */
public interface BrokerAccountsDao {

	/**
	 * @return
	 */
	public List<BrokerAccountItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public BrokerAccountItem findById(Long id);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public Long put(BrokerAccountItem value);

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, BrokerAccountItem value);

	/**
	 * @param id
	 */
	public void deleteById(Long id);

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}