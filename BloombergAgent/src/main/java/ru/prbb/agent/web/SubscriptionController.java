/**
 * 
 */
package ru.prbb.agent.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.agent.services.SubscriptionService;

/**
 * @author ruslan
 *
 */
@Controller
@RequestMapping(value = "/Subscriptions")
public class SubscriptionController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SubscriptionService ss;

	@RequestMapping("/{id}/start")
	@ResponseBody
	public String postStart(
			@RequestParam Long id,
			@RequestParam String[] securities) {
		log.info("Start Subscription id={} for securities={}", id, securities);
		return ss.start(id, securities);
	}

	@RequestMapping("/{id}/stop")
	@ResponseBody
	public String getStop(
			@RequestParam Long id) {
		log.info("Stop Subscription id={}", id);
		return ss.stop(id);
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public String getData(
			@RequestParam Long id) {
		return ss.getData(id);
	}

}
