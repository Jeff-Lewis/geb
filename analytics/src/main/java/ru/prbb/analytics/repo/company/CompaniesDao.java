/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.List;

import ru.prbb.analytics.domain.CompaniesItem;

/**
 * Список компаний
 * 
 * @author RBr
 * 
 */
public interface CompaniesDao {

	/**
	 * @return
	 */
	List<CompaniesItem> show();

	/**
	 * @param id
	 * @return
	 */
	CompaniesItem findById(Long id);

}
