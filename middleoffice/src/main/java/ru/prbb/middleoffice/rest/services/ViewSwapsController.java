package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.services.ViewSwapsDao;

/**
 * Редактирование свопов
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewSwaps")
public class ViewSwapsController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ViewSwapsDao dao;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam Long code,
			@RequestParam String deal)
	{
		dao.put(code, deal);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result del(
			@RequestParam Long code,
			@RequestParam String deal)
	{
		dao.del(code, deal);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<PortfolioItem> showPortfolio()
	{
		return daoEquities.findAllSwaps();
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecurityItem> listSecurities(
			@RequestParam(defaultValue = "Total return swap") String filter,
			@RequestParam(required = false) Long security)
	{
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/Filter", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilter(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findComboFilter(query);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}
}
