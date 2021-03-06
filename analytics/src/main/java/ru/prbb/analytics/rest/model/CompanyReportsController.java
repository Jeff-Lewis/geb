package ru.prbb.analytics.rest.model;

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
import ru.prbb.analytics.domain.CompanyAllItem;
import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.model.CompanyReportsDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Компании и отчёты
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/CompanyReports")
public class CompanyReportsController
		extends BaseController
{

	@Autowired
	private CompanyReportsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> getItems(HttpServletRequest request)
	{
		log.info("GET CompanyReports");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET CompanyReports: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request), id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemAdd(HttpServletRequest request,
			@RequestParam String name)
	{
		log.info("POST CompanyReports: name={}", name);
		dao.put(createUserInfo(request), name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postItemRename(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		log.info("POST CompanyReports: id={}, name={}", id, name);
		dao.renameById(createUserInfo(request), id, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("DEL CompanyReports: id={}", id);
		dao.deleteById(createUserInfo(request), id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}/All", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompanyAllItem> getAll(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET CompanyReports/All: id={}", id);
		return dao.findStaff(createUserInfo(request), id);
	}

	@RequestMapping(value = "/{id}/Report", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CompanyStaffItem> getReport(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET CompanyReports/Report: id={}", id);
		return dao.findStaffReport(createUserInfo(request), id);
	}

	@RequestMapping(value = "/{id}/Staff", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postStaff(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String action,
			@RequestParam Long[] ids)
	{
		log.info("GET CompanyReports/Staff: id={}, action={}, ids={}", Utils.asArray(id, action, ids));
		action = action.toUpperCase();

		if ("ADD".equals(action)) {
			dao.putStaff(createUserInfo(request), id, ids);
			return Result.SUCCESS;
		}

		if ("DEL".equals(action)) {
			dao.deleteStaff(createUserInfo(request), id, ids);
			return Result.SUCCESS;
		}

		return Result.FAIL;
	}
}
