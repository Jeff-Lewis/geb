/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import ru.prbb.middleoffice.domain.RiskRewardPrice1Item;

/**
 * Цена1 для RR
 * 
 * @author RBr
 * 
 */
public interface RiskRewardPrice1Dao {

	/**
	 * @return
	 */
	List<RiskRewardPrice1Item> findAll();

	/**
	 * @param id
	 * @return
	 */
	RiskRewardPrice1Item findById(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

}
