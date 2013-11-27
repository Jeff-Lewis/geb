package ru.prbb.analytics.rest.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.repo.company.CompanyAddDao;

/**
 * Добавление компаний
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/CompanyAdd")
public class CompanyAddController
{
	@Autowired
	private CompanyAddDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData add(
			@RequestParam String[] codes)
	{
		return new ResultData(dao.execute(codes));
	}
}
