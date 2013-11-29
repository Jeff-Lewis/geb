package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.DividendItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.CurrenciesDao;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.BrokerAccountsDao;
import ru.prbb.middleoffice.repo.dictionary.BrokersDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.DividendsDao;

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Dividends")
public class DividendsController
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
	private CurrenciesDao daoCurrencies;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<DividendItem> listAllMembers(
			@RequestParam Long clientId,
			@RequestParam Long brokerId,
			@RequestParam Long securityId,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		return dao.findAll(securityId, clientId, brokerId, null,
				Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));
	}

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam Long securityId,
			@RequestParam Long accountId,
			@RequestParam Long currencyId,
			@RequestParam String dateRecord,
			@RequestParam String dateReceive,
			@RequestParam Integer quantity,
			@RequestParam Double dividend,
			@RequestParam Double extraCost)
	{
		dao.put(securityId, accountId, currencyId,
				Utils.parseDate(dateRecord), Utils.parseDate(dateReceive),
				quantity, dividend, extraCost);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	DividendItem lookupById(
			@PathVariable("id") Long id)
	{
		return dao.findById(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result deleteById(
			@PathVariable("id") Long id,
			@RequestParam String type,
			@RequestParam String value)
	{
		dao.updateAttrById(id, type, value);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result deleteById(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		return daoBrokers.findCombo(query);
	}

	@RequestMapping(value = "/Accounts", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboBrokerAccounts(
			@RequestParam(required = false) String query)
	{
		return daoAccounts.findCombo(query);
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCurrency(
			@RequestParam(required = false) String query)
	{
		return daoCurrencies.findCombo(query);
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.findCombo(query);
	}

}
