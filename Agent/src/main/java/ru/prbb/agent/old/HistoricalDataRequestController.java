/**
 * 
 */
package ru.prbb.agent.old;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.agent.services.BloombergServices;

/**
 * @author RBr
 */
@Controller
@RequestMapping("/HistoricalDataRequest")
public class HistoricalDataRequestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public HistoricalDataRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getHelp() {
		log.trace("GET");

		return "Выполнить запрос HistoricalDataRequest"
				+ "\n"
				+ "Параметр\n"
				+ "dateStart : yyyymmdd\n"
				+ "dateEnd : yyyymmdd\n"
				+ "securities : [ security|currency ]\n"
				+ "fields\n"
				+ "\n"
				+ "Результат\n"
				+ "[ security -> [ date -> [ {field -> value;period;currency;calendar } ] ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "HistoricalDataRequest") String name,
			@RequestParam String dateStart,
			@RequestParam String dateEnd,
			@RequestParam String[] securities,
			@RequestParam String[] fields,
			@RequestParam(required = false) String[] currencies)
	{
		log.info("POST HistoricalDataRequest: name={}", name);
		log.info("POST HistoricalDataRequest: dateStart={}, dateEnd={}", dateStart, dateEnd);
		log.info("POST HistoricalDataRequest: securities={}", (Object) securities);
		log.info("POST HistoricalDataRequest: fields={}", (Object) fields);
		log.info("POST HistoricalDataRequest: currencies={}", (Object) currencies);

		try {
			return bs.executeHistoricalDataRequest(name, dateStart, dateEnd, securities, fields, currencies);
		} catch (Exception e) {
			log.error("POST HistoricalDataRequest " + e.getMessage(), e);
			return e;
		}
	}
}
