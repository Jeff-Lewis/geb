package ru.prbb.analytics.rest.utils.contacts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ContactStaffItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.utils.ContactsDao;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Contacts")
public class ContactsController
{
	@Autowired
	private ContactsDao dao;

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
		dao.updateById(id, name);
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

	@RequestMapping(value = "/Staff", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ContactStaffItem> getStaff(
			@RequestParam Long id)
	{
		return dao.findAllStaff(id);
	}

	@RequestMapping(value = "/Staff/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result addStaff(
			@PathVariable Long id,
			@RequestParam String name,
			@RequestParam Integer type)
	{
		dao.putStaff(id, name, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Staff/{id}/{cid}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result renStaff(
			@PathVariable Long id,
			@PathVariable Long cid,
			@RequestParam String name)
	{
		dao.updateByIdStaff(id, cid, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Staff/{id}/{cid}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result delStaff(
			@PathVariable Long id,
			@PathVariable Long cid)
	{
		dao.deleteByIdStaff(id, cid);
		return Result.SUCCESS;
	}
}
