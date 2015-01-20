/**
 * 
 */
package ru.prbb.agent.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * Зарегистрированные подписки<br>
	 * id -> thread
	 */
	private final Map<Long, SubscriptionThread> threads = new HashMap<Long, SubscriptionThread>();

	//private ThreadGroup group = new ThreadGroup("Subscriptions");

	public SubscriptionService() {
		//group.setDaemon(true);
	}

	public String start(Long id, String[] securities) {
		synchronized (threads) {
			SubscriptionThread thread = threads.get(id);
			if (null == thread) {
				thread = new SubscriptionThread(id);
				try {
					if (thread.startSession(securities)) {
						threads.put(id, thread);
						return "STARTED";
					} else {
						return "ERROR";
					}
				} catch (Exception e) {
					log.error("Start session failed", e);
					return "ERROR\n" + e.toString();
				}
			} else {
				return "IS ALREADY RUNNING";
			}
		}
	}

	public String stop(Long id) {
		synchronized (threads) {
			SubscriptionThread thread = threads.get(id);
			if (null == thread) {
				return "NOT FOUND";
			} else {
				try {
					thread.stopSession();
					return "STOPPING";
				} catch (Exception e) {
					log.error("Stop session failed", e);
					return "ERROR\n" + e.toString();
				}
			}
		}
	}

	public String getData(Long id, boolean isClean) {
		synchronized (threads) {
			SubscriptionThread thread = threads.get(id);
			if (null == thread) {
				return "NOT FOUND";
			} else {
				try {
					return thread.getData(isClean);
				} catch (Exception e) {
					log.error("Get data session failed", e);
					return "ERROR\n" + e.toString();
				}
			}
		}
	}

	@PreDestroy
	public void stop() {
		log.info("Stop all subscription threads");
		for (SubscriptionThread thread : threads.values()) {
			if (null != thread) {
				try {
					thread.stopSession();
				} catch (Exception e) {
					log.error("Stop session failed", e);
				}
			}
		}
	}

	private class SubscriptionThread extends Thread {

		public final Long id;

		private volatile boolean isRun = true;

		private Session session;
		private StringBuilder data = new StringBuilder();

		public SubscriptionThread(Long id) {
			super("Subscription #" + id);
			//super(group, "Subscription #" + id);
			setDaemon(true);
			this.id = id;
		}

		public String getData(boolean isClean) {
			synchronized (data) {
				String res = data.toString();
				if (isClean) {
					data.setLength(0);
				}
				return res;
			}
		}

		public boolean startSession(String[] securities) {
			SessionOptions sessionOptions = new SessionOptions();
			sessionOptions.setServerHost("localhost");
			sessionOptions.setServerPort(8194);

			StringBuilder sb = new StringBuilder();
			SubscriptionList subscriptions = new SubscriptionList();
			for (String security : securities) {
				sb.append(security).append(',');

				Subscription subscription = new Subscription(security,
						"LAST_PRICE,RT_PX_CHG_PCT_1D", "interval=25.0", new CorrelationID(security));
				subscriptions.add(subscription);
			}
			sb.setLength(sb.length() - 1);
			//log.info("Subscription: " + sb);

			log.debug("Connecting to " + sessionOptions.getServerHost() + ":" + sessionOptions.getServerPort());
			session = new Session(sessionOptions);

			try {
				if (!session.start()) {
					log.error("Failed to start session.");
					return false;
				}

				if (!session.openService("//blp/mktdata")) {
					log.error("Failed to open //blp/mktdata");
					return false;
				}

				log.debug("Subscribing...");
				session.subscribe(subscriptions);
			} catch (Exception e) {
				log.error("Error starting subscription", e);
				return false;
			}

			start();

			log.info("Start " + getName());

			return true;
		}

		public void stopSession() {
			log.info("Send STOP " + getName());
			isRun = false;
		}

		@Override
		public void run() {
			try {
				while (isRun) {
					Event event = session.nextEvent();
					EventType eventType = event.eventType();
					if (Event.EventType.SUBSCRIPTION_DATA == eventType
							|| Event.EventType.SUBSCRIPTION_STATUS == eventType) {
						for (Message msg : event) {
							if ("SubscriptionFailure".equals(msg.messageType().toString())) {
								String description = msg.getElement("reason").getElementAsString("description");
								log.error("SubscriptionFailure:" + description);
								continue;
							}

							if (msg.hasElement("LAST_PRICE") && msg.hasElement("RT_PX_CHG_PCT_1D")) {
								String security_code = msg.correlationID().object().toString();
								String last_price = getElementAsString(msg, "LAST_PRICE");
								String last_chng = getElementAsString(msg, "RT_PX_CHG_PCT_1D");
								String update = security_code + '\t' + last_price + '\t' + last_chng;
								synchronized (data) {
									if (data.length() > 32 * 1024) {
										data.setLength(0);
									}
									data.append(update).append('\n');
								}
								log.trace("subsUpdate security_code:" + security_code
										+ ", last_price:" + last_price + ", last_chng:" + last_chng);
							}
						}
					}
				}
			} catch (Exception e) {
				log.error("Get next session event", e);
			} finally {
				threads.put(id, null);
				try {
					session.stop();
				} catch (InterruptedException e) {
					log.error("Stop session", e);
				}
				log.info("Stopped " + getName());
			}
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
