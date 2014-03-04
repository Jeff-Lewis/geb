/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;


/**
 * @author RBr
 */
public interface SetSecurityRiscsDao {

	/**
	 * @param id
	 * @param riskATH
	 * @param riskAVG
	 * @param stopLoss
	 * @param comment
	 * @return
	 */
	int execute(Long id, Double riskATH, Double riskAVG, Double stopLoss, String comment);

}
