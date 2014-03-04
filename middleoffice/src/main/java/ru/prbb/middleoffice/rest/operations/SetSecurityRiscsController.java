package ru.prbb.middleoffice.rest.operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ViewPortfolioTransferItem;
import ru.prbb.middleoffice.repo.operations.SetSecurityRiscsDao;
import ru.prbb.middleoffice.repo.portfolio.ViewPortfolioDao;

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

	@Autowired
	private SetSecurityRiscsDao dao;
	@Autowired
	private ViewPortfolioDao daoPortfolio;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result save(
			@RequestParam Long id,
			@RequestParam Double riskATH,
			@RequestParam Double riskAVG,
			@RequestParam Double stopLoss,
			@RequestParam String comment)
	{
		dao.execute(id, riskATH, riskAVG, stopLoss, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<ViewPortfolioTransferItem> getPortfolio(
			@RequestParam String date)
	{
		return daoPortfolio.executeSelect(Utils.parseDate(date));
	}
}
