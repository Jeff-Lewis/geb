/**
 * 
 */
package ru.prbb.jobber.repo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SubscriptionItem;

/**
 * @author RBr
 */
@Service
public class BloombergServicesJ {

	//public static final String SERVER_JOBBER = "http://192.168.100.101:8080/Jobber"; // Облако
	public static final String SERVER_JOBBER = "http://172.16.15.36:10180/Jobber"; // Облако редирект

	private Logger log = LoggerFactory.getLogger(getClass());

	private String executeRequest(Map<String, Object> data) {
		return ""; // TODO
	}

	private final ObjectMapper m = new ObjectMapper();

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
		m.put("type", "BdsRequest");
		m.put("name", name);
		m.put("securities", securities);
		m.put("fields", fields);

		String response = executeRequest(m);
		return (Map<String, Object>) deserialize(response);
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
		m.put("type", "ReferenceData");
		m.put("name", name);
		m.put("securities", securities);
		m.put("fields", fields);

		String response = executeRequest(m);
		return (Map<String, Map<String, String>>) deserialize(response);
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
		m.put("type", "HistoricalDataRequest");
		m.put("name", name);
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("fields", fields);

		String response = executeRequest(m);
		return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
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
		m.put("type", "HistoricalDataRequest");
		m.put("name", name);
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("fields", fields);
		m.put("currencies", currencies);

		String response = executeRequest(m);
		return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
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
		m.put("type", "LoadAtrRequest");
		m.put("name", name);
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("maType", maType);
		m.put("taPeriod", taPeriod);
		m.put("period", period);
		m.put("calendar", calendar);

		String response = executeRequest(m);
		return (List<Map<String, Object>>) deserialize(response);
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
		m.put("type", "LoadBdpOverrideRequest");
		m.put("name", name);
		m.put("securities", cursecs);
		m.put("currencies", currencies);

		String response = executeRequest(m);
		return (Map<String, Map<String, String>>) deserialize(response);
	}

	private Map<SubscriptionItem, List<SecurityItem>> startedSubscriptions = new HashMap<>();

	@PreDestroy
	public void destroy() {
		log.info("@PreDestroy: Subscriptions stop");

		for (SubscriptionItem item : startedSubscriptions.keySet()) {
			subscriptionStop(item);
		}
	}

	public void subscriptionStart(SubscriptionItem item, List<SecurityItem> securities) {
		if (securities == null || securities.isEmpty())
			return;

		Collections.sort(securities);

		if (securities.equals(startedSubscriptions.get(item)))
			return;

		List<String> secs = new ArrayList<>(securities.size());
		for (SecurityItem security : securities) {
			secs.add(security.getCode());
		}
		
		Map<String, Object> m = new HashMap<>();
		m.put("type", "SubscriptionStart");
		m.put("id", item.getId());
		m.put("name", "Start subscriptions" + item.getName());
		m.put("uriCallback", SERVER_JOBBER + "/Subscription");
		m.put("securities", secs);

		String response = executeRequest(m);
		if ("STARTED".equals(response)) {
			startedSubscriptions.put(item, securities);
		}
	}

	public void subscriptionStop(SubscriptionItem item) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "SubscriptionStop");
		m.put("id", item.getId());
		m.put("name", "Stop subscriptions " + item.getName());

		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("id", item.getId().toString()));

		String response = executeRequest(m);
		if ("STOPPED".equals(response)) {
			startedSubscriptions.remove(item);
		}
	}

}
