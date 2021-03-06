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
import ru.prbb.middleoffice.domain.ReferenceItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.dictionary.TradesystemsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Торговые системы
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Tradesystems")
public class TradesystemsController
		extends BaseController
{

	@Autowired
	private TradesystemsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ReferenceItem> getItems(HttpServletRequest request)
	{
		log.info("GET Tradesystems");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Tradesystems: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request),id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(HttpServletRequest request,
			@RequestParam String name,
			@RequestParam String comment)
	{
		log.info("POST Tradesystems: name={}, comment={}", name, comment);
		dao.put(createUserInfo(request),name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam String comment)
	{
		log.info("POST Tradesystems: id={}, name={}, comment={}", Utils.toArray(id, name, comment));
		dao.updateById(createUserInfo(request),id, name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("DEL Tradesystems: id={}", id);
		dao.deleteById(createUserInfo(request),id);
		return Result.SUCCESS;
	}
}
