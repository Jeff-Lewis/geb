package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.NoConformityItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.repo.services.NoConformityDao;

/**
 * Нет соответствия
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/NoConformity")
public class NoConformityController
{
	@Autowired
	private NoConformityDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<NoConformityItem> show()
	{
		return dao.show();
	}

	@RequestMapping(value = "/Delete", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result delete(
			@RequestParam Long[] ids)
	{
		dao.delete(ids);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/ExportXls", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result exportXls()
	{
		return Result.SUCCESS;
	}
}
