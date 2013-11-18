/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

/**
 * Редактирование фьючерсов
 * 
 * @author RBr
 * 
 */
public interface ViewFuturesDao {

	/**
	 * @param code
	 * @param deal
	 * @param futures
	 */
	void put(Long code, String deal, Long futures);

	/**
	 * @param code
	 * @param deal
	 */
	void del(Long code, String deal);

}
