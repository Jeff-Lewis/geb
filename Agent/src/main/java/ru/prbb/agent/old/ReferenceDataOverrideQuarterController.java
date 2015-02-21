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
@RequestMapping("/ReferenceDataOverrideQuarter")
public class ReferenceDataOverrideQuarterController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public ReferenceDataOverrideQuarterController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String getHelp() {
		log.trace("GET");

		return "Выполнить запрос //blp/refdata"
				+ "\n"
				+ "Параметр\n"
				+ "securities\n"
				+ "fields\n"
				+ "currencies\n"
				+ "over\n"
				+ "\n"
				+ "Результат\n"
				+ "[ security -> period -> [ { field, value } ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "BdpRequestOverrideQuarter") String name,
			@RequestParam String[] securities,
			@RequestParam String[] fields,
			@RequestParam String[] currencies,
			@RequestParam String over)
	{
		log.info("POST ReferenceDataOverrideQuarter: name={}", name);
		log.info("POST ReferenceDataOverrideQuarter: securities={}", (Object) securities);
		log.info("POST ReferenceDataOverrideQuarter: fields={}", (Object) fields);
		log.info("POST ReferenceDataOverrideQuarter: currencies={}", (Object) currencies);
		log.info("POST ReferenceDataOverrideQuarter: over={}", over);

		try {
			return bs.executeBdpRequestOverrideQuarter(name, securities, fields, currencies, over);
		} catch (Exception e) {
			log.error("POST ReferenceDataOverrideQuarter " + e.getMessage(), e);
			return e;
		}
	}
}
