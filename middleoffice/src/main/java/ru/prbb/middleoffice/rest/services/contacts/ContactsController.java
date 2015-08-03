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
import ru.prbb.middleoffice.domain.ContactStaffItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.services.contacts.ContactsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Справочник контактов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Contacts")
public class ContactsController
		extends BaseController
{

	@Autowired
	private ContactsDao dao;

	/**
	 * Список контактов
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> getItems(HttpServletRequest request)
	{
		log.info("GET Contacts");
		return dao.findAll(createUserInfo(request));
	}

	/**
	 * Контакт
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Contacts: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request),id));
	}

	/**
	 * Добавить контакт
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(HttpServletRequest request,
			@RequestParam String name)
	{
		log.info("POST Contacts add: name={}", name);
		dao.put(createUserInfo(request),name);
		return Result.SUCCESS;
	}

	/**
	 * Переименовать контакт
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		log.info("POST Contacts: id={}, name={}", id, name);
		dao.updateById(createUserInfo(request),id, name);
		return Result.SUCCESS;
	}

	/**
	 * Удалить контакт
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("DEL Contacts: id={}", id);
		dao.deleteById(createUserInfo(request),id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ContactStaffItem> getStaff(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Contacts/Staff: id={}", id);
		return dao.findAllStaff(createUserInfo(request),id);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddStaff(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam Integer type)
	{
		log.info("POST Contacts/Staff: id={}, name={}, type={}", Utils.toArray(id, name, type));
		dao.putStaff(createUserInfo(request),id, name, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff/{cid}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateStaff(HttpServletRequest request,
			@PathVariable("id") Long id,
			@PathVariable("cid") Long cid,
			@RequestParam String name)
	{
		log.info("POST Contacts/Staff: id={}, cid={}, name={}", Utils.toArray(id, cid, name));
		dao.updateByIdStaff(createUserInfo(request),id, cid, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff/{cid}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteStaff(HttpServletRequest request,
			@PathVariable("id") Long id,
			@PathVariable("cid") Long cid)
	{
		log.info("POST Contacts/Staff: id={}, cid={}", id, cid);
		dao.deleteByIdStaff(createUserInfo(request),id, cid);
		return Result.SUCCESS;
	}
}
