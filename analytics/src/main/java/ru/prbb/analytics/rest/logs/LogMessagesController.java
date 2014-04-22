package ru.prbb.analytics.rest.logs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.LogMessagesItem;
import ru.prbb.analytics.repo.LogDao;

/**
 * Журнал отправки сообщений
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/LogMessages")
public class LogMessagesController
{
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<LogMessagesItem> show(
			@RequestParam String type,
			@RequestParam String start,
			@RequestParam String stop)
	{
		return dao.getLogMessages(type, start, stop);
	}
}
