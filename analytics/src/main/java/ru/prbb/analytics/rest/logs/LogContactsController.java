package ru.prbb.analytics.rest.logs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.LogContactItem;
import ru.prbb.analytics.repo.LogDao;
import ru.prbb.analytics.rest.BaseController;

/**
 * Журнал изменений справочника контактов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LogContacts")
public class LogContactsController
		extends BaseController
{

	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<LogContactItem> postShow(
			@RequestParam String start,
			@RequestParam String stop)
	{
		log.info("POST LogContacts: start={}, stop={}", start, stop);
		return dao.getLogContacts(start, stop);
	}
}
