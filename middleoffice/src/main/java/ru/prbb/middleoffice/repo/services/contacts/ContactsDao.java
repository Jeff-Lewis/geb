/**
 * 
 */
package ru.prbb.middleoffice.repo.services.contacts;

import java.util.List;

import ru.prbb.middleoffice.domain.ReferenceItem;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
public interface ContactsDao {

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
