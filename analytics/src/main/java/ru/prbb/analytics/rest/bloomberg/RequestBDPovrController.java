package ru.prbb.analytics.rest.bloomberg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.EquityItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.SimpleItem;
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
		dao.execute(security, params, over, period);
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
		dao.execute(security, params, over, _currency);
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
		// select code  from dbo.blm_datasource_ovr
		final List<SimpleItem> list = new ArrayList<SimpleItem>();
		for (long i = 1; i < 11; i++) {
			final SimpleItem item = new SimpleItem();
			item.setId(i);
			item.setName("NAME_" + i);
			list.add(item);
		}
		return list;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<EquityItem> getEquities(
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
		return daoEquities.comboFilter();
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboEquities(query);
	}
}
