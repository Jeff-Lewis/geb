package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.CurrenciesItem;
import ru.prbb.middleoffice.repo.dictionary.CurrenciesDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Валюты
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Currencies")
public class CurrenciesController
		extends BaseController
{

	@Autowired
	private CurrenciesDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CurrenciesItem> getItems(HttpServletRequest request)
	{
		log.info("GET Currencies");
		return dao.findAll(createUserInfo(request));
	}
}
