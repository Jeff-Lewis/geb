/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import ru.prbb.middleoffice.domain.CountryOffsetItem;
import ru.prbb.middleoffice.domain.HolidaysItem;
import ru.prbb.middleoffice.domain.HolidaysWeekItem;

/**
 * @author RBr
 */
public interface HolidaysDao {

	/**
	 * @return
	 */
	List<HolidaysItem> showHolidays();

	/**
	 * @param country
	 * @param date
	 * @param times
	 * @param timee
	 * @param name
	 * @param sms
	 * @param portfolio
	 * @return
	 */
	int addHoliday(String country, Date date, String times, String timee,
			String name, Boolean sms, Boolean portfolio);

	/**
	 * @param country
	 * @param parseDate
	 * @return
	 */
	int delHoliday(String country, Date parseDate);

	/**
	 * @param country
	 * @return
	 */
	List<HolidaysWeekItem> showHolidaysWeek(String country);

	/**
	 * @param items
	 */
	void setHolidaysWeek(List<HolidaysWeekItem> items);

	/**
	 * @return
	 */
	List<CountryOffsetItem> showCountryOffset();

	/**
	 * @param items
	 */
	void setCountryOffset(List<CountryOffsetItem> items);
}
