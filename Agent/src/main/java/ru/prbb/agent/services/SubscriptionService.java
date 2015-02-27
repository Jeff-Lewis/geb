/**
 * 
 */
package ru.prbb.agent.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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
	private final Map<Integer, SubscriptionThread> threads = new HashMap<>();

	public String start(Integer id, String name, String[] securities, String uriCallback) {
		synchronized (threads) {
			SubscriptionThread thread = threads.get(id);
			if (null == thread) {
				thread = new SubscriptionThread(id, uriCallback);
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

	public String stop(Integer id, String name) {
		synchronized (threads) {
			SubscriptionThread thread = threads.get(id);
			if (null == thread) {
				return "NOT FOUND";
			} else {
				try {
					thread.stopSession();
					thread.join();
					return "STOPPED";
				} catch (Exception e) {
					log.error("Stop session failed", e);
					return "ERROR\n" + e.toString();
				}
			}
		}
	}

	@PreDestroy
	public void destroy() {
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

	private class SubscriptionThread extends Thread implements ResponseHandler<String> {

		private final Integer id;
		private final String uriCallback;

		private volatile boolean isRun = true;

		private Session session;


		public SubscriptionThread(Integer id, String uriCallback) {
			super("Subscription #" + id);
			this.id = id;
			this.uriCallback = uriCallback;
			setDaemon(true);
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
			try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
				while (isRun) {
					Event event = session.nextEvent();
					EventType eventType = event.eventType();
					if (Event.EventType.SUBSCRIPTION_DATA == eventType
							|| Event.EventType.SUBSCRIPTION_STATUS == eventType) {
						StringBuilder data = new StringBuilder();
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

								data.append(update).append('\n');
								log.trace("subsUpdate security_code:" + security_code
										+ ", last_price:" + last_price + ", last_chng:" + last_chng);
							}
						}

						List<NameValuePair> nvps = new ArrayList<>();
						nvps.add(new BasicNameValuePair("id", id.toString()));
						nvps.add(new BasicNameValuePair("data", data.toString()));

						HttpPost httpPost = new HttpPost(uriCallback);
						httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

						String response = httpclient.execute(httpPost, this);
						if (!"OK".equals(response))
							log.error(response);
					}
				}
			} catch (Exception e) {
				log.error("Get next session event", e);
			} finally {
				try {
					session.stop();
				} catch (InterruptedException e) {
					log.error("Stop session", e);
				}
				log.info("Stopped " + getName());
				threads.put(id, null);
			}
		}

		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			String reason = response.getStatusLine().getReasonPhrase();
			if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				return "Unexpected response status: " + status + " " + reason;
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
