/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

/**
 * Редактирование акций
 * 
 * @author RBr
 * 
 */
public interface ViewShareDao {

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
