/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.List;

/**
 * Добавление компаний
 * 
 * @author RBr
 * 
 */
public interface CompanyAddDao {

	/**
	 * @param codes
	 * @return
	 */
	List<String[]> execute(String[] codes);

}
