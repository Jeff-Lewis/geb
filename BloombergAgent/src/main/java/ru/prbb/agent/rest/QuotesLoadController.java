package ru.prbb.agent.rest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.agent.service.BloombergServices;
import ru.prbb.bloomberg.model.BloombergResultItem;

/**
 * Загрузка котировок
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/LoadQuotes")
public class QuotesLoadController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public QuotesLoadController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String get() {
		log.trace("GET");

		return "Загрузка котировок"
				+ "\n"
				+ "Параметр\n"
				+ "securities = [ security ]\n"
				+ "begin\n"
				+ "?end\n"
				+ "\n"
				+ "Результат\n"
				+ "[ { security, date, value } ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<BloombergResultItem> execute(
			@RequestParam String[] securities,
			@RequestParam String begin,
			@RequestParam(required = false) String end) {
		log.trace("POST");

		if (Utils.isEmpty(end)) {
			final Calendar c = Calendar.getInstance(Utils.LOCALE);
			c.add(Calendar.DAY_OF_MONTH, -1);
			end = new SimpleDateFormat("yyyy-MM-dd", Utils.LOCALE).format(c.getTime());
			log.info("End date is empty. end = " + end);
		}

		return bs.executeQuotesLoad(begin, end, securities);
	}
}
