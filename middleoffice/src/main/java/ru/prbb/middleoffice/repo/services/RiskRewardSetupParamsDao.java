/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.RiskRewardSetupParamsItem;

/**
 * Задание параметров отчета RR
 * 
 * @author RBr
 * 
 */
public interface RiskRewardSetupParamsDao {

	/**
	 * @param security
	 * @param date
	 * @return
	 */
	List<RiskRewardSetupParamsItem> findAll(Long security, Date date);

	/**
	 * @param id
	 * @return
	 */
	RiskRewardSetupParamsItem findById(Long id);

}
