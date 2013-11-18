package ru.prbb.middleoffice.rest.operations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.repo.operations.SetSecurityRiscsDao;

/**
 * Задать параметры риска
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/SetSecurityRiscs")
public class SetSecurityRiscsController
{
	// @Autowired
	private SetSecurityRiscsDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Object save(
			@RequestParam Long id,
			@RequestParam Double riskATH,
			@RequestParam Double riskAVG,
			@RequestParam Double stopLoss,
			@RequestParam String comment)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> getPortfolio(
			@RequestParam String date)
	{
		// {call dbo.mo_WebGet_SelectPlReport_sp ?, null, 1}
		return new ArrayList<Map<String, Object>>();
	}
}
