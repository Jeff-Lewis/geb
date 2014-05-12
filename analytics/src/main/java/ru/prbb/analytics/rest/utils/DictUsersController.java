package ru.prbb.analytics.rest.utils;

import java.util.ArrayList;
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

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private DictUsersDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DictUserItem> getItems()
	{
		log.info("GET DictUsers");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET DictUsers: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}/Info", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DictUsersInfoItem> getItemInfo(
			@PathVariable("id") Long id)
	{
		log.info("GET DictUsers/Info: id={}", id);
		return dao.findInfoById(id);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemAdd(
			@RequestParam String login,
			@RequestParam String password,
			@RequestParam String name,
			@RequestParam String email)
	{
		log.info("POST DictUsers: login={}, password={}, name={}, email={}",
				Utils.asArray(login, password, name, email));
		dao.put(login, password, name, email);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemUpdate(
			@PathVariable("id") Long id,
			@RequestParam String login,
			@RequestParam String password,
			@RequestParam String name,
			@RequestParam String email)
	{
		log.info("POST DictUsers: id={}, login={}, password={}, name={}, email={}",
				Utils.asArray(id, login, password, name, email));
		dao.updateById(id, login, password, name, email);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL DictUsers: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ContactStaffItem> getStaff(
			@PathVariable("id") Long id)
	{
		log.error("GET DictUsers/Staff");
		//return dao.findAllStaff(id);
		return new ArrayList<>();
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postStaff(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam Integer type)
	{
		log.error("POST DictUsers/Staff: id={}, name={}, type={}", Utils.asArray(id, name, type));
		//dao.putStaff(id, name, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff/{cid}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postStaffRename(
			@PathVariable("id") Long id,
			@PathVariable("cid") Long cid,
			@RequestParam String name)
	{
		log.error("POST DictUsers/Staff: id={}, cid={}, name={}", Utils.asArray(id, cid, name));
		//dao.updateByIdStaff(id, cid, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff/{cid}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteStaff(
			@PathVariable("id") Long id,
			@PathVariable("cid") Long cid)
	{
		log.error("DEL DictUsers/Staff: id={}, cid={}", id, cid);
		//dao.deleteByIdStaff(id, cid);
		return Result.SUCCESS;
	}
}
