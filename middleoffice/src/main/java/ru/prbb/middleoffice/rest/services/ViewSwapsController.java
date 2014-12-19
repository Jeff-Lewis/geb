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
import ru.prbb.middleoffice.repo.services.ViewSwapsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Редактирование свопов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewSwaps")
public class ViewSwapsController
		extends BaseController
{

	@Autowired
	private ViewSwapsDao dao;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAdd(
			@RequestParam Long code,
			@RequestParam String deal)
	{
		log.info("POST ViewSwaps/Add: code={}, deal={}", code, deal);
		dao.put(code, deal);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postDel(
			@RequestParam Long code,
			@RequestParam String deal)
	{
		log.info("POST ViewSwaps/Del: code={}, deal={}", code, deal);
		dao.del(code, deal);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<PortfolioItem> getPortfolio()
	{
		log.info("GET ViewSwaps/Portfolio");
		return daoEquities.findAllSwaps();
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> listSecurities(
			@RequestParam(defaultValue = "Total return swap") String filter,
			@RequestParam(required = false) Long security)
	{
		filter = "Total return swap";
		log.info("POST ViewSwaps/Securities: filter={}, security={}", filter, security);
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewSwaps: FilterSecurities='{}'", query);
		return daoSecurities.findComboSwaps(query);
	}
}
