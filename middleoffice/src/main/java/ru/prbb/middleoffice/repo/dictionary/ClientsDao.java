/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.ReferenceItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Клиенты
 * 
 * @author RBr
 * 
 */
public interface ClientsDao {

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
	 * @param name
	 * @param comment
	 * @return
	 */
	public int put(String name, String comment);

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