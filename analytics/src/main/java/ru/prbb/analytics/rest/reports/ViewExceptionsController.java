package ru.prbb.analytics.rest.reports;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.ViewExceptionsItem;
import ru.prbb.analytics.repo.reports.ViewExceptionsDao;

/**
 * Отчёт по исключениям
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/ViewExceptions")
public class ViewExceptionsController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ViewExceptionsDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<ViewExceptionsItem> getItems()
	{
		log.info("GET ViewExceptions");
		return dao.execute();
	}
}
