package ru.prbb.agent.web;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.agent.services.TaskWorkerService;

/**
 * @author RBr
 */
@Controller
@RequestMapping(produces = "text/plain;charset=utf-8")
public class AjaxController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TaskWorkerService worker;

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public String start(
			@RequestParam(required = false) String host) {
		log.debug("Start: host={}", host);

		if (worker.isRunning()) {
			return "ERROR: Already running";
		}

		if (host != null && host.length() > 0) {
			try {
				worker.start(host);
				return "Started";
			} catch (URISyntaxException e) {
				return "ERROR: " + e.getMessage();
			}
		}

		return "ERROR: host is empty";
	}

	@RequestMapping(value = "/stop", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String stop() {
		log.debug("Stop");

		if (worker.isRunning()) {
			worker.stop();
			return "Stopped";
		}

		return "ERROR: Not running";
	}

	@RequestMapping(value = "/status", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String getStatus() {
		log.debug("Status");

		return worker.getStatus();
	}
}
