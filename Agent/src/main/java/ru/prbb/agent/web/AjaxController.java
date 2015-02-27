package ru.prbb.agent.web;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.agent.domain.JobServer;
import ru.prbb.agent.services.JobServerRepository;

/**
 * @author RBr
 */
@Controller
@RequestMapping(produces = "text/plain;charset=utf-8")
public class AjaxController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JobServerRepository servers;

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	@ResponseBody
	public String start(
			@RequestParam(required = false, defaultValue = "") String host) {
		log.debug("Start: host={}", host);

		if (host.isEmpty()) {
			return "{ RESULT : false, ERROR : 'host is empty' }";
		}

		servers.add(host);

		return "{ RESULT : true }";
	}

	@RequestMapping(value = "/stop", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String stop(
			@RequestParam(required = false, defaultValue = "") String host) {
		log.debug("Stop: host={}", host);

		if (host.isEmpty()) {
			return "{ RESULT : false, ERROR : 'host is empty' }";
		}

		servers.remove(host);

		return "{ RESULT : true }";
	}

	@RequestMapping(value = "/status", method = RequestMethod.GET)
	@ResponseBody
	public String getStatus() {
		log.debug("Status");

		StringBuilder sb = new StringBuilder();

		Iterator<JobServer> it = servers.getServers();
		while (it.hasNext()) {
			JobServer server = it.next();
			sb.append("<tr><td>");
			sb.append(server);
			sb.append("</td><td>");
			sb.append(server.getStatus());
			sb.append("</td></tr>");
		}

		return sb.toString();
	}
}
