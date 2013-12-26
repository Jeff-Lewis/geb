/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.SecuritiesRestsItem;

/**
 * Верификация остатков
 * 
 * @author RBr
 * 
 */
public interface SecuritiesRestsDao {

	/**
	 * @param security
	 * @param client
	 * @param fund
	 * @param batch
	 * @param date
	 * @return
	 */
	List<SecuritiesRestsItem> execute(Long security, Long client, Long fund, Integer batch, Date date);

	/**
	 * @param id
	 * @param checkFlag
	 * @return
	 */
	int updateById(Long id, Byte checkFlag);

}
