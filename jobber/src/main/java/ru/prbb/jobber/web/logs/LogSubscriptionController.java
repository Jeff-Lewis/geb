package ru.prbb.jobber.web.logs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.LogSubscriptionItem;
import ru.prbb.jobber.repo.LogDao;
import ru.prbb.jobber.web.BaseController;

/**
 * Журнал подписки
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LogSubscription")
public class LogSubscriptionController
		extends BaseController
{

	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<LogSubscriptionItem> getItems()
	{
		log.info("GET LogSubscription");
		return dao.getLogSubscription();
	}
}
