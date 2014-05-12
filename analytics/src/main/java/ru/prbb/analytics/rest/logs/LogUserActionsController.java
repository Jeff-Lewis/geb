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

import ru.prbb.Utils;
import ru.prbb.analytics.domain.LogUserActionItem;
import ru.prbb.analytics.repo.LogDao;

/**
 * Журнал отправки сообщений
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LogUserActions")
public class LogUserActionsController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<LogUserActionItem> postShow(
			@RequestParam String begin,
			@RequestParam String end)
	{
		log.info("POST LogUserActions: begin={}, end={}", begin, end);
		return dao.getLogUserActions(Utils.parseDate(begin), Utils.parseDate(end));
	}
}
