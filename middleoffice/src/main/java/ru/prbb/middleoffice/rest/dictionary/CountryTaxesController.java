package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 
 */
@Controller
@RequestMapping("/rest/CountryTaxes")
public class CountryTaxesController
{
	@Autowired
	private CountryTaxesDao dao;
	@Autowired
	private CountriesDao daoCountries;
	@Autowired
	private BrokersDao daoBrokers;
	@Autowired
	private SecurityTypeDao daoSecurityType;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<CountryTaxItem> list()
	{
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		final CountryTaxItem value = dao.findById(id);
		return new ResultData(value);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam Long securityType,
			@RequestParam Long country,
			@RequestParam Long broker,
			@RequestParam Double value,
			@RequestParam String dateBegin)
	{
		final CountryTaxItem item = new CountryTaxItem();
		dao.put(item);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam Double value,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		final CountryTaxItem item = new CountryTaxItem();
		item.setId(id);
		dao.updateById(id, item);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result delete(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/SecurityType", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboSecurityType(
			@RequestParam(required = false) String query)
	{
		return daoSecurityType.findCombo(query);
	}

	@RequestMapping(value = "/Countries", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCountries(
			@RequestParam(required = false) String query)
	{
		return daoCountries.findCombo(query);
	}

	@RequestMapping(value = "/Brokers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboBrokers(
			@RequestParam(required = false) String query)
	{
		return daoBrokers.findCombo(query);
	}
}
