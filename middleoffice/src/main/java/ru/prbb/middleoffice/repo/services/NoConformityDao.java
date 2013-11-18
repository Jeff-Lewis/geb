/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.NoConformityItem;

/**
 * Нет соответствия
 * 
 * @author RBr
 * 
 */
public interface NoConformityDao {

	/**
	 * @return
	 */
	List<NoConformityItem> show();

	/**
	 * @param ids
	 */
	void delete(Long[] ids);

}
