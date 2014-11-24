package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.RiskRewardPrice1Item;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewPortfolioTransferItem;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.portfolio.ViewPortfolioDao;
import ru.prbb.middleoffice.repo.services.RiskRewardPrice1Dao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Цена1 для RR
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/RiskRewardPrice1")
public class RiskRewardPrice1Controller
		extends BaseController
{

	@Autowired
	private RiskRewardPrice1Dao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;
	@Autowired
	private ViewPortfolioDao daoPortfolio;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<RiskRewardPrice1Item> getItems()
	{
		log.info("GET RiskRewardPrice1");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET RiskRewardPrice1: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam Long portfolio,
			@RequestParam Double price)
	{
		log.warn("POST RiskRewardPrice1: portfolio={}, price={}", portfolio, price);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam Long clientId,
			@RequestParam Long fundId,
			@RequestParam Integer batch,
			@RequestParam Double price,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		log.warn("POST RiskRewardPrice1: id={}, clientId={}, fundId={}, batch={}, price={}, dateBegin={}, dateEnd={}",
				Utils.toArray(id, clientId, fundId, batch, price, dateBegin, dateEnd));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL RiskRewardPrice1: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RiskRewardPrice1: Clients='{}'", query);
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO RiskRewardPrice1: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}

	@RequestMapping(value = "/PortfolioShowTransfer", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioTransferItem> getPortfolio(
			@RequestParam String date,
			@RequestParam Long client)
	{
		log.info("GET RiskRewardPrice1/PortfolioShowTransfer: date={}, client={}", date, client);
		return daoPortfolio.executeSelect(Utils.parseDate(date), client);
	}
}
