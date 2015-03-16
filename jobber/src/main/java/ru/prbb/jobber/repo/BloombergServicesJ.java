/**
 * 
 */
package ru.prbb.jobber.repo;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.AgentTask;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SubscriptionItem;
import ru.prbb.jobber.services.AgentTaskService;

/**
 * @author RBr
 */
@Service
public class BloombergServicesJ {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AgentTaskService tasks;
	private Random rnd = new Random(System.currentTimeMillis());

	private String executeRequest(String name, Map<String, Object> data) throws InterruptedException {
		long idTask = rnd.nextLong();
		data.put("idTask", idTask);
		data.put("name", name);
		String json = serialize(data);
		AgentTask task = new AgentTask(idTask, json);
		tasks.add(task);
		synchronized (task) {
			for (int iter = 120, c = 0; c < iter; ++c) {
				try {
					task.wait(1000);
				} catch (InterruptedException ignore) {
				}

				String result = task.getResult();

				if (result == null) {
					continue;
				}

				if ("WAIT".equals(result)) {
					iter = 1200;
					continue;
				}

				tasks.remove(task);
				return result;
			}
		}
		throw new InterruptedException("Agent task execute timeout");
	}

	private final ObjectMapper m = new ObjectMapper();

	private String serialize(Object object) {
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

	/**
	 * BDS запрос
	 * 
	 * @param name
	 * @param securities
	 * @param fields
	 * @return [Peers, EARN_ANN_DT_TIME_HIST_WITH_EPS, ERN_ANN_DT_AND_PER,
	 *         PeerTicker, BEST_ANALYST_RECS_BULK]
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdsRequest");
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			return (Map<String, Object>) deserialize(response);
		} catch (Exception e) {
			log.error("BdsRequest", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Выполнить запрос //blp/refdata, ReferenceDataRequest<br>
	 * Добавить несколько компаний
	 * 
	 * @param name
	 *            Название запроса
	 * @param securities
	 *            Коды
	 * @param fields
	 *            Поля
	 * @return security -> {field, value}
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> executeReferenceDataRequest(String name,
			String[] securities, String[] fields) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeReferenceDataRequest");
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			return (Map<String, Map<String, String>>) deserialize(response);
		} catch (Exception e) {
			log.error("ReferenceData", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Загрузка котировок
	 * 
	 * @param name
	 *            Название запроса
	 * @param startDate
	 * @param endDate
	 * @param securities
	 *            Коды
	 * @param fields
	 *            Поля
	 * @return security -> {date -> { field, value } }
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeHistoricalDataRequest");
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("fields", fields);
		m.put("currencies", null);

		try {
			String response = executeRequest(name, m);
			return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
		} catch (Exception e) {
			log.error("HistoricalDataRequest", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Загрузка котировок
	 * 
	 * @param name
	 *            Название запроса
	 * @param startDate
	 * @param endDate
	 * @param securities
	 *            Коды
	 * @param fields
	 *            Поля
	 * @return security -> {date -> { field, value } }
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields, String[] currencies) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeHistoricalDataRequest");
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("fields", fields);
		m.put("currencies", currencies);

		try {
			String response = executeRequest(name, m);
			return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
		} catch (Exception e) {
			log.error("HistoricalDataRequest", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Загрузка ATR
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeAtrLoad(String name, Date startDate, Date endDate, String[] securities,
			String maType, Integer taPeriod, String period, String calendar) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeAtrLoad");
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("maType", maType);
		m.put("taPeriod", taPeriod);
		m.put("period", period);
		m.put("calendar", calendar);

		try {
			String response = executeRequest(name, m);
			return (List<Map<String, Object>>) deserialize(response);
		} catch (Exception e) {
			log.error("LoadAtrRequest", e);
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> executeBdpOverrideLoad(String name, List<SecForJobRequest> securities) {
		Set<String> currencies = new HashSet<>();
		List<String> cursecs = new ArrayList<>(securities.size());
		for (SecForJobRequest security : securities) {
			currencies.add(security.iso);
			String cursec = security.iso + security.code;
			cursecs.add(cursec);
		}

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdpOverrideLoad");
		m.put("cursecs", cursecs);
		m.put("currencies", currencies);

		try {
			String response = executeRequest(name, m);
			return (Map<String, Map<String, String>>) deserialize(response);
		} catch (Exception e) {
			log.error("LoadBdpOverrideRequest", e);
			throw new RuntimeException(e);
		}
	}

	// FIXME uriCallback
	//private String URI_CALLBACK = "http://172.23.153.164:8080/Jobber/Subscription";

	// Облако
	//private String URI_CALLBACK = "http://192.168.100.101:8080/Jobber/Subscription";

	// Облако редирект
	private String URI_CALLBACK = "http://172.16.15.36:10180/Jobber/Subscription";

	// Облако редирект
	//private String URI_CALLBACK = "http://172.16.15.36:10190/Jobber/Subscription";

	public void subscriptionStart(SubscriptionItem item, List<SecurityItem> securities) {
		if (securities == null || securities.isEmpty())
			return;

		List<String> secs = new ArrayList<>(securities.size());
		for (SecurityItem security : securities) {
			secs.add(security.getCode());
		}

		Map<String, Object> m = new HashMap<>();
		m.put("type", "SubscriptionStart");
		m.put("id", item.getId());
		m.put("uriCallback", URI_CALLBACK);
		
		m.put("securities", secs);

		try {
			String response = executeRequest("Start subscriptions" + item.getName(), m);
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) deserialize(response);
			if (map.get("result").toString().contains("STARTED")) {
				item.setSubscriptionId(map.get("subscriptionId"));
				log.info(response);
			} else {
				log.error(response);
			}
		} catch (Exception e) {
			log.error("SubscriptionStart", e);
			throw new RuntimeException(e);
		}
	}

	public void subscriptionStop(SubscriptionItem item) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "SubscriptionStop");
		m.put("id", item.getId());
		m.put("subscriptionId", item.getSubscriptionId());
		m.put("uriCallback", URI_CALLBACK);

		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("id", item.getId().toString()));

		try {
			String response = executeRequest("Stop subscriptions " + item.getName(), m);
			if (response.contains("STOPPED")) {
				log.info(response);
			} else {
				log.error(response);
			}
		} catch (Exception e) {
			log.error("SubscriptionStop", e);
			throw new RuntimeException(e);
		}
	}

}
