/**
 * 
 */
package ru.prbb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.bloomberg.SubscriptionThread;
import ru.prbb.domain.SubscriptionItem;
import ru.prbb.service.SubscriptionDao;

/**
 * Подписка блумберга
 * 
 * @author RBr
 * 
 */
@Service
public class SubscriptionTask {
	private static final Log log = LogFactory.getLog(SubscriptionTask.class);

	@Autowired
	private SubscriptionDao dao;

	@PreDestroy
	public void destroy() {
		stop();
	}

	/**
	 * Зарегистрированные подписки<br>
	 * id -> thread
	 */
	private final Map<Long, SubscriptionThread> threads = new HashMap<Long, SubscriptionThread>();

	/**
	 * Проверка статуса подписок.<br>
	 * Запуск и остановка при его изменении.
	 */
	@Scheduled(fixedRate = 1000)
	public void execute() {
		List<SubscriptionItem> list = dao.getSubscriptions();
		for (SubscriptionItem sub : list) {
			final Long id = sub.getId();

			if (!threads.containsKey(id)) {
				threads.put(id, null);
				log.info("Register subscription id:" + id + " - " + sub.getName() + ", " + sub.getStatus());
			}

			final SubscriptionThread thread = threads.get(id);
			if (sub.isRunning()) {
				if (null == thread) {
					final SubscriptionThread st = new SubscriptionThread(id, dao);
					if (st.start()) {
						threads.put(id, st);
					} else {
						// TODO Подсчитать количество неудачных запусков
					}
				}
			} else {
				if (null != thread) {
					threads.put(id, null);
					thread.stop();
				}
			}
		}
	}

	@Scheduled(cron = "30 00 03 * * ?")
	public void stop() {
		log.info("Stop all subscription threads");
		for (SubscriptionThread thread : threads.values()) {
			if (null != thread) {
				thread.stop();
			}
		}
		threads.clear();
	}

}
