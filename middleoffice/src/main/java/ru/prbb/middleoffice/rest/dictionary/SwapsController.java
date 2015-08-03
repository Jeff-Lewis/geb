package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.SwapItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.dictionary.SwapsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Свопы
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Swaps")
public class SwapsController
		extends BaseController
{

	@Autowired
	private SwapsDao dao;
	@Autowired
	private EquitiesDao daoEquities;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SwapItem> getItems(HttpServletRequest request)
	{
		log.info("GET Swaps");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET Swaps: id={}", id);
		return new ResultData(dao.findById(createUserInfo(request),id));
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAddItem(HttpServletRequest request,
			@RequestParam String swap,
			@RequestParam Long security)
	{
		log.info("POST Swaps add: swap={}, security={}", swap, security);
		dao.put(createUserInfo(request),swap, security);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpdateItem(HttpServletRequest request,
			@PathVariable("id") Long id,
			@RequestParam String swap)
	{
		log.info("POST Swaps update: id={}, swap={}", id, swap);
		dao.updateById(createUserInfo(request),id, swap);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("DEL Swaps: id={}", id);
		dao.deleteById(createUserInfo(request),id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Equities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboEquities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO Swaps: Equities='{}'", query);
		return daoEquities.findCombo(query);
	}
}
