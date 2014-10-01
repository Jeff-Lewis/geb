package ru.prbb.middleoffice.rest.portfolio;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Export;
import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewDealsItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.BrokerAccountsDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.repo.dictionary.InitiatorsDao;
import ru.prbb.middleoffice.repo.dictionary.TradesystemsDao;
import ru.prbb.middleoffice.repo.portfolio.ViewDealsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Список сделок
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewDeals")
public class ViewDealsController
		extends BaseController
{

	@Autowired
	private ViewDealsDao dao;
	@Autowired
	private InitiatorsDao daoInitiators;
	@Autowired
	private TradesystemsDao daoTradesystems;
	@Autowired
	private BrokerAccountsDao daoAccounts;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private FundsDao daoFunds;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewDealsItem> postShow(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker,
			@RequestParam Long client,
			@RequestParam Long funds,
			@RequestParam Long initiator,
			@RequestParam Integer batch)
	{
		log.info("POST ViewDeals: dateBegin={}, dateEnd={}, ticker={}, client={}, funds={}, initiator={}, batch={}",
				Utils.toArray(dateBegin, dateEnd, ticker, client, funds, initiator, batch));
		return dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker, client, funds, initiator, batch);
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getExport(HttpServletResponse response,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker,
			@RequestParam Long client,
			@RequestParam Long funds,
			@RequestParam Long initiator,
			@RequestParam Integer batch)
	{
		log.info("POST ViewDeals/Export: dateBegin={}, dateEnd={}, ticker={}", Utils.toArray(dateBegin, dateEnd, ticker));
		log.info("POST ViewDeals/Export: dateBegin={}, dateEnd={}, ticker={}, client={}, funds={}, initiator={}, batch={}",
				Utils.toArray(dateBegin, dateEnd, ticker, client, funds, initiator, batch));
		List<ViewDealsItem> list =
				dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker, client, funds, initiator, batch);

		Export exp = Export.newInstance();
		exp.setCaption("Список сделок");
		exp.addTitle("Список сделок за период c: " + dateBegin + " по: " + dateEnd);
		exp.setColumns(
				"ID",
				"Batch",
				"TradeNum",
				"SecShortName",
				"Operation",
				"Quantity",
				"Price",
				"PriceNKD",
				"Currency",
				"TradeDate",
				"SettleDate",
				"TradeSystem",
				"Broker",
				"Account",
				"Client",
				"Portfolio",
				"Funding",
				"Initiator");
		for (ViewDealsItem item : list) {
			exp.addRow(
					item.getId(),
					item.getBatch(),
					item.getTradeNum(),
					item.getSecShortName(),
					item.getOperation(),
					item.getQuantity(),
					item.getPrice(),
					item.getPriceNKD(),
					item.getCurrency(),
					Utils.parseDate(item.getTradeDate()),
					Utils.parseDate(item.getSettleDate()),
					item.getTradeSystem(),
					item.getBroker(),
					item.getAccount(),
					item.getClient(),
					item.getPortfolio(),
					item.getFunding(),
					item.getInitiator());
		}

		String name = "Deals.ods";
		response.setHeader("Content-disposition", "attachment;filename=" + name);
		response.setContentType(exp.getContentType());
		return exp.build();
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postDel(
			@RequestParam Long[] deals)
	{
		log.info("DEL ViewDeals: ids={}", deals);
		dao.deleteById(deals);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Set", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postSet(
			@RequestParam Long[] deals,
			@RequestParam String field,
			@RequestParam String value)
	{
		log.info("POST ViewDeals/Set: field={}, value={}, deals={}", Utils.toArray(field, value, deals));
		dao.updateById(deals, field, value);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Initiator", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboInitiator(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDeals: Initiator='{}'", query);
		return daoInitiators.findCombo(query);
	}

	@RequestMapping(value = "/TradeSystems", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboTradeSystems(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDeals: TradeSystems='{}'", query);
		return daoTradesystems.findCombo(query);
	}

	@RequestMapping(value = "/Accounts", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboAccounts(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDeals: Accounts='{}'", query);
		return daoAccounts.findCombo(query);
	}

	@RequestMapping(value = "/InvestmentPortfolio", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboInvestmentPortfolio(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDeals: InvestmentPortfolio='{}'", query);
		return daoEquities.findComboInvestmentPortfolio(query);
	}

	@RequestMapping(value = "/Tickers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboTickers(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDeals: Tickers='{}'", query);
		return daoEquities.findComboPortfolio(query);
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterClients(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDeals: Client='{}'", query);
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDeals: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}
}
