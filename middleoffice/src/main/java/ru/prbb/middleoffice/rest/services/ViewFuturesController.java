package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewFuturesItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.dictionary.FuturesDao;
import ru.prbb.middleoffice.repo.services.ViewFuturesDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Редактирование фьючерсов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewFutures")
public class ViewFuturesController
		extends BaseController
{

	@Autowired
	private ViewFuturesDao dao;
	@Autowired
	private FuturesDao daoFutures;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAdd(
			@RequestParam Long code,
			@RequestParam String deal,
			@RequestParam Long futures)
	{
		log.info("POST ViewFutures/Add: code={}, deal={}, futures={}", Utils.toArray(code, deal, futures));
		dao.put(code, deal, futures);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postDel(
			@RequestParam Long code,
			@RequestParam String deal)
	{
		log.info("POST ViewFutures/Del: code={}, deal={}", code, deal);
		dao.del(code, deal);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Futures", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFutures(
			@RequestParam String query)
	{
		log.info("COMBO ViewFutures: Futures='{}'", query);
		return daoFutures.findCombo(query);
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewFuturesItem> getPortfolio()
	{
		log.info("GET ViewFutures/Portfolio");
		return daoEquities.findAllFutures();
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> getSecurities(
			@RequestParam(defaultValue = "Future") String filter,
			@RequestParam(required = false) Long security)
	{
		log.info("POST ViewFutures/Securities: filter={}, security={}", filter, security);
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewFutures: FilterSecurities='{}'", query);
		return daoSecurities.findComboFutures(query);
	}
}
