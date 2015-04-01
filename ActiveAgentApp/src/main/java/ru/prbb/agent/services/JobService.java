package ru.prbb.agent.services;

import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import ru.prbb.agent.model.JobServer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ruslan
 */
@Service
public class JobService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private TaskScheduler scheduler;

	private final List<JobServer> servers = new ArrayList<>();

	@PostConstruct
	public void init() {
		String hostMy = "172.23.153.164:8080";
		add(hostMy, "/analytics");
		add(hostMy, "/Jobber");
		add(hostMy, "/middleoffice");

		String hostWork = "172.16.15.36:10180";
		add(hostWork, "/analytics");
		add(hostWork, "/Jobber");
		add(hostWork, "/middleoffice");

		String hostTest = "172.16.15.36:10190";
		add(hostTest, "/analytics");
		add(hostTest, "/Jobber");
		add(hostTest, "/middleoffice");

		for (JobServer server : servers) {
			scheduler.scheduleWithFixedDelay(new CheckAgentTask(server), 1000);
		}
	}

	private boolean add(String host, String path) {
		try {
			JobServer server = new JobServer("http://" + host + path + "/AgentTask");
			log.info("Add JobServer {}", server);
			return servers.add(server);
		} catch (URISyntaxException e) {
			log.error("Add " + host, e);
		}

		return false;
	}

	private class CheckAgentTask implements Runnable {

		private final JobServer server;

		public CheckAgentTask(JobServer server) {
			this.server = server;
		}

		@Override
		public void run() {
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
				log.debug("Execute check Job {}", server);
				server.setStatus("Выполняется запрос к серверу");

				String requestBody;
				try (CloseableHttpResponse response = httpClient.execute(server.getUriRequest())) {
					HttpEntity entity = response.getEntity();
					requestBody = (entity != null) ? EntityUtils.toString(entity) : "";
				}

				if (null == requestBody || requestBody.isEmpty()) {
					server.setStatus("Ожидание");
					return;
				}

				log.info(requestBody);
				@SuppressWarnings("unchecked")
				Map<String, Object> request = (Map<String, Object>) mapper.readValue(requestBody, HashMap.class);

				if (request == null) {
					server.setStatus("Ожидание");
					return;
				}

				String type = (String) request.get("type");
				String idTask = request.get("idTask").toString();
				log.info("Process task {} {}", type, idTask);

				try {
					server.setStatus("Обрабатывается запрос " + type);
					log.info("request");
					log.info(request.toString());
					Object resultTask = processTask(type, request);
					log.info("resultTask");
					log.info(resultTask.toString());

					StringWriter w = new StringWriter();
					mapper.writeValue(w, resultTask);
					HttpUriRequest uriRequest = server.getUriResponse(idTask, "OK", w.toString());

					server.setStatus("Отправляется ответ " + type);
					log.info("Отправляется ответ " + type);
					log.info(w.toString());
					try (CloseableHttpResponse response = httpClient.execute(uriRequest)) {
						HttpEntity entity = response.getEntity();
						requestBody = (entity != null) ? EntityUtils.toString(entity) : "";
					}

					server.setStatus("Выполнен запрос к серверу " + type);
				} catch (Exception e) {
					log.error("Execute HTTP " + e.getMessage());
					server.setStatus(e.toString());

					HttpUriRequest uriRequest = server.getUriResponse(idTask, "ERROR:" + e.toString(), "null");

					server.setStatus("Отправляется ошибка " + type);
					try (CloseableHttpResponse response = httpClient.execute(uriRequest)) {
						HttpEntity entity = response.getEntity();
						requestBody = (entity != null) ? EntityUtils.toString(entity) : "";
					}
				}
			} catch (Exception e) {
				log.error("Execute HTTP " + e.getMessage());
				server.setStatus(e.toString());
			}
		}

	}

	@Autowired
	private BloombergServices bs;

	private String[] toArray(Object obj) {
		if (obj != null) {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) obj;
			return list.toArray(new String[list.size()]);
		}
		return null;
	}

	private Object processTask(String type, Map<String, Object> request) {
		String name = (String) request.get("name");

		if ("executeBdsRequest".equals(type)) {
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Object> result = bs.executeBdsRequest(name, securities,
					fields);
			return result;
		}

		if ("executeReferenceDataRequest".equals(type)) {
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Map<String, String>> result = bs
					.executeReferenceDataRequest(name, securities, fields);
			return result;
		}

		if ("executeHistoricalDataRequest".equals(type)) {
			String dateStart = (String) request.get("dateStart");
			String dateEnd = (String) request.get("dateEnd");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, Map<String, String>>> result = bs
					.executeHistoricalDataRequest(name, dateStart, dateEnd,
							securities, fields, currencies);
			return result;
		}

		if ("executeBdhRequest".equals(type)) {
			String dateStart = (String) request.get("dateStart");
			String dateEnd = (String) request.get("dateEnd");
			String period = (String) request.get("period");
			String calendar = (String) request.get("calendar");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, Map<String, String>>> result = bs
					.executeBdhRequest(name, dateStart, dateEnd, period,
							calendar, currencies, securities, fields);
			return result;
		}

		if ("executeBdhEpsRequest".equals(type)) {
			String dateStart = (String) request.get("dateStart");
			String dateEnd = (String) request.get("dateEnd");
			String period = (String) request.get("period");
			String calendar = (String) request.get("calendar");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, Map<String, String>>> result = bs
					.executeBdhEpsRequest(name, dateStart, dateEnd, period,
							calendar, currencies, securities, fields);
			return result;
		}

		if ("executeAtrLoad".equals(type)) {
			String dateStart = (String) request.get("dateStart");
			String dateEnd = (String) request.get("dateEnd");
			String[] securities = toArray(request.get("securities"));
			String maType = (String) request.get("maType");
			Integer taPeriod = (Integer) request.get("taPeriod");
			String period = (String) request.get("period");
			String calendar = (String) request.get("calendar");

			List<Map<String, Object>> result = bs.executeLoadAtrRequest(name,
					dateStart, dateEnd, securities, maType, taPeriod, period,
					calendar);
			return result;
		}

		if ("executeBdpOverrideLoad".equals(type)) {
			String[] cursecs = toArray(request.get("cursecs"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, String>> result = bs
					.executeBdpOverrideLoad(name, cursecs, currencies);
			return result;
		}

		if ("executeCashFlowLoad".equals(type)) {
			String[] _ids = toArray(request.get("ids"));
			Map<String, Long> ids = new HashMap<>(_ids.length);
			for (String s : _ids) {
				String[] a = s.split(";");
				Long id = new Long(a[0]);
				String security = a[1];
				ids.put(security, id);
			}

			String[] _dates = toArray(request.get("dates"));
			Map<String, String> dates = new HashMap<>(_dates.length);
			for (String s : _dates) {
				String[] a = s.split(";");
				String date = a[0];
				String security = a[1];
				dates.put(security, date);
			}

			List<Map<String, Object>> result = bs.executeLoadCashFlowRequest(
					name, ids, dates);
			return result;
		}

		if ("executeCashFlowLoadNew".equals(type)) {
			String[] _ids = toArray(request.get("ids"));
			Map<String, Long> ids = new HashMap<>(_ids.length);
			for (String s : _ids) {
				String[] a = s.split(";");
				Long id = new Long(a[0]);
				String security = a[1];
				ids.put(security, id);
			}

			String[] _dates = toArray(request.get("dates"));
			Map<String, String> dates = new HashMap<>(_dates.length);
			for (String s : _dates) {
				String[] a = s.split(";");
				String date = a[0];
				String security = a[1];
				dates.put(security, date);
			}

			List<Map<String, Object>> result = bs
					.executeLoadCashFlowRequestNew(name, ids, dates);
			return result;
		}

		if ("executeValuesLoad".equals(type)) {
			String[] _ids = toArray(request.get("ids"));
			Map<String, Long> ids = new HashMap<>(_ids.length);
			for (String s : _ids) {
				String[] a = s.split(";");
				Long id = new Long(a[0]);
				String security = a[1];
				ids.put(security, id);
			}

			List<Map<String, Object>> result = bs.executeLoadValuesRequest(
					name, ids);
			return result;
		}

		if ("executeRateCouponLoad".equals(type)) {
			String[] _ids = toArray(request.get("ids"));
			Map<String, Long> ids = new HashMap<>(_ids.length);
			for (String s : _ids) {
				String[] a = s.split(";");
				Long id = new Long(a[0]);
				String security = a[1];
				ids.put(security, id);
			}

			List<Map<String, Object>> result = bs.executeLoadRateCouponRequest(
					name, ids);
			return result;
		}

		if ("executeBdpRequest".equals(type)) {
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Map<String, String>> result = bs.executeBdpRequest(
					name, securities, fields);
			return result;
		}

		if ("executeBdpRequestOverride".equals(type)) {
			String period = (String) request.get("period");
			String over = (String) request.get("over");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Map<String, String>> result = bs
					.executeBdpRequestOverride(name, securities, fields,
							period, over);
			return result;
		}

		if ("executeBdpRequestOverrideQuarter".equals(type)) {
			String over = (String) request.get("over");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, Map<String, String>>> result = bs
					.executeBdpRequestOverrideQuarter(name, securities, fields,
							currencies, over);
			return result;
		}

		if ("executeFieldInfoRequest".equals(type)) {
			String code = (String) request.get("code");

			Map<String, String> result = bs.executeFieldInfoRequest(name, code);
			return result;
		}

		return "Unknow " + type;
	}
}
