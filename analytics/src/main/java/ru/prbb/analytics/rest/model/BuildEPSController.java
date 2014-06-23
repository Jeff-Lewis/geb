package ru.prbb.analytics.rest.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.EquitiesItem;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.EquitiesDao;
import ru.prbb.analytics.repo.model.BuildEPSDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Расчёт EPS по компании
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/BuildEPS")
public class BuildEPSController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BuildEPSDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(value = "/CalculateEps", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData calculateEPS(
			@RequestParam Long[] ids)
	{
		log.info("POST BuildEPS/CalculateEps: ids={}", (Object) ids);
		return new ResultData(dao.calculate(ids));
	}

	@RequestMapping(value = "/CalculateEpsAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData calculateEPS()
	{
		log.info("GET BuildEPS/CalculateEpsAll");
		return new ResultData(dao.calculate());
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BuildEPS: Filter='{}'", query);
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/FilterEquities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO BuildEPS: FilterEquities='{}'", query);
		return daoEquities.comboEquities(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<EquitiesItem> listEquities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equity)
	{
		log.info("POST BuildEPS: filter={}, equity={}", filter, equity);
		return daoEquities.findAllEquities(filter, equity, 1);
	}
}
