package ru.prbb.jobber.web;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.JobberTaskItem;
import ru.prbb.jobber.domain.Result;
import ru.prbb.jobber.domain.ResultData;
import ru.prbb.jobber.repo.ParametersDao;
import ru.prbb.jobber.services.Description;
import ru.prbb.jobber.services.ScheduledServices;

/**
 * Ручной запуск задач Жобера
 * 
 * @author RBr
 */
@Controller
@RequestMapping(value = "/JobberTasks")
public class JobberTasksController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ParametersDao parameters;

	@Autowired
	private ScheduledServices tasks;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<JobberTaskItem> list() {
		Method[] methods = tasks.getClass().getMethods();
		List<JobberTaskItem> list = new ArrayList<>(methods.length);
		for (Method method : methods) {
			String name = method.getName();
			if (name.startsWith("task")) {
				JobberTaskItem item = new JobberTaskItem();
				item.setName(name);

				Description d = method.getAnnotation(Description.class);
				item.setParameter((d != null) ? d.parameter() : null);
				item.setDescription((d != null) ? d.comment() : null);

				Scheduled s = method.getAnnotation(Scheduled.class);
				item.setCron((s != null) ? s.cron() : "-");

				if (s != null) {
					boolean taskDisabled = tasks.isTaskDisabled(item.getParameter());
					item.setEnabled(!taskDisabled);
				} else {
					item.setEnabled(Boolean.FALSE);
				}

				list.add(item);
			}
		}
		return list;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResultData update(
			@RequestParam String action,
			@RequestParam String task) {
		log.info("update task: {} {}", action, task);

		switch (action.toLowerCase()) {
		case "start":
			parameters.delValue(task);
			break;

		case "stop":
			parameters.setValue(task, "disabled");
			break;
		}

		return new ResultData(list());
	}
	
	@RequestMapping(value = "/taskBdsLoad")
	@ResponseBody
	public Result taskLoadBds() {
		log.info("web LoadBds");
		tasks.taskBdsLoad();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskFuturesLoad")
	@ResponseBody
	public Result taskLoadFutures() {
		log.info("web LoadFutures");
		tasks.taskFuturesLoad();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskQuotesLoad")
	@ResponseBody
	public Result taskLoadQuotes() {
		log.info("web LoadQuotes");
		tasks.taskQuotesLoad();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskAtrLoad")
	@ResponseBody
	public Result taskLoadAtr() {
		log.info("web LoadAtr");
		tasks.taskAtrLoad();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskBdpOverrideLoad")
	@ResponseBody
	public Result taskLoadBdpOverride() {
		log.info("web LoadBdpOverride");
		tasks.taskBdpOverrideLoad();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskHistDataLoad")
	@ResponseBody
	public Result taskLoadHistData() {
		log.info("web LoadHistData");
		tasks.taskHistDataLoad();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskCurrenciesDataLoad")
	@ResponseBody
	public Result taskLoadCurrenciesData() {
		log.info("web LoadCurrenciesData");
		tasks.taskCurrenciesDataLoad();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskBondsLoad")
	@ResponseBody
	public Result taskLoadBonds() {
		log.info("web LoadBonds");
		tasks.taskBondsLoad();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskSubscriptionCheck")
	@ResponseBody
	public Result taskMsgSubscription() {
		log.info("web MsgSubscription");
		tasks.taskSubscriptionCheck();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskJobbers")
	@ResponseBody
	public Result taskMsgJobbers() {
		log.info("web MsgJobbers");
		tasks.taskJobbers();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskQuotes")
	@ResponseBody
	public Result taskMsgQuotes() {
		log.info("web MsgQuotes");
		tasks.taskQuotes();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskBonds")
	@ResponseBody
	public Result taskMsgBonds() {
		log.info("web MsgBonds");
		tasks.taskBonds();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskQuotesRus")
	@ResponseBody
	public Result taskMsgQuotesRus() {
		log.info("web MsgQuotesRus");
		tasks.taskQuotesRus();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskFullermoneyAudio")
	@ResponseBody
	public Result taskMsgFullermoneyAudio() {
		log.info("web MsgFullermoneyAudio");
		tasks.taskFullermoneyAudio();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskQuotesUsa")
	@ResponseBody
	public Result taskMsgQuotesUsa() {
		log.info("web MsgQuotesUsa");
		tasks.taskQuotesUsa();
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/taskAnalytics")
	@ResponseBody
	public Result taskMsgAnalytics() {
		log.info("web MsgAnalytics");
		tasks.taskAnalytics();
		return Result.SUCCESS;
	}

}
