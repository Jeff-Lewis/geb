/**
 * 
 */
package ru.prbb.agent.web;

import java.util.Arrays;

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

		return "Выполнить запрос //blp/tasvc"
				+ "\n"
				+ "Параметр\n"
				+ "startDate : yyyymmdd\n"
				+ "endDate : yyyymmdd\n"
				+ "securities : []\n"
				+ "maType\n"
				+ "taPeriod\n"
				+ "period\n"
				+ "calendar\n"
				+ "\n"
				+ "Результат\n"
				+ "[ { security, date, value } ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object execute(
			@RequestParam(required = false, defaultValue = "Загрузка ATR") String name,
			@RequestParam(value = "startDate") String dateStart,
			@RequestParam(value = "endDate") String dateEnd,
			@RequestParam String[] securities,
			@RequestParam String maType,
			@RequestParam Integer taPeriod,
			@RequestParam String period,
			@RequestParam String calendar) {

		if (log.isInfoEnabled()) {
			log.info("POST execute " + dateStart + ", " + dateEnd + ", "
					+ maType + ", " + taPeriod + ", " + period + ", " + calendar);
			log.info("POST execute " + Arrays.asList(securities));
		}

		try {
			return bs.executeLoadAtrRequest(name, dateStart, dateEnd,
					securities, maType, taPeriod, period, calendar);
		} catch (Exception e) {
			log.error("POST execute " + e.getMessage(), e);
			return e;
		}
	}
}
