/**
 * 
 */
package ru.prbb.middleoffice.rest.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.CountriesDao;
import ru.prbb.middleoffice.repo.dictionary.HolidaysDao;

/**
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Holidays")
public class HolidaysRestController
{
	@Autowired
	private HolidaysDao dao;
	@Autowired
	private CountriesDao daoCountries;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Map<String, Object> init()
	{
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 8);
		map.put("info", new ArrayList<Object>());
		map.put("infoHR", new ArrayList<Object>());
		map.put("infoHU", new ArrayList<Object>());
		map.put("success", true);
		return map;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam String date,
			@RequestParam String name,
			@RequestParam String times,
			@RequestParam String timee,
			@RequestParam String country,
			@RequestParam Boolean sms,
			@RequestParam Boolean portfolio)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/SetTimeOffset", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result setTimeOffset(
			@RequestParam Integer value)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/SetTime", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result setTime(
			@RequestParam String country,
			@RequestParam String date,
			@RequestParam String start,
			@RequestParam String stop)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Countries", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCountries(
			@RequestParam(required = false) String query)
	{
		return daoCountries.findCombo(query);
	}
}
