package ru.prbb.analytics.rest.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.ViewModelItem;
import ru.prbb.analytics.repo.model.ViewModelDao;

/**
 * Просмотр текущей модели
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/ViewModel")
public class ViewModelController
{
	@Autowired
	private ViewModelDao dao;

	@RequestMapping(value = "/Current", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<ViewModelItem> list()
	{
		return dao.findAll();
	}

	@RequestMapping(value = "/{id}/Info", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData getInfo(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.getInfoById(id));
	}

	@RequestMapping(value = "/{id}/Price", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData getPrice(
			@PathVariable("id") Long id)
	{
		return new ResultData(dao.findPriceById(id));
	}
}
