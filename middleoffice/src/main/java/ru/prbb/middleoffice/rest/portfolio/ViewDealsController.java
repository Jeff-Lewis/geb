package ru.prbb.middleoffice.rest.portfolio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.portfolio.ViewDealsDao;

/**
 * Список сделок
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewDeals")
public class ViewDealsController
{
	// @Autowired
	private ViewDealsDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Object> show(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam String ticker)
	{
		return new ArrayList<Object>();
	}

	@RequestMapping(value = "/Export", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Result export(
			@RequestParam String dateBegin,
			@RequestParam String dateEnd,
			@RequestParam String ticker)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result del(
			@RequestParam Long[] deals)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Set", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	Result set(
			@RequestParam Long[] deals,
			@RequestParam String field,
			@RequestParam String value)
	{
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/TradeSystems", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboTradeSystems(
			@RequestParam(required = false) String query)
	{
		return new ArrayList<SimpleItem>();
	}

	@RequestMapping(value = "/Accounts", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboAccounts(
			@RequestParam(required = false) String query)
	{
		return new ArrayList<SimpleItem>();
	}

	@RequestMapping(value = "/Portfolio", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboPortfolio(
			@RequestParam(required = false) String query)
	{
		return new ArrayList<SimpleItem>();
	}

	@RequestMapping(value = "/Tickers", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboTickers(
			@RequestParam(required = false) String query)
	{
		return new ArrayList<SimpleItem>();
	}
}
