package ru.prbb.analytics.rest.portfolio;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

	@Autowired
	private ViewPortfolioDao dao;

	@RequestMapping(value = "/Securities", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioItem> getSecurities(HttpServletRequest request)
	{
		log.info("GET ViewPortfolio/Securities");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewPortfolioItem> getPortfolio(HttpServletRequest request)
	{
		log.info("GET ViewPortfolio/Portfolio");
		return dao.findAllPortfolio(createUserInfo(request));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postChange(HttpServletRequest request,
			@RequestParam String action,
			@RequestParam Long[] ids)
	{
		log.info("POST ViewPortfolio: action={}, ids={}", action, ids);
		action = action.toUpperCase();

		if ("ADD".equals(action)) {
			dao.put(createUserInfo(request), ids);
			return Result.SUCCESS;
		}

		if ("DEL".equals(action)) {
			dao.del(createUserInfo(request), ids);
			return Result.SUCCESS;
		}

		return Result.FAIL;
	}

}
