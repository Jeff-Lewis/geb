package ru.prbb.middleoffice.rest.portfolio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.AccountDao;
import ru.prbb.middleoffice.repo.portfolio.ViewDealsREPODao;

/**
 * Сделки РЕПО
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewDealsREPO")
public class ViewDealsREPOController
{
	// @Autowired
	private ViewDealsREPODao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Object> show(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long securityId)
	{
		return new ArrayList<Object>();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Map<String, Object> get(
			@PathVariable("id") Long id)
	{
		return new HashMap<String, Object>();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Object set(
			@PathVariable("id") Long id,
			@RequestParam Double price,
			@RequestParam Integer quantity,
			@RequestParam Integer days,
			@RequestParam Double rate)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Object deleteById(
			@PathVariable("id") Long id)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		return new ArrayList<SimpleItem>();
	}
}
