package ru.prbb.agent.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.agent.service.BloombergServices;
import ru.prbb.bloomberg.model.BloombergResultItem;

/**
 * Загрузка ставки по купонам
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/LoadRateCoupon")
public class RateCouponLoadRestController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public RateCouponLoadRestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public @ResponseBody
	String get() {
		log.trace("GET");

		return "Загрузка ставки по купонам"
				+ "\n"
				+ "Параметр\n"
				+ "securities = [ id:security ]\n"
				+ "\n"
				+ "Результат\n"
				+ "[ { id, security, date, value } ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<BloombergResultItem> execute(
			@RequestParam String[] securities) {
		log.trace("POST");

		Map<String, Long> items = new HashMap<>();

		for (String s : securities) {
			final int p = s.indexOf(':');

			final Long id = new Long(s.substring(0, p));
			final String name = s.substring(p + 1);

			if (log.isTraceEnabled()) {
				log.trace(s + " -> {" + id + ", '" + name + "'}");
			}

			items.put(name, id);
		}

		return bs.executeRateCouponLoad(items);
	}
}
