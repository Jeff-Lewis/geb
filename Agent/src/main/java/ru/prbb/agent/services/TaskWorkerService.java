package ru.prbb.agent.services;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ruslan
 */
@Service
public class TaskWorkerService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServices bs;
	@Autowired
	private SubscriptionService ss;

	private StringBuilder status = new StringBuilder("<h2>Сервис остановлен.</h2>");
	private WorkThread wt;
	private boolean running;

	public synchronized void start(String host) throws URISyntaxException {
		if (wt != null) {
			throw new IllegalStateException("The service already running");
		}

		wt = new WorkThread(host);
		wt.start();
	}

	public synchronized void stop() {
		if (wt == null) {
			throw new IllegalStateException("The service does not running");
		}

		status = new StringBuilder("<h2>Сервис останавливается...</h2>");

		try {
			running = false;
			wt.join();
		} catch (InterruptedException ignore) {
		}

		status = new StringBuilder("<h2>Сервис остановлен.</h2>");
	}

	public boolean isRunning() {
		return (wt != null) && running;
	}

	public String getStatus() {
		return status.toString();
	}

	private class WorkThread extends Thread {

		private URI server;
		private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					String reason = response.getStatusLine().getReasonPhrase();
					throw new ClientProtocolException("Unexpected response status: " + status + ' ' + reason);
				}
			}

		};

		public WorkThread(String host) throws URISyntaxException {
			super("WorkThread for " + host);
			setDaemon(true);
			server = new URI(host);
		}

		@Override
		public void run() {
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
				HttpGet httpGet = new HttpGet(server);
				log.debug("Executing request " + httpGet.getRequestLine());

				status = new StringBuilder("<h2>Сервис запущен.</h2><br/>");

				running = true;
				do {
					String responseBody = httpClient.execute(httpGet, responseHandler);

					processTask(responseBody);

					try {
						Thread.sleep(1500);
					} catch (InterruptedException ignore) {
					}
				} while (running);
			} catch (Exception e) {
				log.error("WorkThread.run", e);
				status.append("Ошибка: ").append(e.getMessage());
			} finally {
				wt = null;
			}
		}
	}

	private final ObjectMapper m = new ObjectMapper();

	private String serialize(Object object) {
		if (null == object)
			return null;
		try {
			StringWriter w = new StringWriter();
			m.writeValue(w, object);
			return w.toString();
		} catch (Exception e) {
			log.error("serialize", e);
		}
		throw new RuntimeException("serialize");
	}

	private Object deserialize(String content) {
		try {
			log.info(content);
			return m.readValue(content, Object.class);
		} catch (Exception e) {
			log.error("deserialize", e);
		}
		throw new RuntimeException("deserialize");
	}

	private String[] toArray(Object obj) {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) obj;
		return list.toArray(new String[list.size()]);
	}

	private String processTask(String responseBody) {
		if (responseBody.isEmpty())
			return null;

		@SuppressWarnings("unchecked")
		Map<String, Object> req = (Map<String, Object>) deserialize(responseBody);

		String type = (String) req.get("type");
		log.info("Process task " + type);

		if ("executeBdsRequest".equals(type)) {
			String name = (String) req.get("name");
			String[] securities = toArray(req.get("securities"));
			String[] fields = toArray(req.get("fields"));

			Map<String, Object> result = bs.executeBdsRequest(name, securities, fields);
			return serialize(result);
		}

		if ("executeReferenceDataRequest".equals(type)) {
			String name = (String) req.get("name");
			String[] securities = toArray(req.get("securities"));
			String[] fields = toArray(req.get("fields"));

			Map<String, Map<String, String>> result = bs.executeReferenceDataRequest(name, securities, fields);
			return serialize(result);
		}

		if ("executeHistoricalDataRequest".equals(type)) {
			String name = (String) req.get("name");
			String startDate = (String) req.get("dateStart");
			String endDate = (String) req.get("dateEnd");
			String[] securities = toArray(req.get("securities"));
			String[] fields = toArray(req.get("fields"));
			String[] currencies = toArray(req.get("currencies"));

			Map<String, Map<String, Map<String, String>>> result = bs.executeHistoricalDataRequest(name, startDate, endDate, securities, fields, currencies);
			return serialize(result);
		}

		if ("executeAtrLoad".equals(type)) {
			String name = (String) req.get("name");
			String startDate = (String) req.get("dateStart");
			String endDate = (String) req.get("dateEnd");
			String[] securities = toArray(req.get("securities"));
			String maType = (String) req.get("maType");
			Integer taPeriod = (Integer) req.get("taPeriod");
			String period = (String) req.get("period");
			String calendar = (String) req.get("calendar");

			List<Map<String, Object>> result = bs.executeLoadAtrRequest(name, startDate, endDate, securities, maType, taPeriod, period, calendar);
			return serialize(result);
		}

		if ("executeBdpOverrideLoad".equals(type)) {
			String name = (String) req.get("name");
			String[] cursecs = toArray(req.get("cursecs"));
			String[] currencies = toArray(req.get("currencies"));

			Map<String, Map<String, String>> result = bs.executeBdpOverrideLoad(name, cursecs, currencies);
			return serialize(result);
		}

		if ("SubscriptionStart".equals(type)) {
			Integer id = (Integer) req.get("id");
			String name = (String) req.get("name");
			String[] securities = toArray(req.get("securities"));
			String uriCallback = (String) req.get("uriCallback");

			Object result = ss.start(id, securities, uriCallback);
			return serialize(result);
		}

		if ("SubscriptionStop".equals(type)) {
			Integer id = (Integer) req.get("id");
			String name = (String) req.get("name");

			Object result = ss.stop(id);
			return serialize(result);
		}

		return null;
	}
}
