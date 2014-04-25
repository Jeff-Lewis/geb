package ru.prbb.middleoffice.rest.logs;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.LogContactItem;
import ru.prbb.middleoffice.repo.LogDao;

/**
 * Журнал изменений справочника контактов
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/LogContacts")
public class LogContactsController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<LogContactItem> postItems(
			@RequestParam String start,
			@RequestParam String stop)
	{
		log.info("POST LogContacts: start={}, stop={}", start, stop);
		return dao.getLogContacts(start, stop);
	}
}
