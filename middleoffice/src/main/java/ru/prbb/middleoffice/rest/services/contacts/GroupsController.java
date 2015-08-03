package ru.prbb.middleoffice.rest.services.contacts;

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
import ru.prbb.middleoffice.domain.GroupAddressItem;
import ru.prbb.middleoffice.domain.GroupContactsItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.services.contacts.GroupsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Справочник контактов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Groups")
public class GroupsController
		extends BaseController
{

	@Autowired
	private GroupsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> getItems(HttpServletRequest request)
	{
		log.info("GET Groups");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Groups: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request),id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(HttpServletRequest request,
			@RequestParam String name)
	{
		log.info("POST Groups: name={}", name);
		dao.put(createUserInfo(request),name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		log.info("POST Groups: id={}, name={}", id, name);
		dao.updateById(createUserInfo(request),id, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("DEL Groups: id={}", id);
		dao.deleteById(createUserInfo(request),id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Addresses", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<GroupAddressItem> getAddresses(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Groups/Addresses: id={}", id);
		return dao.findAllAddresses(createUserInfo(request),id);
	}

	@RequestMapping(value = "/{id}/Contacts", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<GroupContactsItem> getContacts(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Groups/Contacts: id={}", id);
		return dao.findAllContacts(createUserInfo(request),id);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postStaff(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String action,
			@RequestParam Long[] cids)
	{
		log.info("POST Groups/Staff: id={}, action={}, cids={}", Utils.toArray(id, action, cids));
		action = action.toUpperCase();

		if ("ADD".equals(action)) {
			dao.putStaff(createUserInfo(request),id, cids);
			return Result.SUCCESS;
		}

		if ("DEL".equals(action)) {
			dao.deleteStaff(createUserInfo(request),id, cids);
			return Result.SUCCESS;
		}

		return Result.FAIL;
	}

}
