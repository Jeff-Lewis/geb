/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.DealsPatternItem;

/**
 * Сохраненные шаблоны
 * 
 * @author RBr
 * 
 */
public interface DealsPatternDao {

	/**
	 * @return
	 */
	List<DealsPatternItem> show();

	/**
	 * @param id
	 * @return
	 */
	DealsPatternItem getById(Long id);

	/**
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

	/**
	 * @param id
	 * @return
	 */
	byte[] getFileById(Long id);

	/**
	 * @param name
	 * @param ct
	 * @param bytes
	 * @return
	 */
	int add(String name, String type, byte[] bytes);

}
