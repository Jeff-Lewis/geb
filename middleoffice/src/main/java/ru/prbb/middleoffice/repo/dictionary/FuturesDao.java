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
	 * @param name
	 * @param coef
	 * @param comment
	 * @return
	 */
	public int put(String name, Double coef, String comment);

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
	 * @return
	 */
	public int deleteById(Long id);

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}