package ru.prbb.analytics.rest.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.BrokerItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.utils.BrokersDao;

/**
 * Справочник брокеров
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Brokers")
public class BrokersController
{
	@Autowired
	private BrokersDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<BrokerItem> list()
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
			@RequestParam String fullName,
			@RequestParam Integer rating,
			@RequestParam String bloombergCode,
			@RequestParam Integer coverRussian,
			@RequestParam String shortName)
	{
		dao.put(fullName, rating, bloombergCode, coverRussian, shortName);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam String fullName,
			@RequestParam Integer rating,
			@RequestParam String bloombergCode,
			@RequestParam Integer coverRussian,
			@RequestParam String shortName)
	{
		dao.updateById(id, fullName, rating, bloombergCode, coverRussian, shortName);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result delete(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
		return Result.SUCCESS;
	}
}
