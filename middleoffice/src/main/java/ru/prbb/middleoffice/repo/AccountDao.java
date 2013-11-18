/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.List;

import ru.prbb.middleoffice.domain.ReferenceItem;

/**
 * @author RBr
 *
 */
public interface AccountDao {

	/**
	 * @return
	 */
	public List<ReferenceItem> findAllOrderedByName();

	/**
	 * @param id
	 * @return
	 */
	public ReferenceItem findById(Long id);

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	public Long updateById(Long id, ReferenceItem value);

	/**
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);

}