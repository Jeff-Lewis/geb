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
import ru.prbb.analytics.repo.bloomberg.RequestBDPovrDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * BDP с override
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/RequestBDPovr")
public class RequestBDPovrController
		extends BaseController
{

	@Autowired
	private BloombergServicesA bs;
	@Autowired
	private RequestBDPovrDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postExecute(
			@RequestParam String[] security,
			@RequestParam String[] currency,
			@RequestParam String[] params,
			@RequestParam String over,
			@RequestParam String period)
	{
		log.info("POST RequestBDP: security={}", (Object) security);
		log.info("POST RequestBDP: over={}, period={}, params={}", Utils.asArray(over, period, (Object) params));
		log.info("POST RequestBDP: currency={}", (Object) currency);
		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeBdpRequestOverride("BDP с override", security, params, currency, period, over);
		dao.execute(security, currency, over, answer);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Quarter", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postExecuteQuarter(
			@RequestParam String[] security,
			@RequestParam String[] params,
			@RequestParam String over,
			@RequestParam String[] currency)
	{
		log.info("POST RequestBDP/Quarter: security={}", (Object) security);
		log.info("POST RequestBDP/Quarter: over={}, params={}", over, (Object) params);
		log.info("POST RequestBDP/Quarter: currency={}", (Object) currency);
		Set<String> _currency = new HashSet<String>(Arrays.asList(currency));
		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeBdpRequestOverrideQuarter("BDP с override-quarter", security, params,
						_currency.toArray(new String[_currency.size()]), over);
		dao.execute(security, currency, over, answer);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<EquitiesItem> getSecurities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equities,
			@RequestParam(required = false) Integer fundamentals)
	{
		log.info("POST RequestBDPovr/Securities: filter={}, equities={}, fundamentals={}",
				Utils.asArray(filter, equities, fundamentals));
		return daoEquities.findAllEquities(filter, equities, fundamentals);
	}

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboParams(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDPovr: Params='{}'", query);
		return dao.findParams(query);
	}

	@RequestMapping(value = "/Override", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboOverride(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDPovr: Override='{}'", query);
		return dao.comboFilterOverride(query);
	}

	@RequestMapping(value = "/EquitiesFilter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquitiesFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDPovr: EquitiesFilter='{}'", query);
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDPovr: Equities='{}'", query);
		return daoEquities.comboEquities(query);
	}
}
