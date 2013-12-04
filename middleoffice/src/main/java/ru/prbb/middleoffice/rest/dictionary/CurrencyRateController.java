package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

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

/**
 * Курсы валют
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/CurrencyRate")
public class CurrencyRateController
{
	@Autowired
	private CurrencyRateDao dao;
	@Autowired
	private CurrenciesDao daoCurrencies;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CurrencyRateItem> show(
			@RequestParam String dated,
			@RequestParam String iso)
	{
		return dao.findAll(Utils.parseDate(dated), Utils.parseString(iso));
	}

	@RequestMapping(value = "/Currencies", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json")
	public @ResponseBody
	List<SimpleItem> comboCurrencies(
			@RequestParam(required = false) String query)
	{
		return daoCurrencies.findCombo(query);
	}
}
