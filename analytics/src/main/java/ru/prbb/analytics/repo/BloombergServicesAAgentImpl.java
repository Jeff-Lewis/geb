/**
 * 
 */
package ru.prbb.analytics.repo;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
public final class BloombergServicesAAgentImpl implements BloombergServicesA {

	private static final Log log = LogFactory.getLog(BloombergServicesAAgentImpl.class);

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

	private String executeHttpRequest(String path, List<NameValuePair> nvps, String name) {
		nvps.add(new BasicNameValuePair("name", name));
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
	 * @see ru.prbb.analytics.repo.BloombergServicesA#executeReferenceDataRequest(java.lang.String, java.lang.String[], java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> executeReferenceDataRequest(String name,
			String[] securities, String[] fields) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/ReferenceData", nvps, name);
		return (Map<String, Map<String, String>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.analytics.repo.BloombergServicesA#executeBdpRequest(java.lang.String, java.lang.String[], java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, String>> executeBdpRequest(String name,
			String[] securities, String[] fields) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/ReferenceData", nvps, name);
		return (Map<String, Map<String, String>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.analytics.repo.BloombergServicesA#executeBdpRequestOverride(java.lang.String, java.lang.String[], java.lang.String[], java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Map<String, String>>> executeBdpRequestOverride(String name,
			String[] securities, String[] fields, String period, String over) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));
		nvps.add(new BasicNameValuePair("period", period));
		nvps.add(new BasicNameValuePair("over", over));

		String response = executeHttpRequest("/ReferenceDataOverride", nvps, name);
		return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.analytics.repo.BloombergServicesA#executeBdpRequestOverrideQuarter(java.lang.String, java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Map<String, String>>> executeBdpRequestOverrideQuarter(String name,
			String[] securities, String[] fields, String[] currencies, String over) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));
		nvps.addAll(createList("currencies", currencies));
		nvps.add(new BasicNameValuePair("over", over));

		String response = executeHttpRequest("/ReferenceDataOverrideQuarter", nvps, name);
		return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.analytics.repo.BloombergServicesA#executeBdsRequest(java.lang.String, java.lang.String[], java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/BdsRequest", nvps, name);
		return (Map<String, Object>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.analytics.repo.BloombergServicesA#executeBdhEpsRequest(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String[], java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Map<String, String>>> executeBdhEpsRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("dateStart", dateStart));
		nvps.add(new BasicNameValuePair("dateEnd", dateEnd));
		nvps.add(new BasicNameValuePair("period", period));
		nvps.add(new BasicNameValuePair("calendar", calendar));
		nvps.addAll(createList("currencies", currencies));
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/BdhEpsRequest", nvps, name);
		return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.analytics.repo.BloombergServicesA#executeBdhRequest(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[], java.lang.String[], java.lang.String[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Map<String, String>>> executeBdhRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("dateStart", dateStart));
		nvps.add(new BasicNameValuePair("dateEnd", dateEnd));
		nvps.add(new BasicNameValuePair("period", period));
		nvps.add(new BasicNameValuePair("calendar", calendar));
		nvps.addAll(createList("currencies", currencies));
		nvps.addAll(createList("securities", securities));
		nvps.addAll(createList("fields", fields));

		String response = executeHttpRequest("/BdhRequest", nvps, name);
		return (Map<String, Map<String, Map<String, String>>>) deserialize(response);
	}

	/* (non-Javadoc)
	 * @see ru.prbb.analytics.repo.BloombergServicesA#executeFieldInfoRequest(java.lang.String, java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> executeFieldInfoRequest(String name, String code) {
		List<NameValuePair> nvps = new ArrayList<>();
		nvps.add(new BasicNameValuePair("code", code));

		String response = executeHttpRequest("/FieldInfoRequest", nvps, name);
		return (Map<String, Object>) deserialize(response);
	}
}
