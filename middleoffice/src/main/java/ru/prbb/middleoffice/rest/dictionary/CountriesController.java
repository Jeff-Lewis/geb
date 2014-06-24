package ru.prbb.middleoffice.rest.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.CountryItem;
import ru.prbb.middleoffice.repo.dictionary.CountriesDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Страны
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/Countries")
public class CountriesController
		extends BaseController
{

	@Autowired
	private CountriesDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<CountryItem> getItems()
	{
		log.info("GET Countries");
		return dao.findAll();
	}
}
