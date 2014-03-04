/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;


/**
 * @author RBr
 */
public interface DealsTransferDao {

	/**
	 * @param portfolioId
	 * @param quantity
	 * @param price
	 * @param fundId
	 * @param batch
	 * @param comment
	 * @return
	 */
	int execute(Long portfolioId, Integer quantity, Double price, Long fundId, Integer batch, String comment);

}
