package ru.prbb.middleoffice.rest.portfolio;

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
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewDealsREPOItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
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
	@Autowired
	private ViewDealsREPODao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ViewDealsREPOItem> show(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long security)
	{
		return dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), security);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result set(
			@PathVariable("id") Long id,
			@RequestParam Double rate,
			@RequestParam Integer quantity,
			@RequestParam Double price,
			@RequestParam Integer days)
	{
		dao.updateById(id, rate, quantity, price, days);
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

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		return daoEquities.findCombo(query);
	}
}
