package ru.prbb.analytics.rest.bloomberg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.bloomberg.RequestBDHDao;

/**
 * BDH запрос
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/RequestBDH")
public class RequestBDHController
{
	//@Autowired
	private RequestBDHDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execute(
			@RequestParam String[] security,
			@RequestParam String[] params,
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String period,
			@RequestParam String calendar,
			@RequestParam String currency)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> securities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) String equities,
			@RequestParam(required = false) Integer fundamentals)
	{
		// {call dbo.anca_WebGet_EquityFilter_sp}
		final ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			list.add(map);
			map.put("security_code", "SECURITY_CODE_" + (i + 1));
			map.put("short_name", "SECURITY_NAME_" + (i + 1));
		}
		return list;
	}

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> comboParams(
			@RequestParam(required = false) String query)
	{
		// select code from multy_request_params_v
		final ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			list.add(map);
			map.put("code", "CODE_" + (i + 1));
		}
		return list;
	}

	@RequestMapping(value = "/EquitiesFilter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquitiesFilter(
			@RequestParam(required = false) String query)
	{
		// select name from dbo.anca_WebGet_ajaxEquityFilter_v
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (long i = 1; i < 11; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i);
			item.setName("NAME_" + i);
			list.add(item);
		}
		return list;
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		// select id, name from dbo.anca_WebGet_ajaxEquity_v
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (long i = 1; i < 11; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i);
			item.setName("NAME_" + i);
			list.add(item);
		}
		return list;
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPeriod(
			@RequestParam(required = false) String query)
	{
		// select period_id as id, name from dbo.period_type
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (long i = 1; i < 11; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i);
			item.setName("NAME_" + i);
			list.add(item);
		}
		return list;
	}

	@RequestMapping(value = "/Calendar", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCalendar(
			@RequestParam(required = false) String query)
	{
		// select name from calendar_type
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (long i = 1; i < 11; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i);
			item.setName("NAME_" + i);
			list.add(item);
		}
		return list;
	}
}
