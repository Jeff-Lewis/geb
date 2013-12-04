package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.CurrenciesItem;
import ru.prbb.middleoffice.repo.dictionary.CurrenciesDao;

/**
 * Валюты
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/Currencies")
public class CurrenciesController
{
	@Autowired
	private CurrenciesDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<CurrenciesItem> listAllMembers()
	{
		return dao.findAll();
	}
}
