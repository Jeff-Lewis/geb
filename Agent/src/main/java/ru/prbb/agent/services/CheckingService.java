package ru.prbb.agent.services;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.agent.domain.JobServer;

@Service
public class CheckingService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private BloombergServices bs;
	@Autowired
	private SubscriptionService ss;
	@Autowired
	private JobServerRepository servers;

	private CloseableHttpClient httpClient;

	private boolean isShowError = true;

	private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			StatusLine statusLine = response.getStatusLine();
			int status = statusLine.getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				String reason = statusLine.getReasonPhrase();
				throw new ClientProtocolException("Unexpected response status: " + status + ' ' + reason);
			}
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

		JobServer server = servers.next();

		log.info("Execute check {}", server);

		try {
			server.setStatus("Выполняется запрос к серверу");
			String requestBody = httpClient.execute(server.getUriRequest(), responseHandler);

			if (requestBody.isEmpty()) {
				server.setStatus("Ожидание");
				return;
			}

			log.debug(requestBody);
			@SuppressWarnings("unchecked")
			Map<String, Object> request = (Map<String, Object>) mapper.readValue(requestBody, Object.class);
			
			if (request == null) {
				server.setStatus("Ожидание");
				return;
			}

			String type = (String) request.get("type");
			log.info("Process task " + type);
			
			server.setStatus("Обрабатывается запрос " + type);
			Object resultTask = processTask(type, request);
			
			Map<String, Object> result = new HashMap<>();
			result.put("type", type);
			result.put("hash", request.get("hash"));
			result.put("result", resultTask);

			StringWriter w = new StringWriter();
			mapper.writeValue(w, resultTask);

			server.setStatus("Отправляется ответ " + type);
			httpClient.execute(server.getUriResponse(w.toString()), responseHandler);
			server.setStatus("Выполнен запрос к серверу " + type);
		} catch (IOException e) {
			log.error("Execute HTTP " + e.getMessage());
			server.setStatus(e.toString());
		}

	}

	private String[] toArray(Object obj) {
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) obj;
		return list.toArray(new String[list.size()]);
	}

	private Object processTask(String type, Map<String, Object> request) {
		if ("executeBdsRequest".equals(type)) {
			String name = (String) request.get("name");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Object> result = bs.executeBdsRequest(name, securities, fields);
			return result;
		}

		if ("executeReferenceDataRequest".equals(type)) {
			String name = (String) request.get("name");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Map<String, String>> result = bs.executeReferenceDataRequest(name, securities, fields);
			return result;
		}

		if ("executeHistoricalDataRequest".equals(type)) {
			String name = (String) request.get("name");
			String startDate = (String) request.get("dateStart");
			String endDate = (String) request.get("dateEnd");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, Map<String, String>>> result = bs.executeHistoricalDataRequest(name, startDate,
					endDate, securities, fields, currencies);
			return result;
		}

		if ("executeAtrLoad".equals(type)) {
			String name = (String) request.get("name");
			String startDate = (String) request.get("dateStart");
			String endDate = (String) request.get("dateEnd");
			String[] securities = toArray(request.get("securities"));
			String maType = (String) request.get("maType");
			Integer taPeriod = (Integer) request.get("taPeriod");
			String period = (String) request.get("period");
			String calendar = (String) request.get("calendar");

			List<Map<String, Object>> result = bs.executeLoadAtrRequest(name, startDate, endDate, securities, maType,
					taPeriod, period, calendar);
			return result;
		}

		if ("executeBdpOverrideLoad".equals(type)) {
			String name = (String) request.get("name");
			String[] cursecs = toArray(request.get("cursecs"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, String>> result = bs.executeBdpOverrideLoad(name, cursecs, currencies);
			return result;
		}

		if ("SubscriptionStart".equals(type)) {
			Integer id = (Integer) request.get("id");
			String name = (String) request.get("name");
			String[] securities = toArray(request.get("securities"));
			String uriCallback = (String) request.get("uriCallback");

			Object result = ss.start(id, name, securities, uriCallback);
			return result;
		}

		if ("SubscriptionStop".equals(type)) {
			Integer id = (Integer) request.get("id");
			String name = (String) request.get("name");

			Object result = ss.stop(id, name);
			return result;
		}

		return "Unknow " + type;
	}
}
