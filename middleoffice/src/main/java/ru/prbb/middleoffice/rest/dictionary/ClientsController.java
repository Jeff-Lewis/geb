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
import ru.prbb.middleoffice.domain.ClientsItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.ClientsDao;
import ru.prbb.middleoffice.repo.dictionary.CountriesDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Клиенты
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Clients")
public class ClientsController
		extends BaseController
{

	@Autowired
	private ClientsDao dao;
	@Autowired
	private CountriesDao daoCountries;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ClientsItem> getItems(HttpServletRequest request)
	{
		log.info("GET Clients");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Clients: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request),id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(HttpServletRequest request,
			@RequestParam String name,
			@RequestParam String comment,
			@RequestParam Long country,
			@RequestParam String dateBegin,
			@RequestParam(required = false) String dateEnd)
	{
		log.info("POST Clients add: name={}, comment={}, country={}, dateBegin={}, dateEnd={}",
				Utils.toArray(name, comment, country, dateBegin, dateEnd));
		dao.put(createUserInfo(request),name, comment, country, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam String comment,
			@RequestParam Long country,
			@RequestParam String dateBegin,
			@RequestParam(required = false) String dateEnd)
	{
		log.info("POST Clients update: id={}, name={}, comment={}, country={}, dateBegin={}, dateEnd={}",
				Utils.toArray(id, name, comment, country, dateBegin, dateEnd));
		dao.updateById(createUserInfo(request),id, name, comment, country, Utils.parseDate(dateBegin), Utils.parseDate(dateEnd));
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("DEL Clients: id={}", id);
		dao.deleteById(createUserInfo(request),id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Countries", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCountries(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Clients: Countries='{}'", query);
		return daoCountries.findCombo(query);
	}

}
