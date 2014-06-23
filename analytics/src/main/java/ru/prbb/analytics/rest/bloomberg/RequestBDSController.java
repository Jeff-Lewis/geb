package ru.prbb.analytics.rest.bloomberg;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.prbb.analytics.repo.bloomberg.RequestBDSDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * BDS запрос
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/RequestBDS")
public class RequestBDSController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesA bs;
	@Autowired
	private RequestBDSDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postExecute(
			@RequestParam String[] security,
			@RequestParam String[] params)
	{
		log.info("POST RequestBDS: security={}", (Object) security);
		log.info("POST RequestBDS: params={}", (Object) params);
		Map<String, Object> answer = bs.executeBdsRequest("BDS запрос", security, params);
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
		log.info("POST RequestBDS/Securities: filter={}, equities={}, fundamentals={}",
				Utils.asArray(filter, equities, fundamentals));
		return daoEquities.findAllEquities(filter, equities, fundamentals);
	}

	@RequestMapping(value = "/Params", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboParams(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDS: Params='{}'", query);
		return dao.findParams(query);
	}

	@RequestMapping(value = "/EquitiesFilter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquitiesFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDS: EquitiesFilter='{}'", query);
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RequestBDS: Equities='{}'", query);
		return daoEquities.comboEquities(query);
	}
}
