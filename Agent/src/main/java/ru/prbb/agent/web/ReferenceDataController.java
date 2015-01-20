/**
 * 
 */
package ru.prbb.agent.web;

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
@RequestMapping("/ReferenceData")
public class ReferenceDataController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public ReferenceDataController(BloombergServices bs) {
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
				+ "\n"
				+ "Результат\n"
				+ "[ security -> [ { field, value } ] ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "ReferenceDataRequest") String name,
			@RequestParam String[] securities,
			@RequestParam String[] fields)
	{
		log.info("POST ReferenceData: name={}", name);
		log.info("POST ReferenceData: securities={}", (Object) securities);
		log.info("POST ReferenceData: fields={}", (Object) fields);

		try {
			return bs.executeReferenceDataRequest(name, securities, fields);
		} catch (Exception e) {
			log.error("POST ReferenceData " + e.getMessage(), e);
			return e;
		}
	}
}
