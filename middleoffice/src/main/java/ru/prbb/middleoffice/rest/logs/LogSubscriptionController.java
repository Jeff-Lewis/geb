package ru.prbb.middleoffice.rest.logs;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.repo.LogDao;

/**
 * Журнал подписки
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/LogSubscription")
public class LogSubscriptionController
{
	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> show()
	{
		// {call subscription_data_v_proc}
		return dao.getLogSubscription();
	}
}
