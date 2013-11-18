package ru.prbb.middleoffice.rest.logs;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.repo.LogDao;

/**
 * Журнал изменений справочника контактов
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/LogContacts")
public class LogContactsController
{
	@Autowired
	private LogDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<Map<String, Object>> show(
			@RequestParam String start,
			@RequestParam String stop)
	{
		// {call dbo.check_ncontacts_change_log ?, ?}
		return dao.getLogContacts(start, stop);
	}
}
