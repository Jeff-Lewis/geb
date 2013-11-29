/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.SwapItem;

/**
 * Свопы
 * 
 * @author RBr
 * 
 */
public interface SwapsDao {

	/**
	 * @return
	 */
	public List<SwapItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public SwapItem findById(Long id);

	/**
	 * 
	 * @param swap
	 * @param security
	 * @return
	 */
	public int put(String swap, Long security);

	/**
	 * 
	 * @param id
	 * @param swap
	 * @return
	 */
	public int updateById(Long id, String swap);

	/**
	 * @param id
	 * @return
	 */
	public int deleteById(Long id);

}