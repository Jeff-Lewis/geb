/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.SecurityRiscsItem;

/**
 * Заданные параметры риска
 * 
 * @author RBr
 * 
 */
public interface SecurityRiscsDao {

	/**
	 * 
	 * @param security
	 * @param fund
	 * @param batch
	 * @param client
	 * @param date
	 * @return
	 */
	List<SecurityRiscsItem> findAll(Long security, Long fund, Integer batch, Long client, Date date);

	/**
	 * @param id
	 * @return
	 */
	SecurityRiscsItem findById(Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	int deleteById(Long id);

	/**
	 * 
	 * @param id
	 * @param client_id
	 * @param fund_id
	 * @param batch
	 * @param risk_ath
	 * @param risk_avg
	 * @param stop_loss
	 * @param date_begin
	 * @param date_end
	 * @param comment
	 * @return
	 */
	int updateById(Long id, Long client_id, Long fund_id, Integer batch,
			BigDecimal risk_ath, BigDecimal risk_avg, BigDecimal stop_loss,
			Date date_begin, Date date_end, String comment);

}
