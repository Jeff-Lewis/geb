package ru.prbb.analytics.rest.bloomberg;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import ru.prbb.analytics.repo.bloomberg.RequestBDHDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * BDH запрос
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/RequestBDH")
public class RequestBDHController
		extends BaseController
{

	@Autowired
	private BloombergServicesA bs;
	@Autowired
	private RequestBDHDao dao;
	@Autowired
	private BloombergParamsDao daoParams;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postExecute(HttpServletRequest request,
			@RequestParam String[] security,
			@RequestParam String[] params,
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String period,
			@RequestParam String calendar,
			@RequestParam String[] currency)
	{
		log.info("POST RequestBDH: security={}", (Object) security);
		log.info("POST RequestBDH: dateStart={}, dateEnd={}, period={}, calendar={}",
				Utils.asArray(dateStart, dateEnd, period, calendar));
		log.info("POST RequestBDH: params={}", (Object) params);
		log.info("POST RequestBDH: currency={}", (Object) currency);
		Set<String> _currency = new HashSet<String>(Arrays.asList(currency));
		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeBdhRequest("BDH запрос", dateStart, dateEnd, period, calendar,
						_currency.toArray(new String[_currency.size()]), security, params);
		dao.execute(createUserInfo(request), security, currency, answer);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<EquitiesItem> getSecurities(HttpServletRequest request,
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equities,
			@RequestParam(required = false) Integer fundamentals)
	{
		log.info("POST RequestBDH/Securities: filter={}, equities={}, fundamentals={}", Utils.asArray(filter, equities, fundamentals));
		return daoEquities.findAllEquities(createUserInfo(request), filter, equities, fundamentals);
	}

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboParams(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDH: Params='{}'", query);
		return dao.findParams(createUserInfo(request), query);
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboPeriod(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDH: Period='{}'", query);
		return daoParams.findPeriod(createUserInfo(request), query);
	}

	@RequestMapping(value = "/Calendar", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCalendar(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDH: Calendar='{}'", query);
		return daoParams.findCalendar(createUserInfo(request), query);
	}

	@RequestMapping(value = "/EquitiesFilter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquitiesFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDH: EquitiesFilter='{}'", query);
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDH: Equities='{}'", query);
		return daoEquities.comboEquities(query);
	}
}
