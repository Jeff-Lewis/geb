package ru.prbb.analytics.rest.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.EquityItem;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.SecuritiesDao;
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
	private SecuritiesDao daoSecurities;

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
		// select name from dbo.anca_WebGet_ajaxEquityFilter_v
		return daoSecurities.getFilter(query);
	}

	@RequestMapping(value = "/FilterEquities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilterEquities(
			@RequestParam(required = false) String query)
	{
		// select id, name from dbo.anca_WebGet_ajaxEquity_v
		return daoSecurities.getSecurities(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<EquityItem> listEquities(
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) Long equity)
	{
		// {call dbo.anca_WebGet_EquityFilter_sp}
		// {call dbo.anca_WebGet_EquityFilter_sp ?, ?, ?}
		return daoSecurities.getSecurities(filter, equity, 1);
	}
}
