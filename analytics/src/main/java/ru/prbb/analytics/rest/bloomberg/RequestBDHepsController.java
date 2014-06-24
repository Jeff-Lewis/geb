package ru.prbb.analytics.rest.bloomberg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.BloombergServicesA;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.bloomberg.BloombergParamsDao;
import ru.prbb.analytics.repo.bloomberg.RequestBDHepsDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * BDH запрос с EPS
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/RequestBDHeps")
public class RequestBDHepsController
		extends BaseController
{

	@Autowired
	private BloombergServicesA bs;
	@Autowired
	private RequestBDHepsDao dao;
	@Autowired
	private BloombergParamsDao daoParams;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postExecute(
			@RequestParam String[] security,
			@RequestParam String[] params,
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String period,
			@RequestParam String calendar,
			@RequestParam String[] currency)
	{
		log.info("POST RequestBDHeps: security={}", (Object) security);
		log.info("POST RequestBDHeps: dateStart={}, dateEnd={}, period={}, calendar={}",
				Utils.asArray(dateStart, dateEnd, period, calendar));
		log.info("POST RequestBDHeps: params={}", (Object) params);
		log.info("POST RequestBDHeps: currency={}", (Object) currency);
		Set<String> _currency = new HashSet<String>(Arrays.asList(currency));
		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeBdhEpsRequest("BDH запрос с EPS", dateStart, dateEnd, period, calendar,
						_currency.toArray(new String[_currency.size()]), security, params);
		dao.execute(security, answer);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<EquitiesItem> getSecurities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equities,
			@RequestParam(required = false) Integer fundamentals)
	{
		log.info("POST RequestBDHeps/Securities: filter={}, equities={}, fundamentals={}",
				Utils.asArray(filter, equities, fundamentals));
		return daoEquities.findAllEquities(filter, equities, fundamentals);
	}

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboParams(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDHeps: Params='{}'", query);
		return dao.findParams(query);
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboPeriod(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDHeps: Period='{}'", query);
		return daoParams.findPeriod(query);
	}

	@RequestMapping(value = "/Calendar", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCalendar(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDHeps: Calendar='{}'", query);
		return daoParams.findCalendar(query);
	}

	@RequestMapping(value = "/EquitiesFilter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquitiesFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDHeps: EquitiesFilter='{}'", query);
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDHeps: Equities='{}'", query);
		return daoEquities.comboEquities(query);
	}
}
