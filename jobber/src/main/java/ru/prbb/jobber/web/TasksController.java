package ru.prbb.jobber.web;

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
		return tasks.getTaskData(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String put(HttpServletRequest request)
	{
		return "ERROR:Deprecated";
//		log.info("POST /Agents: ");
//		try {
//			byte[] bs = new byte[request.getContentLength()];
//			try (ServletInputStream is = request.getInputStream()) {
//				is.read(bs);
//			}
//			String s = new String(bs, "UTF-8");
//
//			log.info("ContentType=" + request.getContentType());
//			log.info("ContentLength=" + request.getContentLength());
//
//			int i1 = s.indexOf(' ');
//			int i2 = s.indexOf('\n');
//			long idTask = Long.parseLong(s.substring(0, i1));
//			String status = s.substring(i1 + 1, i2);
//
//			log.info("idTask=" + idTask);
//			log.info("status=" + status);
//
//			if ("OK".equals(status)) {
//				String result = s.substring(i2 + 1);
//
//				AgentTask task = tasks.find(idTask);
//				task.setResult(result);
//				return "OK";
//			} else {
//				return status;
//			}
//		} catch (Exception e) {
//			return "ERROR:" + e.getMessage();
//		}
	}

}
