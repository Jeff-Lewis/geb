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

import ru.prbb.jobber.domain.SubscriptionItem;
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

	/**
	 * Проверка статуса подписок.<br>
	 * Запуск и остановка при его изменении.
	 */
	@Scheduled(initialDelay = 2000, fixedRate = 10000)
	public void execute() {
		log.info("Subscriptions execute");

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
		dao.stop(listStop);
		dao.start(listStart);
	}

	@PreDestroy
	@Scheduled(cron = "30 00 03 * * ?")
	public void stop() {
		log.info("Subscriptions stop");

		List<SubscriptionItem> subscriptions = dao.getSubscriptions();
		dao.stop(subscriptions);
	}

}
