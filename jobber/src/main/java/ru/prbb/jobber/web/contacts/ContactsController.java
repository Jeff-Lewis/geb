package ru.prbb.jobber.web.contacts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.ContactStaffItem;
import ru.prbb.jobber.domain.Result;
import ru.prbb.jobber.domain.ResultData;
import ru.prbb.jobber.domain.SimpleItem;
import ru.prbb.jobber.repo.ContactsDao;
import ru.prbb.jobber.web.BaseController;

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
	public List<SimpleItem> getItems()
	{
		log.info("GET Contacts");
		return dao.findAll();
	}

	/**
	 * Контакт
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET Contacts: id={}", id);
		return new ResultData(dao.findById(id));
	}

	/**
	 * Добавить контакт
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(
			@RequestParam String name)
	{
		log.info("POST Contacts add: name={}", name);
		dao.put(name);
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
	public Result postUpdateItem(
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		log.info("POST Contacts: id={}, name={}", id, name);
		dao.updateById(id, name);
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
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL Contacts: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ContactStaffItem> getStaff(
			@PathVariable("id") Long id)
	{
		log.info("GET Contacts/Staff: id={}", id);
		return dao.findAllStaff(id);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddStaff(
			@PathVariable("id") Long id,
			@RequestParam String name,
			@RequestParam Integer type)
	{
		log.info("POST Contacts/Staff: id={}, name={}, type={}", toArray(id, name, type));
		dao.putStaff(id, name, type);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff/{cid}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateStaff(
			@PathVariable("id") Long id,
			@PathVariable("cid") Long cid,
			@RequestParam String name)
	{
		log.info("POST Contacts/Staff: id={}, cid={}, name={}", toArray(id, cid, name));
		dao.updateByIdStaff(id, cid, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/Staff/{cid}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteStaff(
			@PathVariable("id") Long id,
			@PathVariable("cid") Long cid)
	{
		log.info("POST Contacts/Staff: id={}, cid={}", id, cid);
		dao.deleteByIdStaff(id, cid);
		return Result.SUCCESS;
	}
}
