package ru.prbb.analytics.rest.bloomberg;

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
import ru.prbb.analytics.repo.bloomberg.BloombergParamsDao;
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
	@Autowired
	private RequestBDHDao dao;
	@Autowired
	private BloombergParamsDao daoParams;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result execute(
			@RequestParam String[] security,
			@RequestParam String[] params,
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String period,
			@RequestParam String calendar,
			@RequestParam String[] currency)
	{
		Set<String> _currency = new HashSet<String>(Arrays.asList(currency));
		dao.execute(dateStart, dateEnd, period, calendar, security, params, _currency);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboParams(
			@RequestParam(required = false) String query)
	{
		return dao.findParams();
	}

	@RequestMapping(value = "/Period", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPeriod(
			@RequestParam(required = false) String query)
	{
		return daoParams.findPeriod();
	}

	@RequestMapping(value = "/Calendar", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCalendar(
			@RequestParam(required = false) String query)
	{
		return daoParams.findCalendar();
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
