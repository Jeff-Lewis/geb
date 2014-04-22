package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.CountryTaxItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.SecurityTypeDao;
import ru.prbb.middleoffice.repo.dictionary.BrokersDao;
import ru.prbb.middleoffice.repo.dictionary.CountriesDao;
import ru.prbb.middleoffice.repo.dictionary.CountryTaxesDao;

/**
 * Налоги по странам
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/CountryTaxes")
public class CountryTaxesController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CountryTaxesDao dao;
	@Autowired
	private CountriesDao daoCountries;
	@Autowired
	private BrokersDao daoBrokers;
	@Autowired
	private SecurityTypeDao daoSecurityType;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CountryTaxItem> getItems()
	{
		log.info("GET CountryTaxes");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET CountryTaxes: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam Long securityType,
			@RequestParam Long country,
			@RequestParam Long broker,
			@RequestParam Double value,
			@RequestParam String dateBegin,
			@RequestParam Long countryRecipient)
	{
		log.info("POST CountryTaxes add: securityType={}, country={}, broker={}, value={}, dateBegin={}, countryRecipient={}",
				Utils.toArray(securityType, country, broker, value, dateBegin, countryRecipient));
		dao.put(securityType, country, broker, value, Utils.parseDate(dateBegin), countryRecipient);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam Double value,
			@RequestParam Long countryRecipient,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		log.info("POST CountryTaxes update: id={}, value={}, countryRecipient={}, dateBegin={}, dateEnd={}",
				Utils.toArray(id, value, countryRecipient, dateBegin, dateEnd));
		dao.updateById(id, value, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), countryRecipient);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL CountryTaxes: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/SecurityType", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboSecurityType(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO CountryTaxes: SecurityType='{}'", query);
		return daoSecurityType.findCombo(query);
	}

	@RequestMapping(value = "/Countries", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCountries(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO CountryTaxes: Countries='{}'", query);
		return daoCountries.findCombo(query);
	}

	@RequestMapping(value = "/RecipientCountries", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboRecipientCountries(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO CountryTaxes: RecipientCountries='{}'", query);
		return daoCountries.findCombo(query);
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO CountryTaxes: Brokers='{}'", query);
		return daoBrokers.findCombo(query);
	}
}
