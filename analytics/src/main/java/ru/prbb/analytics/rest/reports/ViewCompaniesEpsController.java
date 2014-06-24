package ru.prbb.analytics.rest.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ViewCompaniesEpsItem;
import ru.prbb.analytics.repo.reports.ViewCompaniesEpsDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * EPS по компаниям
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewCompaniesEps")
public class ViewCompaniesEpsController
		extends BaseController
{

	@Autowired
	private ViewCompaniesEpsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewCompaniesEpsItem> getShow()
	{
		log.info("GET ViewCompaniesEps");
		return dao.execute();
	}
}
