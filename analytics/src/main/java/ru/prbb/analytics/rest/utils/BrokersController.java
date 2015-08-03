package ru.prbb.analytics.rest.utils;

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
import ru.prbb.analytics.domain.BrokerItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.utils.BrokersDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Справочник брокеров
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Brokers")
public class BrokersController
		extends BaseController
{

	@Autowired
	private BrokersDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<BrokerItem> getItems(HttpServletRequest request)
	{
		log.info("GET Brokers");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Brokers: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request), id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemAdd(HttpServletRequest request,
			@RequestParam String fullName,
			@RequestParam Integer rating,
			@RequestParam String bloombergCode,
			@RequestParam Integer coverRussian,
			@RequestParam String shortName)
	{
		log.info("POST Brokers: fullName={}, rating={}, bloombergCode={}, coverRussian={}, shortName={}",
				Utils.asArray(fullName, rating, bloombergCode, coverRussian, shortName));
		dao.put(createUserInfo(request), fullName, rating, bloombergCode, coverRussian, shortName);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemUpdate(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String fullName,
			@RequestParam Integer rating,
			@RequestParam String bloombergCode,
			@RequestParam Integer coverRussian,
			@RequestParam String shortName)
	{
		log.info("POST Brokers: id={}, fullName={}, rating={}, bloombergCode={}, coverRussian={}, shortName={}",
				Utils.asArray(id, fullName, rating, bloombergCode, coverRussian, shortName));
		dao.updateById(createUserInfo(request), id, fullName, rating, bloombergCode, coverRussian, shortName);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("DEL Brokers: id={}", id);
		dao.deleteById(createUserInfo(request), id);
		return Result.SUCCESS;
	}
}
