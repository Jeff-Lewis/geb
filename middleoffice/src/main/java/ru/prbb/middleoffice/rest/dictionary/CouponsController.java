package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Export;
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
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Купоны (погашение)
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Coupons")
public class CouponsController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

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
	@ResponseBody
	public List<CouponItem> list(
			@RequestParam Long clientId,
			@RequestParam Long brokerId,
			@RequestParam Long securityId,
			@RequestParam Long operationId,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		log.info("POST Coupons: clientId={}, brokerId={}, securityId={}, operationId={}, dateBegin={}, dateEnd={}",
				Utils.toArray(clientId, brokerId, securityId, operationId, dateBegin, dateEnd));
		return dao.findAll(securityId, clientId, brokerId, operationId,
				Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));
	}

	@RequestMapping(value = "/ExportXls", method = RequestMethod.GET)
	@ResponseBody
	public byte[] export(HttpServletResponse response,
			@RequestParam Long clientId,
			@RequestParam Long brokerId,
			@RequestParam Long securityId,
			@RequestParam Long operationId,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		log.info("POST Coupons/ExportXls: clientId={}, brokerId={}, securityId={}, operationId={}, dateBegin={}, dateEnd={}",
				Utils.toArray(clientId, brokerId, securityId, operationId, dateBegin, dateEnd));

		List<CouponItem> list = dao.findAll(securityId, clientId, brokerId, operationId,
				Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));

		Export exp = Export.newInstance();
		exp.setCaption("Купоны (погашение)");
		exp.addTitle("Купоны (погашение) с " + dateBegin + " по " + dateEnd);
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
				"COUPON_PER_SHARE",
				"RECEIVE_DATE",
				"REAL_COUPON_PER_SHARE",
				"STATUS",
				"ESTIMATE",
				"REAL_COUPONS",
				"EXTRA_COSTS",
				"TAX_VALUE",
				"COUNTRY",
				"OPER");
		for (CouponItem item : list) {
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
					item.getCoupon_per_share(),
					item.getReceive_date(),
					item.getReal_coupon_per_share(),
					item.getStatus(),
					item.getEstimate(),
					item.getReal_coupons(),
					item.getExtra_costs(),
					item.getTax_value(),
					item.getCountry(),
					item.getOper());
		}

		String name = "Coupons.ods";
		response.setHeader("Content-disposition", "attachment;filename=" + name);
		response.setContentType(exp.getContentType());
		return exp.build();
	}

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result add(
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
		log.info("POST Coupons/Add: securityId={}, accountId={}, fundId={}, currencyId={}, dateRecord={}, dateReceive={},"
				+ " quantity={}, coupon={}, extraCost={}, operationId={}",
				Utils.toArray(securityId, accountId, fundId, currencyId, dateRecord, dateReceive,
						quantity, coupon, extraCost, operationId));
		dao.put(securityId, accountId, fundId, currencyId,
				Utils.parseDate(dateRecord), Utils.parseDate(dateReceive),
				quantity, coupon, extraCost, operationId);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Coupons: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result changeById(
			@PathVariable("id") Long id,
			@RequestParam String field,
			@RequestParam String value)
	{
		log.info("POST Coupons: id={}, field={}, value={}", Utils.toArray(id, field, value));
		if ("ACTUAL".equals(field)) {
			dao.updateById(id, Utils.parseDate(value));
		} else {
			dao.updateAttrById(id, field, value);
		}
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL Coupons: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Clients", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboClients(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Coupons: Clients='{}'", query);
		return daoClients.findCombo(query);
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Coupons: Brokers='{}'", query);
		return daoBrokers.findCombo(query);
	}

	@RequestMapping(value = "/Bonds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboBonds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Coupons: Bonds='{}'", query);
		return daoBonds.findCombo(query);
	}

	@RequestMapping(value = "/Operations", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboOperations(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Coupons: Operations='{}'", query);
		return dao.findComboOperations(query);
	}

	@RequestMapping(value = "/Accounts", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboBrokerAccounts(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Coupons: Accounts='{}'", query);
		return daoAccounts.findCombo(query);
	}

	@RequestMapping(value = "/Funds", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFunds(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Coupons: Funds='{}'", query);
		return daoFunds.findCombo(query);
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCurrency(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Coupons: Currencies='{}'", query);
		return daoCurrencies.findCombo(query);
	}

}
