/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.CountryOffsetItem;
import ru.prbb.middleoffice.domain.HolidaysItem;
import ru.prbb.middleoffice.domain.HolidaysWeekItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * @author RBr
 * 
 */
@Repository
public class HolidaysDaoImpl extends BaseDaoImpl implements HolidaysDao
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

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int addHoliday(String country, Date date, String times, String timee,
			String name, Boolean sms, Boolean portfolio) {
		String sql = "{call dbo.check_holidays ?, ?, ?, ?, ?, ?, ?, 'i'}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, country)
				.setParameter(2, date)
				.setParameter(3, times)
				.setParameter(4, timee)
				.setParameter(5, name)
				.setParameter(6, sms)
				.setParameter(7, portfolio);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int delHoliday(String country, Date date) {
		String sql = "{call dbo.check_holidays ?, ?, '', '', '', 0, 0, 'd'}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, country)
				.setParameter(2, date);
		storeSql(sql, q);
		return q.executeUpdate();
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

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void setHolidaysWeek(List<HolidaysWeekItem> items) {
		String sql = "{call dbo.quotes_send_sms_time_set ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (HolidaysWeekItem item : items) {
			q.setParameter(1, item.getCountry());
			q.setParameter(2, item.getDay_week());
			q.setParameter(3, item.getStart());
			q.setParameter(4, item.getStop());
			storeSql(sql, q);
			q.executeUpdate();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryOffsetItem> showCountryOffset() {
		String sql = "{call dbo.mo_WebGet_offset_sp}";
		Query q = em.createNativeQuery(sql, CountryOffsetItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void setCountryOffset(List<CountryOffsetItem> items) {
		String sql = "{call dbo.mo_WebSet_TradeTimeOffset_sp ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql);
		for (CountryOffsetItem item : items) {
			q.setParameter(1, item.getId());
			q.setParameter(2, item.getCountry());
			q.setParameter(3, item.getOffset());
			q.setParameter(4, item.getStart());
			q.setParameter(5, item.getStop());
			storeSql(sql, q);
			q.executeUpdate();
		}
	}
}
