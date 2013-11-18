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
public interface SecurityTypeDao {

	/**
	 * @param query
	 * @return
	 */
	public List<SimpleItem> findCombo(String query);

}