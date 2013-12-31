package ru.prbb.analytics.rest.utils.contacts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.GroupAddressItem;
import ru.prbb.analytics.domain.GroupContactsItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.utils.GroupsDao;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Groups")
public class GroupsController
{
	@Autowired
	private GroupsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> listAllMembers()
	{
		return dao.findAll();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam String name)
	{
		dao.put(name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		int r = dao.updateById(id, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData lookupById(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result deleteById(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Addresses", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<GroupAddressItem> getAddresses(
			@RequestParam Long id)
	{
		return dao.findAllAddresses(id);
	}

	@RequestMapping(value = "/Contacts", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<GroupContactsItem> getContacts(
			@RequestParam Long id)
	{
		return dao.findAllContacts(id);
	}

	@RequestMapping(value = "/Staff/{id}/Add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result addStaff(
			@PathVariable("id") Long id,
			@RequestParam Long[] cids)
	{
		dao.putStaff(id, cids);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Staff/{id}/Del", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result delStaff(
			@PathVariable("id") Long id,
			@RequestParam Long[] cids)
	{
		dao.deleteStaff(id, cids);
		return Result.SUCCESS;
	}

}
