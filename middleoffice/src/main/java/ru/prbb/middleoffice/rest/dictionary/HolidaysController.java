/**
 * 
 */
package ru.prbb.middleoffice.rest.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.CountryOffsetItem;
import ru.prbb.middleoffice.domain.HolidaysItem;
import ru.prbb.middleoffice.domain.HolidaysWeekItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.CountriesDao;
import ru.prbb.middleoffice.repo.dictionary.HolidaysDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Holidays")
public class HolidaysController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private HolidaysDao dao;
	@Autowired
	private CountriesDao daoCountries;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<HolidaysItem> getItems()
	{
		log.info("GET Holidays");
		return dao.showHolidays();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam String date,
			@RequestParam String name,
			@RequestParam String times,
			@RequestParam String timee,
			@RequestParam String country,
			@RequestParam Boolean sms,
			@RequestParam Boolean portfolio)
	{
		log.info("POST Holidays add: country={}, date={}, times={}, timee={}, name={}, sms={}, portfolio={}",
				Utils.toArray(country, date, times, timee, name, sms, portfolio));
		dao.addHoliday(country, Utils.parseDate(date), times, timee, name, sms, portfolio);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Delete", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postDeleteItem(
			@RequestParam String country,
			@RequestParam String date)
	{
		log.info("POST Holidays delete: country={}, date={}", country, date);
		dao.delHoliday(country, Utils.parseDate(date));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Time/{country}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<HolidaysWeekItem> getTime(
			@PathVariable("country") String country)
	{
		log.info("GET Holidays time: country={}", country);
		return dao.showHolidaysWeek(country);
	}

	@RequestMapping(value = "/Time", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postTime(
			@RequestParam String[] values)
	{
		List<HolidaysWeekItem> items = new ArrayList<>(values.length);
		for (String value : values) {
			log.info("POST Holidays time: value={}", value);
			String[] arr = value.split(";", 4);
			HolidaysWeekItem item = new HolidaysWeekItem();
			item.setCountry(arr[0]);
			item.setDay_week(new Integer(arr[1]));
			item.setStart(arr[2]);
			item.setStop(arr[3]);
			items.add(item);
		}
		dao.setHolidaysWeek(items);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/TimeOffset", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CountryOffsetItem> getTimeOffset()
	{
		log.info("GET Holidays timeoffset");
		return dao.showCountryOffset();
	}

	@RequestMapping(value = "/TimeOffset", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postTimeOffset(
			@RequestParam String[] values)
	{
		List<CountryOffsetItem> items = new ArrayList<>();
		for (String value : values) {
			log.info("POST Holidays timeoffset: value={}", value);
			String[] arr = value.split(";", 5);
			CountryOffsetItem item = new CountryOffsetItem();
			item.setId(new Long(arr[0]));
			item.setCountry(arr[1]);
			item.setOffset(new Integer(arr[2]));
			item.setStart(arr[3]);
			item.setStop(arr[4]);
			items.add(item);
		}
		dao.setCountryOffset(items);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Countries", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCountries(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Holidays: Countries='{}'", query);
		return daoCountries.findCombo(query);
	}
}
