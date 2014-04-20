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
import ru.prbb.analytics.domain.DictUserItem;
import ru.prbb.analytics.domain.DictUsersInfoItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.users.DictUsersDao;

/**
 * Справочник пользователей - Пользователи
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/DictUsers")
public class DictUsersController
{

	@Autowired
	private DictUsersDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DictUserItem> show()
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

	@RequestMapping(value = "/{id}/Info", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DictUsersInfoItem> showInfoById(
			@PathVariable("id") Long id)
	{
		return dao.findInfoById(id);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam String login,
			@RequestParam String password,
			@RequestParam String name,
			@RequestParam String email)
	{
		dao.put(login, password, name, email);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam String login,
			@RequestParam String password,
			@RequestParam String name,
			@RequestParam String email)
	{
		dao.updateById(id, login, password, name, email);
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

	@RequestMapping(value = "/Staff", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ContactStaffItem> getStaff(
			@RequestParam Long id)
	{
		return new ArrayList<>();//dao.findAllStaff(id);
	}

	@RequestMapping(value = "/Staff/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result addStaff(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam Integer type)
	{
		//dao.putStaff(id, name, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Staff/{id}/{cid}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result renStaff(
			@PathVariable("id") Long id,
			@PathVariable("cid") Long cid,
			@RequestParam String name)
	{
		//dao.updateByIdStaff(id, cid, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Staff/{id}/{cid}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result delStaff(
			@PathVariable("id") Long id,
			@PathVariable("cid") Long cid)
	{
		//dao.deleteByIdStaff(id, cid);
		return Result.SUCCESS;
	}
}
