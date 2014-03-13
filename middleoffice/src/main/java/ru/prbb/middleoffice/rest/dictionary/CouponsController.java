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
import ru.prbb.middleoffice.domain.CouponItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.BondsDao;
import ru.prbb.middleoffice.repo.dictionary.BrokerAccountsDao;
import ru.prbb.middleoffice.repo.dictionary.BrokersDao;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.CouponsDao;
import ru.prbb.middleoffice.repo.dictionary.CurrenciesDao;
import ru.prbb.middleoffice.repo.dictionary.FundsDao;

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Coupons")
public class CouponsController
{
	@Autowired
	private CouponsDao dao;
	@Autowired
	private ClientsDao daoClients;
	@Autowired
	private BrokersDao daoBrokers;
	@Autowired
	private BondsDao daoBonds;
	@Autowired
	private BrokerAccountsDao daoAccounts;
	@Autowired
	private FundsDao daoFunds;
	@Autowired
	private CurrenciesDao daoCurrencies;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CouponItem> list(
			@RequestParam Long clientId,
			@RequestParam Long brokerId,
			@RequestParam Long securityId,
			@RequestParam Long operationId,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		return dao.findAll(securityId, clientId, brokerId, operationId,
				Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));
	}

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam Long securityId,
			@RequestParam Long accountId,
			@RequestParam Long fundId,
			@RequestParam Long currencyId,
			@RequestParam String dateRecord,
			@RequestParam String dateReceive,
			@RequestParam Integer quantity,
			@RequestParam Double coupon,
			@RequestParam Double extraCost,
			@RequestParam Long operationId)
	{
		dao.put(securityId, accountId, fundId, currencyId,
				Utils.parseDate(dateRecord), Utils.parseDate(dateReceive),
				quantity, coupon, extraCost, operationId);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData lookupById(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result changeById(
			@PathVariable("id") Long id,
			@RequestParam String field,
			@RequestParam String value)
	{
		if ("ACTUAL".equals(field)) {
			dao.updateById(id, Utils.parseDate(value));
		} else {
			dao.updateAttrById(id, field, value);
		}
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

	@RequestMapping(value = "/Bonds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboBonds(
			@RequestParam(required = false) String query)
	{
		return daoBonds.findCombo(query);
	}

	@RequestMapping(value = "/Operations", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboOperations(
			@RequestParam(required = false) String query)
	{
		return dao.findComboOperations(query);
	}

	@RequestMapping(value = "/Accounts", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboBrokerAccounts(
			@RequestParam(required = false) String query)
	{
		return daoAccounts.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		return daoFunds.findCombo(query);
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCurrency(
			@RequestParam(required = false) String query)
	{
		return daoCurrencies.findCombo(query);
	}

}
