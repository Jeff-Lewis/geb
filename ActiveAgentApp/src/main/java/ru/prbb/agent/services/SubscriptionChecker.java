package ru.prbb.agent.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.prbb.agent.model.SecurityItem;
import ru.prbb.agent.model.SubscriptionItem;
import ru.prbb.agent.model.SubscriptionServer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Подписка блумберга
 * 
 * @author RBr
 */
class SubscriptionChecker implements Runnable {

	private final Logger log = LoggerFactory.getLogger(getClass());

	final ObjectMapper mapper;

	final SubscriptionServer server;

	private final List<SubscriptionItem> subs = new ArrayList<>();
	private final List<SubscriptionThread> subsThreads = new ArrayList<>();

	private boolean isTerminate = false;

	public SubscriptionChecker(ObjectMapper mapper, SubscriptionServer server) {
		this.mapper = mapper;
		this.server = server;
	}

	public void terminate() {
		isTerminate = true;
		for (SubscriptionThread thread : subsThreads) {
			thread.stopSubs();
		}
		subsThreads.clear();
	}

	@Override
	public void run() {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			log.debug("Execute check Subscription {}", server);
			server.setStatus("Выполняется запрос к серверу");

			if (isTerminate) {
				return;
			}

			String requestBody = httpClient.execute(server.getUriRequestList(), responseHandler);

			if (null == requestBody || requestBody.isEmpty()) {
				server.setStatus("Ожидание");
				return;
			}

			log.info(requestBody);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> request = mapper.readValue(requestBody, ArrayList.class);

			if (request == null) {
				server.setStatus("Ожидание");
				return;
			}

			for (Map<String, Object> map : request) {
				Long id = Long.valueOf(String.valueOf(map.get("id")));
				SubscriptionItem item = new SubscriptionItem(id);
				item.setName(String.valueOf(map.get("name")));
				item.setComment(String.valueOf(map.get("comment")));
				item.setStatus(String.valueOf(map.get("status")));

				int idx = subs.indexOf(item);
				if (idx < 0) {
					idx = 0;
					subs.add(idx, item);

					if (item.isRunning()) {
						start(httpClient, item);
					}
				} else {
					SubscriptionItem item1 = subs.get(idx);
					item1.setName(item.getName());
					item1.setComment(item.getComment());

					if (item1.isStopped() && item.isStopped()) {
						continue;
					}

					if (item1.isStopped() && item.isRunning()) {
						start(httpClient, item1);
						continue;
					}

					if (item1.isRunning() && item.isRunning()) {
						start(httpClient, item1);
						continue;
					}

					if (item1.isRunning() && item.isStopped()) {
						stop(httpClient, item1);
						continue;
					}
				}
			}
		} catch (Exception e) {
			log.error("Execute HTTP " + e.getMessage());
			server.setStatus(e.toString());
		}
	}

	private void start(HttpClient httpClient, SubscriptionItem item) throws Exception {
		log.info("Get Subscription securities for {}", item.getName());
		server.setStatus("Выполняется запрос к серверу");

		String requestBody = httpClient.execute(server.getUriRequestSecs(item.getId()), responseHandler);

		if (null == requestBody || requestBody.isEmpty()) {
			server.setStatus("Ожидание");
			return;
		}

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = mapper.readValue(requestBody, ArrayList.class);
		Set<SecurityItem> secs = new HashSet<>(list.size());
		for (Map<String, Object> map : list) {
			SecurityItem sec = new SecurityItem();
			sec.setId(Long.valueOf(String.valueOf(map.get("id"))));
			sec.setCode(String.valueOf(map.get("code")));
			sec.setName(String.valueOf(map.get("name")));
			secs.add(sec);
		}

		if (item.isRunning() && secs.equals(item.getSecurities())) {
			return;
		}

		SubscriptionThread subThread = null;

		for (SubscriptionThread thread : subsThreads) {
			if (item.equals(thread.getItem())) {
				subThread = thread;
				break;
			}
		}

		item.getSecurities().clear();
		item.getSecurities().addAll(secs);

		if (subThread != null) {
			subThread.updateSubs(secs);
		} else {
			item.stop();
			subThread = new SubscriptionThread(this, item);
			subThread.startSubs(secs);
			subsThreads.add(subThread);
		}
	}

	private void stop(HttpClient httpClient, SubscriptionItem item) throws Exception {
		log.info("Get Subscription securities for {}", item.getName());
		server.setStatus("Выполняется запрос к серверу");

		String requestBody = httpClient.execute(server.getUriRequestStop(item.getId()), responseHandler);

		if (null == requestBody || requestBody.isEmpty()) {
			server.setStatus("Ожидание");
			return;
		}

		for (SubscriptionThread thread : subsThreads) {
			if (item.equals(thread.getItem())) {
				thread.stopSubs();
				subsThreads.remove(item);
				break;
			}
		}
	}

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

}
