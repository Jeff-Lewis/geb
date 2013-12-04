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
	int deleteById(Long id);

}
