package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SecurityValuesItem;
import ru.prbb.middleoffice.repo.services.LoadValuesDao;

/**
 * Загрузка номинала
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/LoadValues")
public class LoadValuesController
{
	@Autowired
	private LoadValuesDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData show(
			@RequestParam String[] securities)
	{
		return new ResultData(dao.execute(securities));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecurityValuesItem> listSecurities()
	{
		return dao.findAllSecurities();
	}
}
