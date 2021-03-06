/**
 * 
 */
package ru.prbb.agent.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.agent.domain.SubscriptionServer;

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

	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private SubscriptionServerRepository servers;

	private CloseableHttpClient httpClient;

	private boolean isShowError = true;

	private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

		@Override
		public String handleResponse(HttpResponse response) {
			try {
				StatusLine statusLine = response.getStatusLine();
				int status = statusLine.getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return (entity != null) ? EntityUtils.toString(entity) : "";
				} else {
					String reason = statusLine.getReasonPhrase();
					log.error("Jobber response status: " + status + ' ' + reason);
				}
			} catch (Exception e) {
				log.error("Jobber response exception: " + e.getMessage());
			}
			return "";
		}

	};

	@PostConstruct
	public void init() {
		log.info("Init HttpClient");
		httpClient = HttpClients.createDefault();
	}

	@PreDestroy
	public void done() {
		log.info("Done HttpClient");
		try {
			if (httpClient != null)
				httpClient.close();
		} catch (IOException e) {
			log.error("Close HttpClient", e);
		}
	}

	@Scheduled(fixedDelay = 5 * 1000)
	public void execute() {
		if (httpClient == null) {
			if (isShowError) {
				isShowError = false;
				log.error("HttpClient is null");
			}
			return;
		}

		SubscriptionServer server = servers.next();

		log.info("Execute check {}", server);

		try {
			server.setStatus("Выполняется запрос к серверу");
			String requestBody = httpClient.execute(server.getUriRequest(), responseHandler);

			if (null == requestBody || requestBody.isEmpty()) {
				server.setStatus("Ожидание");
				return;
			}

			log.debug(requestBody);
			@SuppressWarnings("unchecked")
			List<Object> request = (List<Object>) mapper.readValue(requestBody, ArrayList.class);

			if (request == null) {
				server.setStatus("Ожидание");
				return;
			}

			log.info("Process task " + request);

		} catch (Exception e) {
			log.error("Execute HTTP " + e.getMessage());
			server.setStatus(e.toString());
		}
	}
	
	
	/**
	 * Зарегистрированные подписки<br>
	 * id -> thread
	 */
	private final Map<ThreadId, SubscriptionThread> threads = new HashMap<>();

	public String start(Integer id, String name, String[] securities, String uriCallback) {
		synchronized (threads) {
			ThreadId threadId = new ThreadId(id, uriCallback);
			SubscriptionThread thread = threads.get(threadId);
			if (null == thread) {
				thread = new SubscriptionThread(threadId);
				try {
					thread.startSession(securities);
					threads.put(threadId, thread);
					return "STARTED";
				} catch (Exception e) {
					log.error("Start session failed", e);
					return "ERROR:" + e.getMessage();
				}
			} else {
				return "IS ALREADY RUNNING";
			}
		}
	}

	public String stop(Integer id, String uriCallback) {
		synchronized (threads) {
			ThreadId threadId = new ThreadId(id, uriCallback);
			SubscriptionThread thread = threads.get(threadId);
			if (null == thread) {
				return "NOT FOUND";
			} else {
				try {
					thread.stopSession();
					thread.join();
					return "STOPPED";
				} catch (Exception e) {
					log.error("Stop session failed", e);
					return "ERROR:" + e.getMessage();
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

	private class ThreadId {

		private Integer id;
		private String uriCallback;

		public ThreadId(Integer id, String uriCallback) {
			this.id = id;
			this.uriCallback = uriCallback;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ThreadId other = (ThreadId) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (uriCallback == null) {
				if (other.uriCallback != null)
					return false;
			} else if (!uriCallback.equals(other.uriCallback))
				return false;
			return true;
		}

		private SubscriptionService getOuterType() {
			return SubscriptionService.this;
		}
	}

	private class SubscriptionThread extends Thread implements ResponseHandler<String> {

		private final ThreadId threadId;

		private volatile boolean isRun = true;

		private Session session;


		public SubscriptionThread(ThreadId threadId) {
			super("Subscription #" + threadId.id);
			this.threadId = threadId;
			setDaemon(true);
		}

		public void startSession(String[] securities) {
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

			log.info("Connecting to " + sessionOptions.getServerHost() + ":" + sessionOptions.getServerPort());
			session = new Session(sessionOptions);

			try {
				if (!session.start()) {
					throw new RuntimeException("Failed to start session.");
				}

				if (!session.openService("//blp/mktdata")) {
					throw new RuntimeException("Failed to open //blp/mktdata");
				}

				session.subscribe(subscriptions);
			} catch (IOException e) {
				throw new RuntimeException("Error starting subscription:" + e.getMessage(), e);
			} catch (InterruptedException e) {
				throw new RuntimeException("Error starting subscription:" + e.getMessage(), e);
			}

			start();

			log.info("Subscribe started " + getName());
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

						//data.setLength(data.length() - 1);

						List<NameValuePair> nvps = new ArrayList<>();
						nvps.add(new BasicNameValuePair("id", threadId.id.toString()));
						nvps.add(new BasicNameValuePair("data", data.toString()));

						HttpPost httpPost = new HttpPost(threadId.uriCallback);
						httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

						String response = httpclient.execute(httpPost, this);
						if (!"OK".equals(response))
							log.error(response);
					}
				}
			} catch (IOException e) {
				log.error("Execute HTTP Post", e);
			} catch (Exception e) {
				log.error("Get next session event", e);
			} finally {
				try {
					session.stop();
				} catch (InterruptedException e) {
					log.error("Stop session", e);
				}
				log.info("Stopped " + getName());
				threads.put(threadId, null);
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
