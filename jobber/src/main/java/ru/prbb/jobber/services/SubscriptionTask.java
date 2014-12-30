/**
 * 
 */
package ru.prbb.jobber.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SubscriptionItem;
import ru.prbb.jobber.repo.BloombergServicesJ;
import ru.prbb.jobber.repo.SubscriptionDao;

/**
 * Подписка блумберга
 * 
 * @author RBr
 */
@Configuration
@EnableScheduling
public class SubscriptionTask {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SubscriptionDao dao;
	@Autowired
	private BloombergServicesJ bs;

	/**
	 * Проверка статуса подписок.<br>
	 * Запуск и остановка при его изменении.
	 */
	@Scheduled(initialDelay = 2000, fixedRate = 60 * 1000)
	public void execute() {
		log.debug("Subscriptions execute");

		List<SubscriptionItem> list = dao.getSubscriptions();
		List<SubscriptionItem> listStart = new ArrayList<>(list.size());
		List<SubscriptionItem> listStop = new ArrayList<>(list.size());
		for (SubscriptionItem subscription : list) {
			if (subscription.isRunning()) {
				listStart.add(subscription);
			} else {
				listStop.add(subscription);
			}
		}

		bs.subscriptionStop(listStop);

		for (SubscriptionItem item : listStart) {
			List<SecurityItem> securities = dao.subsGetSecs(item.getId());
			bs.subscriptionStart(item, securities);
		}

		for (SubscriptionItem item : listStart) {
			List<String[]> data = bs.subscriptionData(item);
			dao.subsUpdate(data);
		}
	}

	/*
	 * Цель перезапуска
	 * 1. блумберг изредка сходил сума и переставал отдавать данные по тикерам избирательно
	 * 2. обновление указателей на активный контракт для фьючей
	 */
	@PreDestroy
	@Scheduled(cron = "30 00 03 * * ?")
	public void stop() {
		log.info("Subscriptions stop");

		List<SubscriptionItem> subscriptions = dao.getSubscriptions();
		bs.subscriptionStop(subscriptions);
	}

}
