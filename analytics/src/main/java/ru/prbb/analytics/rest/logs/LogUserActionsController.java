package ru.prbb.analytics.rest.logs;

import java.util.List;

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
 * 
 */
@Controller
@RequestMapping("/rest/LogUserActions")
public class LogUserActionsController
{
	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<LogUserActionItem> show(
			@RequestParam String begin,
			@RequestParam String end)
	{
		return dao.getLogUserActions(Utils.parseDate(begin), Utils.parseDate(end));
	}
}
