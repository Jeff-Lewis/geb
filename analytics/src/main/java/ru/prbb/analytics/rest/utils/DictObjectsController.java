package ru.prbb.analytics.rest.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ContactStaffItem;
import ru.prbb.analytics.domain.DictObjectItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.users.DictObjectsDao;

/**
 * Справочник пользователей - Пользователи
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/DictObjects")
public class DictObjectsController
{

	@Autowired
	private DictObjectsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DictObjectItem> show()
	{
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData showById(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam String name,
			@RequestParam String comment)
	{
		dao.put(name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam String comment)
	{
		dao.updateById(id, name, comment);
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
}
