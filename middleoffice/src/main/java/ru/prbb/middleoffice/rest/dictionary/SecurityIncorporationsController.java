package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Регистрация инструментов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/SecurityIncorporations")
public class SecurityIncorporationsController
		extends BaseController
{

	@Autowired
	private SecurityIncorporationsDao dao;
	@Autowired
	private SecuritiesDao daoSecurities;
	@Autowired
	private CountriesDao daoCountries;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SecurityIncorporationListItem> getItems(HttpServletRequest request)
	{
		log.info("GET SecurityIncorporations");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET SecurityIncorporations: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request),id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(HttpServletRequest request,
			@RequestParam Long security,
			@RequestParam Long country,
			@RequestParam String dateBegin)
	{
		log.info("POST SecurityIncorporations: security={}, country={}, dateBegin={}", Utils.toArray(security, country, dateBegin));
		dao.put(createUserInfo(request),security, country, Utils.parseDate(dateBegin));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String dateBegin,
			@RequestParam String dateEnd)
	{
		log.info("POST SecurityIncorporations: id={}, dateBegin={}, dateEnd={}", Utils.toArray(id, dateBegin, dateEnd));
		dao.updateById(createUserInfo(request),id, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("DEL SecurityIncorporations: id={}", id);
		dao.deleteById(createUserInfo(request),id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboSecurities(HttpServletRequest request,
			@RequestParam(required = false) String query)
	{
		log.info("COMBO SecurityIncorporations: Securities='{}'", query);
		return daoSecurities.findCombo(query);
	}

	@RequestMapping(value = "/Countries", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCountries(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO SecurityIncorporations: Countries='{}'", query);
		return daoCountries.findCombo(query);
	}
}
