package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Export;
import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.DividendItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.BrokerAccountsDao;
import ru.prbb.middleoffice.repo.dictionary.BrokersDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.CurrenciesDao;
import ru.prbb.middleoffice.repo.dictionary.DividendsDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Дивиденды
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Dividends")
public class DividendsController
		extends BaseController
{

	@Autowired
	private DividendsDao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private BrokersDao daoBrokers;
	@Autowired
	private BrokerAccountsDao daoAccounts;
	@Autowired
	private FundsDao daoFunds;
	@Autowired
	private CurrenciesDao daoCurrencies;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<DividendItem> postItems(
			@RequestParam Long clientId,
			@RequestParam Long brokerId,
			@RequestParam Long securityId,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		log.info("POST Dividends: clientId={}, brokerId={}, securityId={}, dateBegin={}, dateEnd={}",
				Utils.toArray(clientId, brokerId, securityId, dateBegin, dateEnd));
		return dao.findAll(securityId, clientId, brokerId, null,
				Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));
	}

	@RequestMapping(value = "/ExportXls", method = RequestMethod.GET)
	@ResponseBody
	public byte[] postExport(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam Long brokerId,
			@RequestParam Long securityId,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		log.info("POST Dividends/ExportXls: clientId={}, brokerId={}, securityId={}, dateBegin={}, dateEnd={}",
				Utils.toArray(clientId, brokerId, securityId, dateBegin, dateEnd));
		List<DividendItem> list = dao.findAll(securityId, clientId, brokerId, null,
				Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));

		Export exp = Export.newInstance();
		exp.setCaption("Дивиденды");
		exp.addTitle("Дивиденды с " + dateBegin + " по " + dateEnd);
		exp.setColumns(
				"SECURITY_CODE",
				"SHORT_NAME",
				"CLIENT",
				"FUND",
				"BROKER",
				"ACCOUNT",
				"CURRENCY",
				"RECORD_DATE",
				"QUANTITY",
				"DIVIDEND_PER_SHARE",
				"RECEIVE_DATE",
				"REAL_DIVIDEND_PER_SHARE",
				"STATUS",
				"ESTIMATE",
				"REAL_DIVIDENDS",
				"EXTRA_COSTS",
				"TAX_VALUE",
				"COUNTRY");
		for (DividendItem item : list) {
			exp.addRow(
					item.getSecurity_code(),
					item.getShort_name(),
					item.getClient(),
					item.getFund(),
					item.getBroker(),
					item.getAccount(),
					item.getCurrency(),
					item.getRecord_date(),
					item.getQuantity(),
					item.getDividend_per_share(),
					item.getReceive_date(),
					item.getReal_dividend_per_share(),
					item.getStatus(),
					item.getEstimate(),
					item.getReal_dividends(),
					item.getExtra_costs(),
					item.getTax_value(),
					item.getCountry());
		}

		String name = "Dividends.ods";
		response.setHeader("Content-disposition", "attachment;filename=" + name);
		response.setContentType(exp.getContentType());
		return exp.build();
	}

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam Long securityId,
			@RequestParam Long accountId,
			@RequestParam Long fundId,
			@RequestParam Long currencyId,
			@RequestParam String dateRecord,
			@RequestParam String dateReceive,
			@RequestParam Integer quantity,
			@RequestParam Double dividend,
			@RequestParam Double extraCost)
	{
		log.info("POST Dividends/Add: securityId={}, accountId={}, fundId={}, currencyId={},"
				+ " dateRecord={}, dateReceive={}, quantity={}, dividend={}, extraCost={}",
				Utils.toArray(securityId, accountId, fundId, currencyId,
						dateRecord, dateReceive, quantity, dividend, extraCost));
		dao.put(securityId, accountId, fundId, currencyId,
				Utils.parseDate(dateRecord), Utils.parseDate(dateReceive),
				quantity, dividend, extraCost);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DividendItem getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Dividends: id={}", id);
		return dao.findById(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result changeById(
			@PathVariable("id") Long id,
			@RequestParam String type,
			@RequestParam String value)
	{
		log.info("POST Dividends: id={}, type={}, value={}", Utils.toArray(id, type, value));
		dao.updateAttrById(id, type, value);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL Dividends: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Dividends: Clients='{}'", query);
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Dividends: Brokers='{}'", query);
		return daoBrokers.findCombo(query);
	}

	@RequestMapping(value = "/Accounts", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboBrokerAccounts(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Dividends: Accounts='{}'", query);
		return daoAccounts.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Dividends: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCurrency(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Dividends: Currencies='{}'", query);
		return daoCurrencies.findCombo(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Dividends: Equities='{}'", query);
		return daoEquities.findCombo(query);
	}

}
