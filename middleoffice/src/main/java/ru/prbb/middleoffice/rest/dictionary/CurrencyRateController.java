package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.CurrencyRateItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.dictionary.CurrenciesDao;
import ru.prbb.middleoffice.repo.dictionary.CurrencyRateDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Курсы валют
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/CurrencyRate")
public class CurrencyRateController
		extends BaseController
{

	@Autowired
	private CurrencyRateDao dao;
	@Autowired
	private CurrenciesDao daoCurrencies;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<CurrencyRateItem> postItems(HttpServletRequest request,
			@RequestParam String dated,
			@RequestParam String iso)
	{
		log.info("POST CurrencyRate: dated={}, iso={}", dated, iso);
		return dao.findAll(createUserInfo(request),Utils.parseDate(dated), Utils.parseString(iso));
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public List<SimpleItem> comboCurrencies(
			@RequestParam(required = false) String query)
	{
		log.info("COMBO CurrencyRate: Currencies='{}'", query);
		return daoCurrencies.findCombo(query);
	}
}
