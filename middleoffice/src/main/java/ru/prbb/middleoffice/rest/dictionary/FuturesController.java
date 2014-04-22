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
import ru.prbb.middleoffice.domain.FuturesItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.dictionary.FuturesDao;

/**
 * Фьючерсы
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Futures")
public class FuturesController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private FuturesDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<FuturesItem> getItems()
	{
		log.info("GET Futures");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Futures: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam String name,
			@RequestParam Double coef,
			@RequestParam String comment)
	{
		log.info("POST Futures add: name={}, coef={}, comment={}", Utils.toArray(name, coef, comment));
		dao.put(name, coef, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam String comment)
	{
		log.info("POST Futures update: id={}, name={}, comment={}", Utils.toArray(id, name, comment));
		dao.updateById(id, name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result delete(
			@PathVariable("id") Long id)
	{
		log.info("DEL Futures: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}
}
