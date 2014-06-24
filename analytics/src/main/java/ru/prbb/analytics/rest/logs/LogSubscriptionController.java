package ru.prbb.analytics.rest.logs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.SubscriptionItem;
import ru.prbb.analytics.repo.LogDao;
import ru.prbb.analytics.rest.BaseController;

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
	public List<SubscriptionItem> getShow()
	{
		log.info("GET LogSubscription");
		return dao.getLogSubscription();
	}
}
