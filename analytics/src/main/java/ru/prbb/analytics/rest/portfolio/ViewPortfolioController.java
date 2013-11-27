package ru.prbb.analytics.rest.portfolio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ViewPortfolioItem;
import ru.prbb.analytics.domain.ViewPortfolioSecurityItem;
import ru.prbb.analytics.repo.portfolio.ViewPortfolioDao;

/**
 * Добавление организаций в Portfolio
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewPortfolio")
public class ViewPortfolioController
{
	@Autowired
	private ViewPortfolioDao dao;

	@RequestMapping(value = "/Securities", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ViewPortfolioSecurityItem> showSecurities()
	{
		return dao.getSecurities();
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ViewPortfolioItem> showPortfolio()
	{
		return dao.getPortfolio();
	}

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result add(
			@RequestParam String[] ids)
	{
		dao.put(ids);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result del(
			@RequestParam String[] ids)
	{
		dao.del(ids);
		return Result.SUCCESS;
	}
}
