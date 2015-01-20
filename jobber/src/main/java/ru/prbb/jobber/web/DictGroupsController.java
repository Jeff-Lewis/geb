package ru.prbb.jobber.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.DictGroupItem;
import ru.prbb.jobber.domain.DictGroupsObjectItem;
import ru.prbb.jobber.domain.DictGroupsPermisionItem;
import ru.prbb.jobber.domain.DictGroupsUserItem;
import ru.prbb.jobber.domain.Result;
import ru.prbb.jobber.domain.ResultData;
import ru.prbb.jobber.repo.users.DictGroupsDao;

/**
 * Справочник пользователей - Группы
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/DictGroups")
public class DictGroupsController
		extends BaseController
{

	@Autowired
	private DictGroupsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DictGroupItem> getItems()
	{
		log.info("GET DictGroups");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET DictGroups: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemAdd(
			@RequestParam String name,
			@RequestParam String comment)
	{
		log.info("POST DictGroups: name={}, comment={}", name, comment);
		dao.put(name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemUpdate(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam String comment)
	{
		log.info("POST DictGroups: id={}, name={}, comment={}", toArray(id, name, comment));
		dao.updateById(id, name, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL DictGroups: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Users", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DictGroupsUserItem> getUsers(
			@PathVariable("id") Long idGroup)
	{
		log.info("GET DictGroups/Users: idGroup={}", idGroup);
		return dao.findAllUsers(idGroup);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DictGroupsUserItem> getStaff(
			@PathVariable("id") Long idGroup)
	{
		log.info("GET DictGroups/Staff: idGroup={}", idGroup);
		return dao.findAllStaff(idGroup);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postStaff(
			@PathVariable("id") Long idGroup,
			@RequestParam String action,
			@RequestParam Long[] ids)
	{
		log.info("POST DictGroups/Staff: idGroup={}, action={}, ids={}",
				toArray(idGroup, action, ids));
		action = action.toUpperCase();

		if ("ADD".equals(action)) {
			dao.addStuff(idGroup, ids);
			return Result.SUCCESS;
		}

		if ("DEL".equals(action)) {
			dao.delStuff(idGroup, ids);
			return Result.SUCCESS;
		}

		return Result.FAIL;
	}

	@RequestMapping(value = "/{id}/Objects", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DictGroupsObjectItem> getObjects(
			@PathVariable("id") Long idGroup)
	{
		log.info("GET DictGroups/Objects: idGroup={}", idGroup);
		return dao.findAllObjects(idGroup);
	}

	@RequestMapping(value = "/{id}/Permission", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DictGroupsPermisionItem> getPermission(
			@PathVariable("id") Long idGroup)
	{
		log.info("GET DictGroups/Permission: idGroup={}", idGroup);
		return dao.findAllPermission(idGroup);
	}

	@RequestMapping(value = "/{id}/Permission", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postPermission(
			@PathVariable("id") Long idGroup,
			@RequestParam String action,
			@RequestParam(required = false) Long[] objects,
			@RequestParam Long[] ids)
	{
		log.info("POST DictGroups/Permission: idGroup={}, action={}, objects={}, ids={}",
				toArray(idGroup, action, objects, ids));
		action = action.toUpperCase();

		if ("ADD".equals(action)) {
			dao.addPermission(idGroup, objects, ids);
			return Result.SUCCESS;
		}

		if ("DEL".equals(action)) {
			dao.delPermission(idGroup, ids);
			return Result.SUCCESS;
		}

		return Result.FAIL;
	}
}
