package ru.prbb.middleoffice.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.domain.SecurityItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.domain.ViewOptionsItem;
import ru.prbb.middleoffice.repo.EquitiesDao;
import ru.prbb.middleoffice.repo.SecuritiesDao;
import ru.prbb.middleoffice.repo.dictionary.OptionsDao;
import ru.prbb.middleoffice.repo.services.ViewOptionsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Редактирование опционов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewOptions")
public class ViewOptionsController
		extends BaseController
{

	@Autowired
	private ViewOptionsDao dao;
	@Autowired
	private OptionsDao daoOptions;
	@Autowired
	private EquitiesDao daoEquities;
	@Autowired
	private SecuritiesDao daoSecurities;

	@RequestMapping(value = "/Add", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postAdd(
			@RequestParam Long code,
			@RequestParam String deal,
			@RequestParam Long futures)
	{
		log.info("POST ViewOptions/Add: code={}, deal={}, futures={}", Utils.toArray(code, deal, futures));
		dao.put(code, deal, futures);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Del", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postDel(
			@RequestParam Long code,
			@RequestParam String deal)
	{
		log.info("POST ViewOptions/Del: code={}, deal={}", code, deal);
		dao.del(code, deal);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/Options", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboOptions(
			@RequestParam String query)
	{
		log.info("COMBO ViewOptions: Options='{}'", query);
		return daoOptions.findCombo(query);
	}

	@RequestMapping(value = "/Portfolio", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewOptionsItem> getPortfolio()
	{
		log.info("GET ViewOptions/Portfolio");
		return daoEquities.findAllOptions();
	}

	@RequestMapping(value = "/Securities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SecurityItem> listSecurities(
			@RequestParam(defaultValue = "Option") String filter,
			@RequestParam(required = false) Long security)
	{
		filter = "Option";
		log.info("POST ViewOptions/Securities: filter={}, security={}", filter, security);
		return daoSecurities.findAll(filter, security);
	}

	@RequestMapping(value = "/FilterSecurities", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboFilterSecurities(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO ViewOptions: FilterSecurities='{}'", query);
		return daoSecurities.findComboOptions(query);
	}
}
