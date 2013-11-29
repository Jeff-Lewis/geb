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
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SecurityIncorporationListItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.dictionary.CountriesDao;
import ru.prbb.middleoffice.repo.dictionary.SecurityIncorporationsDao;

/**
 * Регистрация инструментов
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/SecurityIncorporations")
public class SecurityIncorporationsController
{
	@Autowired
	private SecurityIncorporationsDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;
	@Autowired
	private CountriesDao daoCountries;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<SecurityIncorporationListItem> list()
	{
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam Long security,
			@RequestParam Long country,
			@RequestParam String dateBegin)
	{
		dao.put(security, country, Utils.parseDate(dateBegin));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		dao.updateById(id, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));
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

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboSecurities(
			@RequestParam(required = false) String query)
	{
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/Countries", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCountries(
			@RequestParam(required = false) String query)
	{
		return daoCountries.findCombo(query);
	}
}
