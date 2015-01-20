package ru.prbb.jobber.web.logs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.LogUserActionItem;
import ru.prbb.jobber.repo.UserHistory;
import ru.prbb.jobber.web.BaseController;

/**
 * Журнал отправки сообщений
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LogUserActions")
public class LogUserActionsController
		extends BaseController
{

	@Autowired
	private UserHistory uh;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<LogUserActionItem> postShow(
			@RequestParam String begin,
			@RequestParam String end)
	{
		log.info("POST LogUserActions: begin={}, end={}", begin, end);
		return uh.getHistory(parseDateTime(begin), parseDateTime(end));
	}
}
