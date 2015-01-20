package ru.prbb.jobber.web.contacts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.GroupAddressItem;
import ru.prbb.jobber.domain.GroupContactsItem;
import ru.prbb.jobber.domain.Result;
import ru.prbb.jobber.domain.ResultData;
import ru.prbb.jobber.domain.SimpleItem;
import ru.prbb.jobber.repo.GroupsDao;
import ru.prbb.jobber.web.BaseController;

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
	public List<SimpleItem> getItems()
	{
		log.info("GET Groups");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Groups: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam String name)
	{
		log.info("POST Groups: name={}", name);
		dao.put(name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		log.info("POST Groups: id={}, name={}", id, name);
		dao.updateById(id, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL Groups: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Addresses", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<GroupAddressItem> getAddresses(
			@PathVariable("id") Long id)
	{
		log.info("GET Groups/Addresses: id={}", id);
		return dao.findAllAddresses(id);
	}

	@RequestMapping(value = "/{id}/Contacts", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<GroupContactsItem> getContacts(
			@PathVariable("id") Long id)
	{
		log.info("GET Groups/Contacts: id={}", id);
		return dao.findAllContacts(id);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postStaff(
			@PathVariable("id") Long id,
			@RequestParam String action,
			@RequestParam Long[] cids)
	{
		log.info("POST Groups/Staff: id={}, action={}, cids={}", toArray(id, action, cids));
		action = action.toUpperCase();

		if ("ADD".equals(action)) {
			dao.putStaff(id, cids);
			return Result.SUCCESS;
		}

		if ("DEL".equals(action)) {
			dao.deleteStaff(id, cids);
			return Result.SUCCESS;
		}

		return Result.FAIL;
	}

}
