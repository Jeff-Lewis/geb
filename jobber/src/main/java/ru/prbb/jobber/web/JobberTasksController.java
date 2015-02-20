package ru.prbb.jobber.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.prbb.jobber.services.ScheduledTasks;

/**
 * Ручной запуск задач Жобера
 * 
 * @author RBr
 */
@Controller
@RequestMapping(value = "/JobberTasks")
public class JobberTasksController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ScheduledTasks tasks;

	@RequestMapping(value = "/LoadBds")
	public String taskLoadBds(Model model) {
		log.info("web LoadBds");
		tasks.taskBdsLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/LoadFutures")
	public String taskLoadFutures(Model model) {
		log.info("web LoadFutures");
		tasks.taskFuturesLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/LoadQuotes")
	public String taskLoadQuotes(Model model) {
		log.info("web LoadQuotes");
		tasks.taskQuotesLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/LoadAtr")
	public String taskLoadAtr(Model model) {
		log.info("web LoadAtr");
		tasks.taskAtrLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/LoadBdpOverride")
	public String taskLoadBdpOverride(Model model) {
		log.info("web LoadBdpOverride");
		tasks.taskBdpOverrideLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/LoadHistData")
	public String taskLoadHistData(Model model) {
		log.info("web LoadHistData");
		tasks.taskHistDataLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/LoadCurrenciesData")
	public String taskLoadCurrenciesData(Model model) {
		log.info("web LoadCurrenciesData");
		tasks.taskCurrenciesDataLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/LoadBonds")
	public String taskLoadBonds(Model model) {
		log.info("web LoadBonds");
		tasks.taskBondsLoad();
		return "redirect:/";
	}

	@RequestMapping(value = "/MsgSubscription")
	public String taskMsgSubscription() {
		log.info("web MsgSubscription");
		tasks.taskSubscription();
		return "redirect:/";
	}

	@RequestMapping(value = "/MsgJobbers")
	public String taskMsgJobbers() {
		log.info("web MsgJobbers");
		tasks.taskJobbers();
		return "redirect:/";
	}

	@RequestMapping(value = "/MsgQuotes")
	public String taskMsgQuotes() {
		log.info("web MsgQuotes");
		tasks.taskQuotes();
		return "redirect:/";
	}

	@RequestMapping(value = "/MsgBonds")
	public String taskMsgBonds() {
		log.info("web MsgBonds");
		tasks.taskBonds();
		return "redirect:/";
	}

	@RequestMapping(value = "/MsgQuotesRus")
	public String taskMsgQuotesRus() {
		log.info("web MsgQuotesRus");
		tasks.taskQuotesRus();
		return "redirect:/";
	}

	@RequestMapping(value = "/MsgFullermoneyAudio")
	public String taskMsgFullermoneyAudio() {
		log.info("web MsgFullermoneyAudio");
		tasks.taskFullermoneyAudio();
		return "redirect:/";
	}

	@RequestMapping(value = "/MsgQuotesUsa")
	public String taskMsgQuotesUsa() {
		log.info("web MsgQuotesUsa");
		tasks.taskQuotesUsa();
		return "redirect:/";
	}

	@RequestMapping(value = "/MsgAnalytics")
	public String taskMsgAnalytics() {
		log.info("web MsgAnalytics");
		tasks.taskAnalytics();
		return "redirect:/";
	}

}
