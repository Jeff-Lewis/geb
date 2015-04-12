/**
 * 
 */
package ru.prbb.middleoffice.repo.loading;

import java.util.List;

import ru.prbb.middleoffice.domain.AtrLoadDataItem;
import ru.prbb.middleoffice.domain.SimpleItem;

/**
 * Загрузка ATR
 * 
 * @author RBr
 * 
 */
public interface LoadATRDao {

	/**
	 * @param dateStart
	 * @param dateEnd
	 * @param securities
	 * @param typeMA
	 * @param periodTA
	 * @param period
	 * @param calendar
	 * @return
	 */
	List<AtrLoadDataItem> execute(List<AtrLoadDataItem> answer,
			String typeMA, Integer periodTA, String period, String calendar);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> getTypeMA(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> getPeriod(String query);

	/**
	 * @param query
	 * @return
	 */
	List<SimpleItem> getCalendar(String query);

}
