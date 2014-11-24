/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.ViewPortfolioItem;
import ru.prbb.middleoffice.domain.ViewPortfolioTransferItem;

/**
 * Текущий портфель
 * 
 * @author RBr
 */
public interface ViewPortfolioDao {

	/**
	 * @param date
	 * @param security
	 * @return
	 */
	List<ViewPortfolioItem> executeSelect(Date date, Long security, Long client);

	/**
	 * @param date
	 * @return
	 */
	List<ViewPortfolioTransferItem> executeSelect(Date date, Long client);

	/**
	 * @param date
	 * @param security
	 * @param client
	 * @return
	 */
	int executeCalc(Date date, Long security, Long client);

	/**
	 * @param date
	 * @param security
	 * @return
	 */
	int executeDelete(Date date, Long security);

}
