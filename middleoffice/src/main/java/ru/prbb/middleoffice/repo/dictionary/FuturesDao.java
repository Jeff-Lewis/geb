/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.FuturesItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Фьючерсы
 * 
 * @author RBr
 * 
 */
public interface FuturesDao {

	/**
	 * @return
	 */
	public List<FuturesItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public FuturesItem findById(Long id);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public Long put(FuturesItem value);

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, FuturesItem value);

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