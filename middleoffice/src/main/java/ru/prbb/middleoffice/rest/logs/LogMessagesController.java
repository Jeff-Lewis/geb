package ru.prbb.middleoffice.rest.logs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.LogMessagesItem;
import ru.prbb.middleoffice.repo.LogDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Журнал отправки сообщений
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LogMessages")
public class LogMessagesController
		extends BaseController
{

	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<LogMessagesItem> getItems(
			@RequestParam String type,
			@RequestParam String start,
			@RequestParam String stop)
	{
		log.info("POST LogMessages: type={}, start={}, stop={}", Utils.toArray(type, start, stop));
		return dao.getLogMessages(type, start, stop);
	}
}
