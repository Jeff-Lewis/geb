/**
 * 
 */
package ru.prbb.jobber.repo;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SubscriptionItem;

/**
 * @author RBr
 * 
 */
@Service
public class BloombergServicesJ {
	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * @param name
	 * @param items
	 * @return
	 */
	private Collection<NameValuePair> createList(String name, String[] items) {
		List<NameValuePair> list = new ArrayList<>(items.length);
		for (String s : items) {
			list.add(new BasicNameValuePair(name, s));
		}
		return list;
	}

	private synchronized String executeHttpRequest(String path, List<NameValuePair> nvps, String name) {
		nvps.add(new BasicNameValuePair("name", name));
		try {
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("172.16.15.117")
					//.setHost("172.23.149.175") // TODO DEBUG localhost
					.setPort(48080)
					.setPath(path)
					.build();

			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

			CloseableHttpClient httpclient = HttpClients.createDefault();
			try {
				log.debug("Executing request " + httpPost.getRequestLine());
				String responseBody = httpclient.execute(httpPost, new ResponseHandler<String>() {

					public String handleResponse(final HttpResponse response)
							throws ClientProtocolException, IOException {
						int status = response.getStatusLine().getStatusCode();
						String reason = response.getStatusLine().getReasonPhrase();
						if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
							HttpEntity entity = response.getEntity();
							return entity != null ? EntityUtils.toString(entity) : null;
						} else {
							throw new ClientProtocolException("Unexpected response status: " + status + " " + reason);
						}
					}

				});
				log.debug("Response\n" + responseBody);
				if (responseBody.contains("stackTrace")) {
					@SuppressWarnings("unchecked")
					Map<String, Object> res =
							(Map<String, Object>) deserialize(responseBody);
					Object message = res.get("message");
					throw new Exception((message != null) ? message.toString() : res.toString());
				}
				return responseBody;
			} finally {
				httpclient.close();
			}
		} catch (Exception e) {
			log.error("executeHttpRequest", e);
			throw new RuntimeException(e);
		}
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
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/BdsRequest", nvps, name);
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
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/ReferenceData", nvps, name);
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
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("dateStart", sdf.format(startDate)));
		nvps.add(new BasicNameValuePair("dateEnd", sdf.format(endDate)));
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/HistoricalDataRequest", nvps, name);
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
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("dateStart", sdf.format(startDate)));
		nvps.add(new BasicNameValuePair("dateEnd", sdf.format(endDate)));
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));
		nvps.addAll(createList("currencies", currencies));

		String response = executeHttpRequest("/HistoricalDataRequest", nvps, name);
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
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("startDate", sdf.format(startDate)));
		nvps.add(new BasicNameValuePair("endDate", sdf.format(endDate)));
		nvps.addAll(createList("securities", securities));
		nvps.add(new BasicNameValuePair("maType", maType));
		nvps.add(new BasicNameValuePair("taPeriod", taPeriod.toString()));
		nvps.add(new BasicNameValuePair("period", period));
		nvps.add(new BasicNameValuePair("calendar", calendar));

		String response = executeHttpRequest("/LoadAtrRequest", nvps, name);
		return (List<Map<String, Object>>) deserialize(response);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> executeBdpOverrideLoad(String name, List<SecForJobRequest> securities) {
		Set<String> currencies = new HashSet<>();
		List<NameValuePair> nvps = new ArrayList<>();
		for (SecForJobRequest security : securities) {
			currencies.add(security.iso);
			String cursec = security.iso + security.code;
			nvps.add(new BasicNameValuePair("securities", cursec));
		}
		nvps.addAll(createList("currencies", currencies.toArray(new String[currencies.size()])));

		String response = executeHttpRequest("/LoadBdpOverrideRequest", nvps, name);
		return (Map<String, Map<String, String>>) deserialize(response);
	}

	private List<SubscriptionItem> startedSubscriptions = new ArrayList<>();

	@PreDestroy
	public void destroy() {
		log.info("@PreDestroy: Subscriptions stop");

		subscriptionStop(startedSubscriptions);
	}

	public void subscriptionStart(SubscriptionItem item, List<SecurityItem> securities) {
		if (startedSubscriptions.contains(item))
			return;
		List<NameValuePair> nvps = new ArrayList<>(securities.size());
		nvps.add(new BasicNameValuePair("id", item.getId().toString()));
		for (SecurityItem security : securities) {
			nvps.add(new BasicNameValuePair("securities", security.getCode()));
		}
		String path = "/Subscriptions/Start";
		String name = "Start subscription " + item.getName();
		String response = executeHttpRequest(path, nvps, name);
		if ("STARTED".equals(response)) {
			startedSubscriptions.add(item);
		}
	}

	public void subscriptionStop(List<SubscriptionItem> items) {
		List<NameValuePair> nvps = new ArrayList<>(items.size());
		for (SubscriptionItem item : items) {
			nvps.add(new BasicNameValuePair("ids", item.getId().toString()));
		}
		String response = executeHttpRequest("/Subscriptions/Stop", nvps, "Stop subscriptions");
		String[] lines = response.split("\n");
		for (String line : lines) {
			String[] s = line.split("\t");
			String id = s[0];
			String res = s[1];
			if ("STOPPING".equals(res)) {
				SubscriptionItem item = new SubscriptionItem();
				item.setId(new Long(id));
				startedSubscriptions.remove(item);
			}
		}
	}

	public List<String[]> subscriptionData(SubscriptionItem item) {
		if (!startedSubscriptions.contains(item)) {
			return Collections.emptyList();
		}

		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("id", item.getId().toString()));
		nvps.add(new BasicNameValuePair("isClean", "true"));
		String path = "/Subscriptions/Data";
		String name = "Get data subscription " + item.getName();
		String response = executeHttpRequest(path, nvps, name);

		String[] lines = response.split("\n");
		List<String[]> result = new ArrayList<>(lines.length);
		for (String line : lines) {
			String[] arr = line.split("\t");
			if (arr.length != 3) {
				log.error("Data line:" + line);
			} else {
				result.add(arr);
			}
		}

		return result;
	}

}
