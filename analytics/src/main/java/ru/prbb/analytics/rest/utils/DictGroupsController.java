package ru.prbb.analytics.rest.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.DictGroupItem;
import ru.prbb.analytics.domain.DictGroupsObjectItem;
import ru.prbb.analytics.domain.DictGroupsPermisionItem;
import ru.prbb.analytics.domain.DictGroupsUserItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.users.DictGroupsDao;

/**
 * Справочник пользователей - Группы
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/DictGroups")
public class DictGroupsController
{

	@Autowired
	private DictGroupsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DictGroupItem> show()
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

	@RequestMapping(value = "/{id}/Users", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DictGroupsUserItem> getUsers(
			@PathVariable("id") Long idGroup)
	{
		return dao.findAllUsers(idGroup);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DictGroupsUserItem> getStaff(
			@PathVariable("id") Long idGroup)
	{
		return dao.findAllStaff(idGroup);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result editStaff(
			@PathVariable("id") Long idGroup,
			@RequestParam String action,
			@RequestParam Long[] ids)
	{
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
	public @ResponseBody
	List<DictGroupsObjectItem> getObjects(
			@PathVariable("id") Long idGroup)
	{
		return dao.findAllObjects(idGroup);
	}

	@RequestMapping(value = "/{id}/Permission", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DictGroupsPermisionItem> getPermission(
			@PathVariable("id") Long idGroup)
	{
		return dao.findAllPermission(idGroup);
	}

	@RequestMapping(value = "/{id}/Permission", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result editPermission(
			@PathVariable("id") Long idGroup,
			@RequestParam String action,
			@RequestParam(required = false) Long[] objects,
			@RequestParam Long[] ids)
	{
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
