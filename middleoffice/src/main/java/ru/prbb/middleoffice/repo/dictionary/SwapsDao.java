/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.ReferenceItem;

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

}