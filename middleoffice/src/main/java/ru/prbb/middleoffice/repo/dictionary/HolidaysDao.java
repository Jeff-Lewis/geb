/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import ru.prbb.middleoffice.domain.HolidaysItem;
import ru.prbb.middleoffice.domain.HolidaysWeekItem;

/**
 * @author RBr
 *
 */
public interface HolidaysDao {

	/**
	 * @return
	 */
	List<HolidaysItem> showHolidays();

	/**
	 * @param country
	 * @return
	 */
	List<HolidaysWeekItem> showHolidaysWeek(String country);

}
