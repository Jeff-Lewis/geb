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
import ru.prbb.middleoffice.domain.ReferenceItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.dictionary.BrokersDao;

/**
 * Брокеры
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Brokers")
public class BrokersController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BrokersDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ReferenceItem> getItems()
	{
		log.info("GET Brokers");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Brokers: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam String name,
			@RequestParam String comment)
	{
		log.info("POST Brokers add: name={}, comment={}", name, comment);
		dao.put(name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam String comment)
	{
		log.info("POST Brokers update: id={}, name={}, comment={}", Utils.toArray(id, name, comment));
		dao.updateById(id, name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL Brokers: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}
}
