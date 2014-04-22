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

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.SwapItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.SwapsDao;

/**
 * Свопы
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Swaps")
public class SwapsController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SwapsDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SwapItem> getItems()
	{
		log.info("GET Swaps");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Swaps: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam String swap,
			@RequestParam Long security)
	{
		log.info("POST Swaps add: swap={}, security={}", swap, security);
		dao.put(swap, security);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam String swap)
	{
		log.info("POST Swaps update: id={}, swap={}", id, swap);
		dao.updateById(id, swap);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL Swaps: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Swaps: Equities='{}'", query);
		return daoEquities.findCombo(query);
	}
}
