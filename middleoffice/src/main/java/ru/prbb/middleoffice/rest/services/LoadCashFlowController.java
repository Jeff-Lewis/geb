package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SecurityCashFlowItem;
import ru.prbb.middleoffice.repo.services.LoadCashFlowDao;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/LoadCashFlow")
public class LoadCashFlowController
{
	@Autowired
	private LoadCashFlowDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	ResultData show(
			@RequestParam String[] securities)
	{
		return new ResultData(dao.execute(securities));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SecurityCashFlowItem> getSecurities()
	{
		return dao.findAllSecurities();
	}
}
