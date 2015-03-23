package ru.prbb.agent.services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author RBr
 */
@Service
@EnableScheduling
public class RegistrationService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private SetupService setup;

	private URI uri;
	private boolean isActive;

	@PostConstruct
	public void init() {
		try {
			uri = new URIBuilder()
					.setScheme("http")
					.setHost(setup.getHostServer())
					.setPort(setup.getPortServer())
					.setPath("/Jobber/Agents").build();
		} catch (Exception e) {
			log.error("RegistrationService::PostConstruct", e);
			uri = null;
		}
	}

	/**
	 * Регистрация на сервере каждые 5 мин
	 */
	@Scheduled(fixedDelay = 1000 * 60 * 5)
	public void execute() {
		if (uri == null) {
			log.error("registration canceled");
			return;
		}

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("host", setup.getHostAgent()));
			nvps.add(new BasicNameValuePair("port", String.valueOf(setup.getPortAgent())));

			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

			log.info("Executing registration request " + httpPost.getRequestLine());
			String responseBody = httpclient.execute(httpPost, responseHandler);
			log.info(responseBody);

			isActive = true;
		} catch (Exception e) {
			log.error("registration", e);
		}

	}

	@PreDestroy
	public void destroy() {
		if (isActive) {
			try {
				HttpDelete httpDelete = new HttpDelete(uri);
				log.info("Executing unregistration request " + httpDelete.getRequestLine());
				try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
					String responseBody = httpclient.execute(httpDelete, responseHandler);
					log.info(responseBody);
				}
			} catch (Exception e) {
				log.error("unregistration", e);
			}
		}
	}

	// Create a custom response handler
	private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

		public String handleResponse(final HttpResponse response)
				throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		}

	};
}
