/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.DividendItem;

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
public interface DividendsDao {

	/**
	 * @return
	 */
	public List<DividendItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public DividendItem findById(Long id);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public Long put();

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, DividendItem value);

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateAttrById(Long id, String type, String value);

	/**
	 * @param id
	 */
	public void deleteById(Long id);

}