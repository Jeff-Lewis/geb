package ru.prbb.jobber.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.AgentTask;
import ru.prbb.jobber.services.AgentTaskService;

@Controller
@RequestMapping(value = "/AgentTask", produces = "application/json")
public class AgentTaskController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AgentTaskService tasks;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String get()
	{
		//log.info("GET /Agents");
		try {
			AgentTask task = tasks.next();
			return (task != null) ? task.getJson() : "";
		} catch (Exception e) {
			return "ERROR:" + e.getMessage();
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String put(
			@RequestParam String type,
			@RequestParam Long idTask,
			@RequestParam String result)
	{
		log.info("POST /Agents");
		try {
			AgentTask task = tasks.find(idTask);
			task.setResult(result);
			return "OK";
		} catch (Exception e) {
			return "ERROR:" + e.getMessage();
		}
	}

}
