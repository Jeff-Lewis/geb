package ru.prbb.analytics.rest.portfolio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ViewPortfolioItem;
import ru.prbb.analytics.repo.portfolio.ViewPortfolioDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Добавление организаций в Portfolio
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewPortfolio")
public class ViewPortfolioController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ViewPortfolioDao dao;

	@RequestMapping(value = "/Securities", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioItem> getSecurities()
	{
		log.info("GET ViewPortfolio/Securities");
		return dao.findAll();
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioItem> getPortfolio()
	{
		log.info("GET ViewPortfolio/Portfolio");
		return dao.findAllPortfolio();
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postChange(
			@RequestParam String action,
			@RequestParam Long[] ids)
	{
		log.info("POST ViewPortfolio: action={}, ids={}", action, ids);
		action = action.toUpperCase();

		if ("ADD".equals(action)) {
			dao.put(ids);
			return Result.SUCCESS;
		}

		if ("DEL".equals(action)) {
			dao.del(ids);
			return Result.SUCCESS;
		}

		return Result.FAIL;
	}

}
