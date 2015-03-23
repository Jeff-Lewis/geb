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
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.prbb.agent.model.JobServer;

/**
 * 
 * @author ruslan
 *
 */
@Service
public class JobService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private BloombergServices bs;
	@Autowired
	private JobServerRepository servers;

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

	@Scheduled(fixedDelay = 1000)
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
			
			if (null == requestBody || requestBody.isEmpty()) {
				server.setStatus("Ожидание");
				return;
			}
			
			log.debug(requestBody);
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
				Object resultTask = processTask(type, request);
				
				StringWriter w = new StringWriter();
				mapper.writeValue(w, resultTask);
				
				HttpUriRequest uriRequest = server.getUriResponse(type, idTask, w.toString());
				
				server.setStatus("Отправляется ответ " + type);
				httpClient.execute(uriRequest, responseHandler);
				
				server.setStatus("Выполнен запрос к серверу " + type);
			} catch (Exception e) {
				log.error("Execute HTTP " + e.getMessage());
				server.setStatus(e.toString());
				
				HttpUriRequest uriRequest = server.getUriResponse(type, idTask, e.getMessage());
				
				server.setStatus("Отправляется ответ " + type);
				httpClient.execute(uriRequest, responseHandler);
			}
		} catch (Exception e) {
			log.error("Execute HTTP " + e.getMessage());
			server.setStatus(e.toString());
		}
	}

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

			Map<String, Object> result = bs.executeBdsRequest(name, securities, fields);
			return result;
		}

		if ("executeReferenceDataRequest".equals(type)) {
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Map<String, String>> result = bs.executeReferenceDataRequest(name, securities, fields);
			return result;
		}

		if ("executeHistoricalDataRequest".equals(type)) {
			String dateStart = (String) request.get("dateStart");
			String dateEnd = (String) request.get("dateEnd");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, Map<String, String>>> result =
					bs.executeHistoricalDataRequest(name, dateStart, dateEnd, securities, fields, currencies);
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

			Map<String, Map<String, Map<String, String>>> result =
					bs.executeBdhRequest(name, dateStart, dateEnd, period, calendar, currencies, securities, fields);
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

			Map<String, Map<String, Map<String, String>>> result =
					bs.executeBdhEpsRequest(name, dateStart, dateEnd, period, calendar, currencies, securities, fields);
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

			List<Map<String, Object>> result =
					bs.executeLoadAtrRequest(name, dateStart, dateEnd, securities, maType, taPeriod, period, calendar);
			return result;
		}

		if ("executeBdpOverrideLoad".equals(type)) {
			String[] cursecs = toArray(request.get("cursecs"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, String>> result = bs.executeBdpOverrideLoad(name, cursecs, currencies);
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

			List<Map<String, Object>> result = bs.executeLoadCashFlowRequest(name, ids, dates);
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

			List<Map<String, Object>> result = bs.executeLoadCashFlowRequestNew(name, ids, dates);
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

			List<Map<String, Object>> result = bs.executeLoadValuesRequest(name, ids);
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

			List<Map<String, Object>> result = bs.executeLoadRateCouponRequest(name, ids);
			return result;
		}

		if ("executeBdpRequest".equals(type)) {
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Map<String, String>> result = bs.executeBdpRequest(name, securities, fields);
			return result;
		}

		if ("executeBdpRequestOverride".equals(type)) {
			String period = (String) request.get("period");
			String over = (String) request.get("over");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));

			Map<String, Map<String, String>> result =
					bs.executeBdpRequestOverride(name, securities, fields, period, over);
			return result;
		}

		if ("executeBdpRequestOverrideQuarter".equals(type)) {
			String over = (String) request.get("over");
			String[] securities = toArray(request.get("securities"));
			String[] fields = toArray(request.get("fields"));
			String[] currencies = toArray(request.get("currencies"));

			Map<String, Map<String, Map<String, String>>> result =
					bs.executeBdpRequestOverrideQuarter(name, securities, fields, currencies, over);
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
