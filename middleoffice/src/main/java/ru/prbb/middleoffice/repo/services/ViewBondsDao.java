/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

/**
 * Редактирование облигаций
 * 
 * @author RBr
 * 
 */
public interface ViewBondsDao {

	/**
	 * @param code
	 * @param ticker
	 */
	void put(Long code, String ticker);

	/**
	 * @param code
	 * @param ticker
	 */
	void del(Long code, String ticker);

}
