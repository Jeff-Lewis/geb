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

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewDealsItem> postShow(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker)
	{
		log.info("POST ViewDeals: dateBegin={}, dateEnd={}, ticker={}", Utils.toArray(dateBegin, dateEnd, ticker));
		return dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker);
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getExport(HttpServletResponse response,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long ticker)
	{
		log.info("POST ViewDeals/Export: dateBegin={}, dateEnd={}, ticker={}", Utils.toArray(dateBegin, dateEnd, ticker));
		List<ViewDealsItem> list =
				dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), ticker);

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

	@RequestMapping(value = "/Portfolio", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboPortfolio(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDeals: Portfolio='{}'", query);
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
}
