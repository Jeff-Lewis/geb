/**
 * 
 */
package ru.prbb.jobber.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SubscriptionItem;
import ru.prbb.jobber.repo.BloombergDao;
import ru.prbb.jobber.repo.BloombergServicesJ;
import ru.prbb.jobber.repo.SendingDao;
import ru.prbb.jobber.repo.SubscriptionDao;

/**
 * Запланированные задачи Jobber
 * 
 * @author RBr
 */
//@Configuration
//@EnableScheduling
public class ScheduledTasks {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServicesJ bs;

	@Autowired
	private BloombergDao daoBloomberg;
	@Autowired
	private SubscriptionDao daoSubscription;
	@Autowired
	private SendingDao daoSending;

	/**
	 * Проверка статуса подписок.<br>
	 * Запуск и остановка при его изменении.
	 */
	//@Scheduled(initialDelay = 2000, fixedRate = 10 * 1000)
	public void subscriptionExecute() {
		log.debug("Subscriptions execute");

		List<SubscriptionItem> list = daoSubscription.getSubscriptions();
		for (SubscriptionItem subscription : list) {
			if (subscription.isRunning()) {
				List<SecurityItem> securities = daoSubscription.subsGetSecs(subscription.getId());
//				bs.subscriptionStart(subscription, securities);
			} else {
//				bs.subscriptionStop(subscription);
			}
		}

	}

	/*
	 * Цель перезапуска
	 * 1. блумберг изредка сходил сума и переставал отдавать данные по тикерам избирательно
	 * 2. обновление указателей на активный контракт для фьючей
	 */
	//@Scheduled(cron = "30 00 03 * * *")
	public void subscriptionStop() {
		log.info("Subscriptions stop");

		List<SubscriptionItem> subscriptions = daoSubscription.getSubscriptions();
		for (SubscriptionItem subscription : subscriptions) {
//			bs.subscriptionStop(subscription);
		}
	}
}
