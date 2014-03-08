package ru.prbb.middleoffice.rest.portfolio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewDealsItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.BrokerAccountsDao;
import ru.prbb.middleoffice.repo.dictionary.TradesystemsDao;
import ru.prbb.middleoffice.repo.portfolio.ViewDealsDao;

/**
 * Список сделок
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewDeals")
public class ViewDealsController
{
	@Autowired
	private ViewDealsDao dao;
	@Autowired
	private TradesystemsDao daoTradesystems;
	@Autowired
	private BrokerAccountsDao daoAccounts;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ViewDealsItem> show(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker)
	{
		return dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker);
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET, produces = "application/json")
	public void export(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker)
	{
		List<ViewDealsItem> list =
				dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker);

		for (ViewDealsItem item : list) {

		}
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result del(
			@RequestParam Long[] deals)
	{
		dao.deleteById(deals);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Set", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result set(
			@RequestParam Long[] deals,
			@RequestParam String field,
			@RequestParam String value)
	{
		dao.updateById(deals, field, value);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/TradeSystems", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboTradeSystems(
			@RequestParam(required = false) String query)
	{
		return daoTradesystems.findCombo(query);
	}

	@RequestMapping(value = "/Accounts", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboAccounts(
			@RequestParam(required = false) String query)
	{
		return daoAccounts.findCombo(query);
	}

	@RequestMapping(value = "/Portfolio", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPortfolio(
			@RequestParam(required = false) String query)
	{
		return daoEquities.findComboInvestmentPortfolio(query);
	}

	@RequestMapping(value = "/Tickers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboTickers(
			@RequestParam(required = false) String query)
	{
		return daoEquities.findComboPortfolio(query);
	}
}
