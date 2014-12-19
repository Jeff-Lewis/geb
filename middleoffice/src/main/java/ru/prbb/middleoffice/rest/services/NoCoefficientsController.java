package ru.prbb.middleoffice.rest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.NoCoefficientsItem;
import ru.prbb.middleoffice.repo.services.NoCoefficientsDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Не хватает коэффициентов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/NoCoefficients")
public class NoCoefficientsController
		extends BaseController
{

	@Autowired
	private NoCoefficientsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<NoCoefficientsItem> getItems()
	{
		log.info("GET NotVisibleCoupons");

		List<NoCoefficientsItem> f = dao.showFutures();
		List<NoCoefficientsItem> o = dao.showOptions();

		List<NoCoefficientsItem> res = new ArrayList<>(f.size() + o.size());
		res.addAll(f);
		res.addAll(o);
		return res;
	}
}
