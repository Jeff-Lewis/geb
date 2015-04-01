package ru.prbb.agent.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

import ru.prbb.agent.model.SecurityItem;
import ru.prbb.agent.model.SubscriptionItem;

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
class SubscriptionThread extends Thread implements ResponseHandler<String> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private SubscriptionChecker checker;
	private SubscriptionItem item;

	private Session session;

	private volatile boolean isRun = true;

	public SubscriptionThread(SubscriptionChecker checker, SubscriptionItem item) {
		super(item.getName());
		this.checker = checker;
		this.item = item;
		//setDaemon(true);
	}

	public SubscriptionItem getItem() {
		return item;
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
					//nvps.add(new BasicNameValuePair("id", item.getId().toString()));
					nvps.add(new BasicNameValuePair("data", data.toString()));

					HttpPost httpPost = checker.server.getUriRequestUpdate(item.getId());
					httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
					//httpPost.setEntity(new StringEntity(data.toString(), "UTF-8"));

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
			item.stop();
			log.info("Stopped " + getName());
		}
	}

	public void startSubs(Set<SecurityItem> securities) {
		item.stop();

		SessionOptions sessionOptions = new SessionOptions();
		sessionOptions.setServerHost("localhost");
		sessionOptions.setServerPort(8194);

		log.info("Connecting to " + sessionOptions.getServerHost() + ":" + sessionOptions.getServerPort());
		session = new Session(sessionOptions);

		try {
			if (!session.start()) {
				throw new RuntimeException("Failed to start session.");
			}

			if (!session.openService("//blp/mktdata")) {
				throw new RuntimeException("Failed to open //blp/mktdata");
			}

			start();

			session.subscribe(newSubscrList(securities));

			item.start();

			log.info("Subscribe started " + getName());
		} catch (Exception e) {
			try {
				session.stop();
			} catch (InterruptedException ignore) {
				log.error("Session stop", ignore);
			}
			throw new RuntimeException("Error starting subscription:" + e.getMessage(), e);
		}
	}

	private SubscriptionList newSubscrList(Set<SecurityItem> securities) {
		SubscriptionList subscriptions = new SubscriptionList();
		for (SecurityItem securityItem : securities) {
			String code = securityItem.getCode();

			Subscription subscription = new Subscription(code,
					"LAST_PRICE,RT_PX_CHG_PCT_1D", "interval=25.0", new CorrelationID(securityItem.getCode()));

			subscriptions.add(subscription);
		}
		return subscriptions;
	}

	public void updateSubs(Set<SecurityItem> securities) throws IOException {
		session.resubscribe(newSubscrList(securities));
	}

	public void stopSubs() {
		item.stop();
		item.getSecurities().clear();

		log.info("Send STOP " + getName());
		isRun = false;
		try {
			session.stop();
		} catch (InterruptedException e) {
			log.error("Stop session", e);
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
