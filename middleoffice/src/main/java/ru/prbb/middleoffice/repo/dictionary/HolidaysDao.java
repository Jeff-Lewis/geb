/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.CountryOffsetItem;
import ru.prbb.middleoffice.domain.HolidaysItem;
import ru.prbb.middleoffice.domain.HolidaysWeekItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class HolidaysDao
{

	@Autowired
	private EntityManagerService ems;

	public List<HolidaysItem> showHolidays(ArmUserInfo user) {
		String sql = "select country, holiday_date, holiday_name," +
				" holiday_time_start, holiday_time_stop, sms, portfolio" +
				" from dbo.holidays";
		return ems.getResultList(user, HolidaysItem.class, sql);
	}

	public int addHoliday(ArmUserInfo user, String country, Date date, String times,
			String timee, String name, Boolean sms, Boolean portfolio) {
		String sql = "{call dbo.check_holidays ?, ?, ?, ?, ?, ?, ?, 'i'}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, country, date, times, timee, name, sms, portfolio);
	}

	public int delHoliday(ArmUserInfo user, String country, Date date) {
		String sql = "{call dbo.check_holidays ?, ?, '', '', '', 0, 0, 'd'}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, country, date);
	}

	public List<HolidaysWeekItem> showHolidaysWeek(ArmUserInfo user, String country) {
		String sql = "select country, day_week, start, stop from dbo.quotes_send_sms_time_v_" + country;
		return ems.getSelectList(user, HolidaysWeekItem.class, sql);
//		List<HolidaysWeekItem> res = new ArrayList<>(list.size());
//		for (Object[] arr : list) {
//			HolidaysWeekItem item = new HolidaysWeekItem(arr);
//			res.add(item);
//		}
//		return res;
	}

	public void setHolidaysWeek(ArmUserInfo user, List<HolidaysWeekItem> items) {
		String sql = "{call dbo.quotes_send_sms_time_set ?, ?, ?, ?}";
		for (HolidaysWeekItem item : items) {
			ems.executeUpdate(AccessAction.UPDATE, user, sql, item.getCountry(), item.getDay_week(), item.getStart(), item.getStop());
		}
	}

	public List<CountryOffsetItem> showCountryOffset(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_offset_sp}";
		return ems.getSelectList(user, CountryOffsetItem.class, sql);
	}

	public void setCountryOffset(ArmUserInfo user, List<CountryOffsetItem> items) {
		String sql = "{call dbo.mo_WebSet_TradeTimeOffset_sp ?, ?, ?, ?, ?}";
		for (CountryOffsetItem item : items) {
			ems.executeUpdate(AccessAction.UPDATE, user, sql, item.getId(), item.getCountry(), item.getOffset(), item.getStart(), item.getStop());
		}
	}
}
