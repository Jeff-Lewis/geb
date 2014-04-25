package ru.prbb.middleoffice.rest.portfolio;

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
 */
@Controller
@RequestMapping("/rest/ViewDealsREPO")
public class ViewDealsREPOController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ViewDealsREPODao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewDealsREPOItem> postShow(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam Long security)
	{
		log.info("POST ViewDealsREPO: dateBegin={}, dateEnd={}, security={}", Utils.toArray(dateBegin, dateEnd, security));
		return dao.findAll(Utils.parseDate(dateBegin), Utils.parseDate(dateEnd), security);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET ViewDealsREPO: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam Double rate,
			@RequestParam Integer quantity,
			@RequestParam Double price,
			@RequestParam Integer days)
	{
		log.info("POST ViewDealsREPO: id={}, rate={}, quantity={}, price={}, days={}",
				Utils.toArray(id, rate, quantity, price, days));
		dao.updateById(id, rate, quantity, price, days);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL ViewDealsREPO: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewDealsREPO: Equities='{}'", query);
		return daoEquities.findCombo(query);
	}
}
