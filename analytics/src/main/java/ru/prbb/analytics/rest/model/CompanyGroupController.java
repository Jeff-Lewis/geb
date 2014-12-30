package ru.prbb.analytics.rest.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.model.CompanyGroupDao;
import ru.prbb.analytics.rest.BaseController;
import ru.prbb.analytics.rest.bloomberg.RequestBDPController;

/**
 * Компании и группы
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/CompanyGroup")
public class CompanyGroupController
		extends BaseController
{

	@Autowired
	private CompanyGroupDao dao;
	@Autowired
	private RequestBDPController reqBDP;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> getItems()
	{
		log.info("GET CompanyGroup");
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(
			@PathVariable("id") Long id)
	{
		log.info("GET CompanyGroup: id={}", id);
		return new ResultData(dao.findById(id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemAdd(
			@RequestParam String name)
	{
		log.info("GET CompanyGroup: name={}", name);
		dao.put(name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemRename(
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		log.info("POST CompanyGroup: id={}, name={}", id, name);
		dao.renameById(id, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL CompanyGroup: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompanyStaffItem> getAll(
			@PathVariable("id") Long id)
	{
		log.info("GET CompanyGroup/All: id={}", id);
		return dao.findStaff();
	}

	@RequestMapping(value = "/{id}/Group", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompanyStaffItem> getGroup(
			@PathVariable("id") Long id)
	{
		log.info("GET CompanyGroup/Group: id={}", id);
		return dao.findStaff(id);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postStaff(
			@PathVariable("id") Long id,
			@RequestParam String action,
			@RequestParam Long[] ids)
	{
		log.info("GET CompanyGroup/Staff: id={}, action={}, ids={}", Utils.asArray(id, action, ids));
		action = action.toUpperCase();

		if ("ADD".equals(action)) {
			dao.putStaff(id, ids);
		}

		if ("DEL".equals(action)) {
			dao.deleteStaff(id, ids);
		}

		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/RequestBDP", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Result  getRequestBDP(
			@PathVariable("id") Long id)
	{
		log.info("GET CompanyGroup/RequestBDP: id={}", id);

		List<CompanyStaffItem> list = dao.findStaff(id);

		String[] security = new String[list.size()];
		int i = 0;
		for (CompanyStaffItem item : list) {
			security[i++] = item.getSecurity_code();
		}
		reqBDP.postExecute(security, null);

		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/RequestYearly", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Result  getRequestYearly(
			@PathVariable("id") Long id)
	{
		log.info("GET CompanyGroup/RequestYearly: id={}", id);

		List<CompanyStaffItem> list = dao.findStaff(id);

		String[] security = new String[list.size()];
		int i = 0;
		for (CompanyStaffItem item : list) {
			security[i++] = item.getSecurity_code();
		}
		throw new RuntimeException("Функция в разработке.");
		//reqBDP.postExecute(security, null);

		//return Result.SUCCESS;
	}

}
