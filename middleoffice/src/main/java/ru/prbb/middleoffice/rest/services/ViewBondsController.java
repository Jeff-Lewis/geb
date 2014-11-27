package ru.prbb.middleoffice.rest.services;

import java.util.List;

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
import ru.prbb.middleoffice.repo.services.ViewBondsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Редактирование облигаций
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewBonds")
public class ViewBondsController
		extends BaseController
{

	@Autowired
	private ViewBondsDao dao;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAdd(
			@RequestParam Long code,
			@RequestParam String ticker)
	{
		log.info("POST ViewBonds/Add: code={}, ticker={}", code, ticker);
		dao.put(code, ticker);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postDel(
			@RequestParam Long code,
			@RequestParam String ticker)
	{
		log.info("POST ViewBonds/Del: code={}, ticker={}", code, ticker);
		dao.del(code, ticker);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<PortfolioItem> getPortfolio()
	{
		log.info("GET ViewBonds/Portfolio");
		return daoEquities.findAllBonds();
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> listSecurities(
			@RequestParam(defaultValue = "Bond") String filter,
			@RequestParam(required = false) Long security)
	{
		log.info("POST ViewBonds/Securities: filter={}, security={}", filter, security);
		return daoSecurities.findAll("Bond", security);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewBonds: FilterSecurities='{}'", query);
		return daoSecurities.findComboBonds(query);
	}
}
