/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * @author RBr
 */
//@Service
public final class BloombergServicesMAgentImpl implements BloombergServicesM {

	private static final Log log = LogFactory.getLog(BloombergServicesMAgentImpl.class);

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

	/**
	 * @param name
	 * @param ids
	 * @return
	 */
	private Collection<NameValuePair> createList(String name, Map<String, Long> ids) {
		List<NameValuePair> list = new ArrayList<>(ids.size());
		for (Entry<String, Long> entry : ids.entrySet()) {
			String id = entry.getValue().toString();
			String security = entry.getKey();
			list.add(new BasicNameValuePair(name, id + ";" + security));
		}
		return list;
	}

	private String executeHttpRequest(String path, List<NameValuePair> nvps) {
		try {
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("172.16.15.117")
					//.setHost(InetAddress.getLocalHost().getHostAddress())
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
						if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
							HttpEntity entity = response.getEntity();
							return entity != null ? EntityUtils.toString(entity) : null;
						} else {
							throw new ClientProtocolException("Unexpected response status: " + status);
						}
					}

				});
				log.debug("Response " + responseBody);
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
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	private final ObjectMapper m = new ObjectMapper();

	private Object deserialize(String content) {
		try {
			return m.readValue(content, Object.class);
		} catch (Exception e) {
			log.error("deserialize", e);
		}
		throw new RuntimeException("deserialize");
	}

	/* (non-Javadoc)
	 * @see ru.prbb.middleoffice.repo.BloombergServicesM#executeReferenceDataRequest(java.lang.String, java.lang.String[], java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> executeReferenceDataRequest(String name,
			String[] securities, String[] fields) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("name", name));
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/ReferenceData", nvps);
		return (Map<String, Map<String, String>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.middleoffice.repo.BloombergServicesM#executeHistoricalDataRequest(java.lang.String, java.util.Date, java.util.Date, java.lang.String[], java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("name", name));
		nvps.add(new BasicNameValuePair("dateStart", sdf.format(startDate)));
		nvps.add(new BasicNameValuePair("dateEnd", sdf.format(endDate)));
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/HistoricalDataRequest", nvps);
		return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.middleoffice.repo.BloombergServicesM#executeCashFlowLoad(java.util.Map, java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeCashFlowLoad(Map<String, Long> ids, Map<String, String> dates) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("ids", ids));
		for (Entry<String, String> entry : dates.entrySet()) {
			String date = entry.getValue();
			String security = entry.getKey();
			nvps.add(new BasicNameValuePair("dates", date + ";" + security));
		}

		String response = executeHttpRequest("/LoadCashFlowRequest", nvps);
		return (List<Map<String, Object>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.middleoffice.repo.BloombergServicesM#executeCashFlowLoadNew(java.util.Map, java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeCashFlowLoadNew(Map<String, Long> ids, Map<String, String> dates) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("ids", ids));
		for (Entry<String, String> entry : dates.entrySet()) {
			String date = entry.getValue();
			String security = entry.getKey();
			nvps.add(new BasicNameValuePair("dates", date + ";" + security));
		}

		String response = executeHttpRequest("/LoadCashFlowRequestNew", nvps);
		return (List<Map<String, Object>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.middleoffice.repo.BloombergServicesM#executeValuesLoad(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeValuesLoad(final Map<String, Long> ids) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("ids", ids));

		String response = executeHttpRequest("/LoadValuesRequest", nvps);
		return (List<Map<String, Object>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.middleoffice.repo.BloombergServicesM#executeRateCouponLoad(java.util.Map)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeRateCouponLoad(final Map<String, Long> ids) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("ids", ids));

		String response = executeHttpRequest("/LoadRateCouponRequest", nvps);
		return (List<Map<String, Object>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.middleoffice.repo.BloombergServicesM#executeAtrLoad(java.util.Date, java.util.Date, java.lang.String[], java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> executeAtrLoad(Date startDate, Date endDate, String[] securities,
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

		String response = executeHttpRequest("/LoadAtrRequest", nvps);
		return (List<Map<String, Object>>) deserialize(response);
	}
}
