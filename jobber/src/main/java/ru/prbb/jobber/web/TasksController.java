package ru.prbb.jobber.web;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.tasks.TaskData;
import ru.prbb.jobber.domain.tasks.TaskItem;
import ru.prbb.jobber.services.TasksService;

@Controller
@RequestMapping(value = "/Tasks", produces = "application/json")
public class TasksController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private TasksService tasks;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public TaskItem getTask()
	{
		return tasks.getTask();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TaskData getData(
			@PathVariable Long id)
	{
		log.info("GET TaskData id={}", id);
		return tasks.getTaskData(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String put(HttpServletRequest request,
			@PathVariable Long id)
	{
		String str = "";
		try {
			try (BufferedReader r = request.getReader()) {
				str = r.readLine();
			}
			//log.info("PUT UpdateTaskData id={}, {}", id, str);
			tasks.updateTaskData(id, str);
		} catch (Exception e) {
			log.error("PUT UpdateTaskData id=" + id + ", " + str, e);
			return "ERROR:" + e.getMessage();
		}
		return "";
	}

}
