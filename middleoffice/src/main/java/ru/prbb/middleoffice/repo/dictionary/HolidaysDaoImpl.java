/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.HolidaysItem;
import ru.prbb.middleoffice.domain.HolidaysWeekItem;

/**
 * @author RBr
 * 
 */
@Repository
public class HolidaysDaoImpl implements HolidaysDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<HolidaysItem> showHolidays() {
		String sql = "select country, holiday_date, holiday_name," +
				" holiday_time_start, holiday_time_stop, sms, portfolio" +
				" from dbo.holidays";
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<HolidaysItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			HolidaysItem item = new HolidaysItem();
			item.setCountry(Utils.toString(arr[0]));
			item.setDate(Utils.toDate(arr[1]));
			item.setName(Utils.toString(arr[2]));
			item.setTime_start(Utils.toTime(arr[3]));
			item.setTime_stop(Utils.toTime(arr[4]));
			item.setSms(Utils.toByte(arr[5]));
			item.setPortfolio(Utils.toByte(arr[6]));
			res.add(item);
		}
		return res;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<HolidaysWeekItem> showHolidaysWeek(String country) {
		String sql = "select country, day_week, start, stop from dbo.quotes_send_sms_time_v_" + country;
		Query q = em.createNativeQuery(sql);
		@SuppressWarnings("rawtypes")
		List list = q.getResultList();
		List<HolidaysWeekItem> res = new ArrayList<>(list.size());
		for (Object object : list) {
			Object[] arr = (Object[]) object;
			HolidaysWeekItem item = new HolidaysWeekItem();
			item.setCountry(Utils.toString(arr[0]));
			item.setDay_week(Utils.toInteger(arr[1]));
			item.setStart(Utils.toTime(arr[2]));
			item.setStop(Utils.toTime(arr[3]));
			res.add(item);
		}
		return res;
	}

}
