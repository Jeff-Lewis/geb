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

import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.BloombergServicesA;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.bloomberg.RequestBDPovrDao;

/**
 * BDP с override
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/RequestBDPovr")
public class RequestBDPovrController
{
	@Autowired
	private BloombergServicesA bs;
	@Autowired
	private RequestBDPovrDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execute(
			@RequestParam String[] security,
			@RequestParam String[] params,
			@RequestParam String over,
			@RequestParam String period)
	{
		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeBdpRequestOverride("BDP с override", security, params, period, over);
		dao.execute(security, over, answer);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Quarter", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result executeQuarter(
			@RequestParam String[] security,
			@RequestParam String[] params,
			@RequestParam String over,
			@RequestParam String[] currency)
	{
		Set<String> _currency = new HashSet<String>(Arrays.asList(currency));
		Map<String, Map<String, Map<String, String>>> answer =
				bs.executeBdpRequestOverrideQuarter("BDP с override-quarter", security, params,
						_currency.toArray(new String[_currency.size()]), over);
		dao.execute(security, over, answer);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboParams(
			@RequestParam(required = false) String query)
	{
		return dao.findParams();
	}

	@RequestMapping(value = "/Override", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboOverride(
			@RequestParam(required = false) String query)
	{
		return dao.comboFilterOverride(query);
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<EquitiesItem> getEquities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equities,
			@RequestParam(required = false) Integer fundamentals)
	{
		return daoEquities.findAllEquities(filter, equities, fundamentals);
	}

	@RequestMapping(value = "/EquitiesFilter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquitiesFilter(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboEquities(query);
	}
}
