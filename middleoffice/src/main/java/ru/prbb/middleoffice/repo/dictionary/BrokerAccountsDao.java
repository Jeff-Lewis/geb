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
	 * @param name
	 * @param client
	 * @param broker
	 * @param comment
	 * @return
	 */
	public int put(String name, String client, String broker, String comment);

	/**
	 * 
	 * @param id
	 * @param name
	 * @param comment
	 * @return
	 */
	public int updateById(Long id, String name, String comment);

	/**
	 * @param id
	 */
	public int deleteById(Long id);

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}