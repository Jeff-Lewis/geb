/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * @author RBr
 *
 */
public interface BondsDao {

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> findCombo(String query);

}
