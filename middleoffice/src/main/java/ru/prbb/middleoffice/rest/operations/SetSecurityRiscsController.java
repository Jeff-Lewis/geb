package ru.prbb.middleoffice.rest.operations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */
@Controller
@RequestMapping("/rest/SetSecurityRiscs")
public class SetSecurityRiscsController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SetSecurityRiscsDao dao;
	@Autowired
	private ViewPortfolioDao daoPortfolio;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postSave(
			@RequestParam Long id,
			@RequestParam Double riskATH,
			@RequestParam Double riskAVG,
			@RequestParam Double stopLoss,
			@RequestParam String comment)
	{
		log.info("POST SetSecurityRiscs: id={}, riskATH={}, riskAVG={}, stopLoss={}, comment={}",
				Utils.toArray(id, riskATH, riskAVG, stopLoss, comment));
		dao.execute(id, riskATH, riskAVG, stopLoss, comment);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioTransferItem> postGetPortfolio(
			@RequestParam String date)
	{
		log.info("POST SetSecurityRiscs/Portfolio: date={}", date);
		return daoPortfolio.executeSelect(Utils.parseDate(date));
	}
}
