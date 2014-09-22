package ru.prbb.agent.rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.Utils;
import ru.prbb.agent.services.BloombergServices;
import ru.prbb.bloomberg.model.CashFlowData;
import ru.prbb.bloomberg.model.CashFlowResultItem;

/**
 * Загрузка дат погашений
 * 
 * @author RBr
 */
//@Controller
//@RequestMapping("/LoadCashFlow")
public class CashFlowLoadController {

	private final Log log = LogFactory.getLog(getClass());

	private final BloombergServices bs;

	@Autowired
	public CashFlowLoadController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public @ResponseBody
	String get() {
		log.trace("GET");

		return "Загрузка дат погашений"
				+ "\n"
				+ "Параметр\n"
				+ "securities = [ id:date:security ]\n"
				+ "\n"
				+ "Результат\n"
				+ "[ { id, security, params, date, value, value2 } ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	List<CashFlowResultItem> execute(
			@RequestParam String[] securities) {
		log.trace("POST");

		List<CashFlowData> datas = new ArrayList<>();

		for (String s : securities) {
			final int p = s.indexOf(':');
			final int p1 = s.indexOf(':', p + 1);

			final Long id = new Long(s.substring(0, p));
			final Date date = Utils.parseDate(s.substring(p + 1, p1));
			final String name = s.substring(p1 + 1);

			if (log.isTraceEnabled()) {
				log.trace(s + " -> {" +id + ", '" + name + "', " + date +"}");
			}

			datas.add(new CashFlowData(id, name, date));
		}

		return bs.executeCashFlowLoad(datas);
	}
}
