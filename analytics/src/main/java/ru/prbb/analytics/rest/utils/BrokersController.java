package ru.prbb.analytics.rest.utils;

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
import ru.prbb.analytics.domain.BrokerItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.utils.BrokersDao;

/**
 * Справочник брокеров
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
	public List<BrokerItem> getItems()
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
	public Result postItemAdd(
			@RequestParam String fullName,
			@RequestParam Integer rating,
			@RequestParam String bloombergCode,
			@RequestParam Integer coverRussian,
			@RequestParam String shortName)
	{
		log.info("POST Brokers: fullName={}, rating={}, bloombergCode={}, coverRussian={}, shortName={}",
				Utils.asArray(fullName, rating, bloombergCode, coverRussian, shortName));
		dao.put(fullName, rating, bloombergCode, coverRussian, shortName);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemUpdate(
			@PathVariable("id") Long id,
			@RequestParam String fullName,
			@RequestParam Integer rating,
			@RequestParam String bloombergCode,
			@RequestParam Integer coverRussian,
			@RequestParam String shortName)
	{
		log.info("POST Brokers: id={}, fullName={}, rating={}, bloombergCode={}, coverRussian={}, shortName={}",
				Utils.asArray(id, fullName, rating, bloombergCode, coverRussian, shortName));
		dao.updateById(id, fullName, rating, bloombergCode, coverRussian, shortName);
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
