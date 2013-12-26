package ru.prbb.analytics.rest.model;

import java.util.List;

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

/**
 * Расчёт EPS по компании
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/BuildEPS")
public class BuildEPSController
{
	@Autowired
	private BuildEPSDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(value = "/CalculateEps", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData calculateEPS(
			@RequestParam Long[] ids)
	{
		return new ResultData(dao.calculate(ids));
	}

	@RequestMapping(value = "/CalculateEpsAll", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData calculateEPS()
	{
		return new ResultData(dao.calculate());
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboFilter(query);
	}

	@RequestMapping(value = "/FilterEquities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilterEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.comboEquities(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<EquitiesItem> listEquities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equity)
	{
		return daoEquities.findAllEquities(filter, equity, 1);
	}
}
