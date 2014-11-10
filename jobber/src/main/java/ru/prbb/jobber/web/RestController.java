package ru.prbb.jobber.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.prbb.jobber.services.ScheduledTasks;

/**
 * @author RBr
 */
@Controller
@RequestMapping(value = "/rest")
public class RestController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ScheduledTasks tasks;

	@RequestMapping(value = "/BdsLoad")
	public String taskBdsLoad(Model model) {
		log.info("web BdsLoad");
		tasks.taskBdsLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/FuturesLoad")
	public String taskFuturesLoad(Model model) {
		log.info("web FuturesLoad");
		tasks.taskFuturesLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/QuotesLoad")
	public String taskQuotesLoad(Model model) {
		log.info("web QuotesLoad");
		tasks.taskQuotesLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/AtrLoad")
	public String taskAtrLoad(Model model) {
		log.info("web AtrLoad");
		tasks.taskAtrLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/BdpOverrideLoad")
	public String taskBdpOverrideLoad(Model model) {
		log.info("web BdpOverrideLoad");
		tasks.taskBdpOverrideLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/HistDataLoad")
	public String taskHistDataLoad(Model model) {
		log.info("web HistDataLoad");
		tasks.taskHistDataLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/CurrenciesDataLoad")
	public String taskCurrenciesDataLoad(Model model) {
		log.info("web CurrenciesDataLoad");
		tasks.taskCurrenciesDataLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/BondsLoad")
	public String taskBondsLoad(Model model) {
		log.info("web BondsLoad");
		tasks.taskBondsLoad();
		return "redirect:/";
	}

}
