package ru.prbb.analytics.rest.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ResultData;
import ru.prbb.analytics.domain.ViewModelItem;
import ru.prbb.analytics.repo.model.ViewModelDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Просмотр текущей модели
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewModel")
public class ViewModelController
		extends BaseController
{

	@Autowired
	private ViewModelDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewModelItem> getItems(HttpServletRequest request)
	{
		log.info("GET ViewModel");
		return dao.findAll(createUserInfo(request));
	}

	@RequestMapping(value = "/{id}/Info", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItemInfo(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET ViewModel/Info: id={}", id);
		return new ResultData(dao.getInfoById(createUserInfo(request), id));
	}

	@RequestMapping(value = "/{id}/Price", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultData getItemPrice(HttpServletRequest request,
			@PathVariable("id") Long id)
	{
		log.info("GET ViewModel/Price: id={}", id);
		return new ResultData(dao.findPriceById(createUserInfo(request), id));
	}
}
