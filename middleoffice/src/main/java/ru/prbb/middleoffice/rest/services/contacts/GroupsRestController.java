package ru.prbb.middleoffice.rest.services.contacts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.services.contacts.ContactsDao;

/**
 * Справочник контактов
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Groups")
public class GroupsRestController
{
	@Autowired
	private ContactsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> listAllMembers()
	{
		// {call dbo.WebGet_SelectGroups_sp}
		ArrayList<SimpleItem> list = new ArrayList<SimpleItem>();
		for (long i = 1; i < 11; i++) {
			SimpleItem e = new SimpleItem();
			e.setId(i);
			e.setName("name" + i);
			list.add(e);
		}
		return list;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam String name)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result update(
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData lookupById(
			@PathVariable("id") Long id)
	{
		// {call dbo.WebGet_SelectContactsAddress_sp ?}
		// {call dbo.WebGet_SelectGroupContacts_sp ?}
		SimpleItem e = new SimpleItem();
		e.setId(id);
		e.setName("name" + id);
		return new ResultData(e);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result deleteById(
			@PathVariable("id") Long id)
	{
		return Result.SUCCESS;
	}
}
