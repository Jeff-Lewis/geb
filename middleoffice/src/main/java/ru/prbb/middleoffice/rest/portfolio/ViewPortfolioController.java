package ru.prbb.middleoffice.rest.portfolio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.portfolio.ViewPortfolioDao;

/**
 * Текущий портфель
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewPortfolio")
public class ViewPortfolioController
{
	// @Autowired
	private ViewPortfolioDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Object> show(
			@RequestParam String date,
			@RequestParam Long ticker)
	{
		return new ArrayList<Object>();
	}

	@RequestMapping(value = "/Calculate", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result calculate(
			@RequestParam String date,
			@RequestParam Long ticker)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result export(
			@RequestParam String date,
			@RequestParam Long ticker)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Tickers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}
}
