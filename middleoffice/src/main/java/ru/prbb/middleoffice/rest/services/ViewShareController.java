package ru.prbb.middleoffice.rest.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.PortfolioItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.services.ViewShareDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Редактирование акций
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewShare")
public class ViewShareController
		extends BaseController
{

	@Autowired
	private ViewShareDao dao;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAdd(HttpServletRequest request,
			@RequestParam Long code,
			@RequestParam String deal)
	{
		log.info("POST ViewShare/Add: code={}, deal={}", code, deal);
		dao.put(createUserInfo(request),code, deal);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postDel(HttpServletRequest request,
			@RequestParam Long code,
			@RequestParam String deal)
	{
		log.info("POST ViewShare/Del: code={}, deal={}", code, deal);
		dao.del(createUserInfo(request),code, deal);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<PortfolioItem> getPortfolio(HttpServletRequest request)
	{
		log.info("GET ViewShare/Portfolio");
		return daoEquities.findAllPortfolio(createUserInfo(request));
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> listSecurities(HttpServletRequest request,
			@RequestParam(defaultValue = "Equity") String filter,
			@RequestParam(required = false) Long security)
	{
		filter = "Equity";
		log.info("POST ViewShare/Securities: filter={}, security={}", filter, security);
		return daoSecurities.findAll(createUserInfo(request),filter, security);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewShare: FilterSecurities='{}'", query);
		return daoSecurities.findComboShares(query);
	}
}
