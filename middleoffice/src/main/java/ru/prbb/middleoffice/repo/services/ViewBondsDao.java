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
	 * @param deal
	 */
	void put(Long code, String deal);

	/**
	 * @param code
	 * @param deal
	 */
	void del(Long code, String deal);

}
