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

import ru.prbb.agent.services.SubscriptionService;

/**
 * @author ruslan
 */
@Controller
@RequestMapping(value = "/Subscription", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
public class SubscriptionController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SubscriptionService ss;

	@RequestMapping("/Start")
	@ResponseBody
	public String postStart(
			@RequestParam Integer id,
			@RequestParam String[] securities,
			@RequestParam String uriCallback) {
		log.debug("Subscription: Start id={}, uriCallback={}, securities={}", id, uriCallback, securities);
		return ss.start(id, securities, uriCallback);
	}

	@RequestMapping("/Stop")
	@ResponseBody
	public String postStop(
			@RequestParam Integer id) {
		log.debug("Subscription: Stop id={}", id);
		return ss.stop(id);
	}

}
