/**
 * 
 */
package ru.prbb.agent.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.agent.dao.DBManager;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Event.EventType;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;
import com.bloomberglp.blpapi.Subscription;
import com.bloomberglp.blpapi.SubscriptionList;

/**
 * Подписка блумберга
 * 
 * @author RBr
 */
@Service
public class SubscriptionService {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	private BloombergServices bs;

	@Autowired
	private DBManager dbm;

	/**
	 * Зарегистрированные подписки<br>
	 * id -> thread
	 */
	private final Map<Long, SubscriptionThread> threads = new HashMap<Long, SubscriptionThread>();

	/**
	 * Проверка статуса подписок.<br>
	 * Запуск и остановка при его изменении.
	 */
	//@Scheduled(cron = "* * * * * ?")
	public void execute() {
		List<Map<String, Object>> subs = dbm.subsGetList();

		if (threads.isEmpty()) {
			for (Map<String, Object> map : subs) {
				final Long id = new Long(map.get("id_subscr").toString());
				final boolean status = convertStatus(map.get("subscription_status"));

				threads.put(id, null);
				log.info("Register subscription id:" + id + ", status:" + status);
			}
		}

		for (Map<String, Object> map : subs) {
			final Long id = new Long(map.get("id_subscr").toString());
			final boolean status = convertStatus(map.get("subscription_status"));

			SubscriptionThread thread = threads.get(id);
			if (status) {
				if (null == thread) {
					thread = new SubscriptionThread(id);
					if (thread.start()) {
						threads.put(id, thread);
					} else {
						// TODO Подсчитать количество неудачных запусков
					}
				}
			} else {
				if (null != thread) {
					thread.stop();
				}
			}
		}
	}

	@PreDestroy
	@Scheduled(cron = "30 00 03 * * ?")
	public void stop() {
		log.info("Stop all subscription threads");
		for (SubscriptionThread thread : threads.values()) {
			if (null != thread) {
				thread.stop();
			}
		}
	}

	private boolean convertStatus(Object object) {
		final String status = object.toString();
		if ("Running".equals(status))
			return true;
		if ("Stopped".equals(status))
			return false;
		throw new RuntimeException("Unknown subscription status: " + status);
	}

	private class SubscriptionThread implements Runnable {

		public final Long id;

		private volatile boolean isRun = true;

		private Session session;
		private Thread thread;

		public SubscriptionThread(Long id) {
			this.id = id;
		}

		public boolean start() {
			final List<Map<String, Object>> secs = dbm.subsGetSecs(id);
			if (secs.isEmpty()) {
				return false;
			}

			final SessionOptions sessionOptions = new SessionOptions();
			sessionOptions.setServerHost("localhost");
			sessionOptions.setServerPort(8194);

			final StringBuilder sb = new StringBuilder();
			final SubscriptionList subscriptions = new SubscriptionList();
			for (Map<String, Object> item : secs) {
				final String s = item.get("security_code").toString();
				sb.append(s).append(',');

				final Subscription subscription = new Subscription(s,
						"LAST_PRICE,RT_PX_CHG_PCT_1D", "interval=25.0", new CorrelationID(s));
				subscriptions.add(subscription);
			}
			sb.setLength(sb.length() - 1);
			log.info("Subscription: " + sb);

			log.debug("Connecting to " + sessionOptions.getServerHost() + ":" + sessionOptions.getServerPort());
			session = new Session(sessionOptions);

			try {
				if (!session.start()) {
					log.fatal("Failed to start session.");
					return false;
				}

				if (!session.openService("//blp/mktdata")) {
					log.fatal("Failed to open //blp/mktdata");
					return false;
				}

				log.debug("Subscribing...");
				session.subscribe(subscriptions);
			} catch (Exception e) {
				log.error(e);
				return false;
			}

			thread = new Thread(this, "Subscription #" + id);
			thread.setDaemon(true);
			thread.start();

			log.info("Start " + thread.getName());

			return true;
		}

		public void stop() {
			log.info("Send STOP " + thread.getName());
			isRun = false;
		}

		@Override
		public void run() {
			try {
				while (isRun) {
					final Event event = session.nextEvent();
					final EventType eventType = event.eventType();
					if (Event.EventType.SUBSCRIPTION_DATA == eventType
							|| Event.EventType.SUBSCRIPTION_STATUS == eventType) {
						for (Message msg : event) {
							if (log.isTraceEnabled()) {
								log.trace("Size msg=" + msg);
								log.trace("Size msg=" + msg.toString().length());
							}

							if ("SubscriptionFailure".equals(msg.messageType().toString())) {
								log.error("SubscriptionFailure:"
										+ msg.getElement("reason").getElementAsString("description"));
								continue;
							}

							if (msg.hasElement("LAST_PRICE") && msg.hasElement("RT_PX_CHG_PCT_1D")) {
								final String security_code = (String) msg.correlationID().object();
								final String last_price = getElementAsString(msg, "LAST_PRICE");
								final String last_chng = getElementAsString(msg, "RT_PX_CHG_PCT_1D");
								dbm.subsUpdate(security_code, last_price, last_chng);
								log.trace("subsUpdate security_code:" + security_code
										+ ", last_price:" + last_price + ", last_chng:" + last_chng);
							}
						}
					}
				}
			} catch (Exception e) {
				log.error(e);
			} finally {
				threads.put(id, null);
				try {
					session.stop();
				} catch (InterruptedException e) {
					log.error(e);
				}
			}
			log.info("Stopped " + thread.getName());
		}

		private String getElementAsString(Message msg, String name) {
			try {
				return msg.getElementAsString(name);
			} catch (Exception e) {
				return null;
			}
		}
	}
}
