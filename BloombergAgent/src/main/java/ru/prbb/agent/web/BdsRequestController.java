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
@RequestMapping("/BdsRequest")
public class BdsRequestController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final BloombergServices bs;

	@Autowired
	public BdsRequestController(BloombergServices bs) {
		this.bs = bs;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
	public String getHelp() {
		log.trace("GET");

		return "BdsRequest //blp/refdata, ReferenceDataRequest"
				+ "\n"
				+ "Параметр\n"
				+ "securities\n"
				+ "fields"
				+ "\n"
				+ "Результат\n"
				+ "BEST_ANALYST_RECS_BULK -> [ security -> [ { field, value } ] ]\n"
				+ "EARN_ANN_DT_TIME_HIST_WITH_EPS -> [ security -> [ { field, value } ] ]\n"
				+ "ERN_ANN_DT_AND_PER -> [ security -> [ { field, value } ] ]\n"
				+ "PeerTicker -> [ security -> [ peer ] ]\n"
				+ "Peers -> [ PeerData ]\n"
				+ "\n"
				+ "\n";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Object postExecute(
			@RequestParam(required = false, defaultValue = "BdsRequest") String name,
			@RequestParam String[] securities,
			@RequestParam String[] fields)
	{
		log.info("POST BdsRequest: name={}", name);
		log.info("POST BdsRequest: securities={}", (Object) securities);
		log.info("POST BdsRequest: fields={}", (Object) fields);

		try {
			return bs.executeBdsRequest(name, securities, fields);
		} catch (Exception e) {
			log.error("POST BdsRequest " + e.getMessage(), e);
			return e;
		}
	}
}
