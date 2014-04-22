package ru.prbb.middleoffice.rest.operations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.SecurityTypeDao;
import ru.prbb.middleoffice.repo.dictionary.BrokerAccountsDao;
import ru.prbb.middleoffice.repo.dictionary.CurrenciesDao;
import ru.prbb.middleoffice.repo.dictionary.FuturesDao;
import ru.prbb.middleoffice.repo.dictionary.TradesystemsDao;
import ru.prbb.middleoffice.repo.operations.DealsOneNewDao;

/**
 * Загрузка единичной сделки
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/DealsOneNew")
public class DealsOneNewController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private DealsOneNewDao dao;
	@Autowired
	private SecurityTypeDao daoSecurityType;
	@Autowired
	private CurrenciesDao daoCurrencies;
	@Autowired
	private TradesystemsDao daoTradesystems;
	@Autowired
	private BrokerAccountsDao daoBrokerAccounts;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private SecuritiesDao daoSecurities;
	@Autowired
	private FuturesDao daoFutures;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result save(
			@RequestParam String tradenum,
			@RequestParam String batch,
			@RequestParam String tradeticker,
			@RequestParam String tradedate,
			@RequestParam String settleDate,
			@RequestParam String tradeoper,
			@RequestParam Double tradeprice,
			@RequestParam Double quantity,
			@RequestParam String currency,
			@RequestParam String tradeSystem,
			@RequestParam String account,
			@RequestParam String portfolio,
			@RequestParam String tickerSelect,
			@RequestParam String futures,
			@RequestParam Integer kindTicker)
	{
		dao.put(Utils.parseString(batch), tradenum, tradeticker,
				Utils.parseDate(tradedate), Utils.parseDate(settleDate),
				tradeoper, tradeprice, quantity, currency, tradeSystem, account,
				Utils.parseString(portfolio), tickerSelect,
				Utils.parseString(futures), kindTicker);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/SecurityType", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboSecurityType(
			@RequestParam(required = false) String query)
	{
		return daoSecurityType.findCombo(query);
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCurrencies(
			@RequestParam(required = false) String query)
	{
		return daoCurrencies.findCombo(query);
	}

	@RequestMapping(value = "/TradeSystems", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboTradesystems(
			@RequestParam(required = false) String query)
	{
		return daoTradesystems.findCombo(query);
	}

	@RequestMapping(value = "/Accounts", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboAccounts(
			@RequestParam(required = false) String query)
	{
		return daoBrokerAccounts.findCombo(query);
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
	List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/Futures", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFutures(
			@RequestParam(required = false) String query)
	{
		return daoFutures.findCombo(query);
	}
}
