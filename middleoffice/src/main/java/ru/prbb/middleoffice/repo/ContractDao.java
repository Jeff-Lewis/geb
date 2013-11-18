/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 *
 */
public interface ContractDao {

	/**
	 * @return
	 */
	public List<SimpleItem> findAll();

	/**
	 * @param id
	 * @return
	 */
	public SimpleItem findById(Long id);

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, SimpleItem value);

	/**
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);

}