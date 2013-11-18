package ru.prbb.analytics.rest.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.CompanyStaffItem;
import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.SimpleItem;
import ru.prbb.analytics.repo.model.CompanyGroupDao;

/**
 * Компании и группы
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/CompanyGroup")
public class CompanyGroupController
{
	@Autowired
	private CompanyGroupDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> list()
	{
		// {call dbo.anca_WebGet_PivotGroups_sp}
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData get(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findById(id));
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
	Result ren(
			@PathVariable("id") Long id,
			@RequestParam String name)
	{
		dao.renameById(id, name);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result del(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "Staff/All", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CompanyStaffItem> listA(
			@RequestParam Long id)
	{
		// {call dbo.anca_WebGet_SelectEquitiesNotPivotGroup_sp}
		return dao.findStaff(id);
	}

	@RequestMapping(value = "Staff/Group", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CompanyStaffItem> listG(
			@RequestParam Long id)
	{
		// {call dbo.anca_WebGet_SelectPivotGroupEquities_sp ?}
		return dao.findStaffGroup(id);
	}

	@RequestMapping(value = "Staff/{id}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@PathVariable("id") Long id,
			@RequestParam String action,
			@RequestParam Long[] ids)
	{
		if ("ADD".equals(action)) {
			dao.putStaff(id, ids);
		}
		if ("DEL".equals(action)) {
			dao.deleteStaff(id, ids);
		}
		return Result.SUCCESS;
	}
}
