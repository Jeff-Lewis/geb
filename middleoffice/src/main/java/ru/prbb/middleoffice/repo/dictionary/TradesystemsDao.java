/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.ReferenceItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Торговые системы
 * 
 * @author RBr
 * 
 */
public interface TradesystemsDao {

	/**
	 * @return
	 */
	public List<ReferenceItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public ReferenceItem findById(Long id);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public Long put(ReferenceItem value);

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, ReferenceItem value);

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