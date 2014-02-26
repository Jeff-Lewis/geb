/**
 * 
 */
package ru.prbb.agent.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.prbb.agent.service.BloombergServices;

/**
 * @author RBr
 */
@RestController
@RequestMapping("/LoadAtrRequest")
public class LoadAtrRequestController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public LoadAtrRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String get() {
		log.trace("GET");

		return "Выполнить запрос LoadAtrRequest"
				+ "\n"
				+ "Параметр\n"
				+ "ids\n"
				+ "dates\n"
				+ "\n"
				+ "Результат\n"
				+ "[ security -> [ { field, value } ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object execute(
			@RequestParam String startDate,
			@RequestParam String endDate,
			@RequestParam String[] securities,
			@RequestParam String maType,
			@RequestParam Integer taPeriod,
			@RequestParam String period,
			@RequestParam String calendar) {
		log.trace("POST");

		try {
			return bs.executeLoadAtrRequest("Загрузка ATR", startDate, endDate,
					securities, maType, taPeriod, period, calendar);
		} catch (Exception e) {
			return e;
		}
	}
}
