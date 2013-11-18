/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

/**
 * Редактирование свопов
 * 
 * @author RBr
 * 
 */
public interface ViewSwapsDao {

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
