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

import ru.prbb.agent.services.SubscriptionService;

/**
 * @author ruslan
 */
@Controller
@RequestMapping(value = "/Subscriptions", method = { RequestMethod.GET, RequestMethod.POST }, produces = "text/plain;charset=utf-8")
public class SubscriptionController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SubscriptionService ss;

	@RequestMapping("/Start")
	@ResponseBody
	public String postStart(
			@RequestParam Long id,
			@RequestParam String[] securities) {
		log.info("Start Subscription id={} for securities={}", id, securities);
		return ss.start(id, securities);
	}

	@RequestMapping("/Data")
	@ResponseBody
	public String postData(
			@RequestParam Long id,
			@RequestParam(defaultValue = "false", required = false) Boolean isClean) {
		return ss.getData(id, isClean);
	}

	@RequestMapping("/Stop")
	@ResponseBody
	public String postStop(
			@RequestParam Long[] ids) {
		StringBuilder res = new StringBuilder();
		for (Long id : ids) {
			//log.info("Stop Subscriptions id={}", id);
			String r = ss.stop(id);
			res.append(id).append('\t').append(r).append('\n');
		}
		return res.toString();
	}

}
