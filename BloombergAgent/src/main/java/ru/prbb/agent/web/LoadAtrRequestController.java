/**
 * 
 */
package ru.prbb.agent.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public LoadAtrRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String getHelp() {
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
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "Загрузка ATR") String name,
			@RequestParam(value = "startDate") String dateStart,
			@RequestParam(value = "endDate") String dateEnd,
			@RequestParam String[] securities,
			@RequestParam String maType,
			@RequestParam Integer taPeriod,
			@RequestParam String period,
			@RequestParam String calendar)
	{
		log.info("POST LoadAtrRequest: name={}", name);
		log.info("POST LoadAtrRequest: dateStart={}, dateEnd={}, maType={}, taPeriod={}, period={}, calendar={}",
				dateStart, dateEnd, maType, taPeriod, period, calendar);
		log.info("POST LoadAtrRequest: securities={}", (Object) securities);

		try {
			return bs.executeLoadAtrRequest(name, dateStart, dateEnd,
					securities, maType, taPeriod, period, calendar);
		} catch (Exception e) {
			log.error("POST LoadAtrRequest " + e.getMessage(), e);
			return e;
		}
	}
}
