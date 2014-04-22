package ru.prbb.middleoffice.rest.logs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.SubscriptionItem;
import ru.prbb.middleoffice.repo.LogDao;

/**
 * Журнал подписки
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LogSubscription")
public class LogSubscriptionController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<SubscriptionItem> getItems()
	{
		log.info("GET LogSubscription");
		return dao.getLogSubscription();
	}
}
