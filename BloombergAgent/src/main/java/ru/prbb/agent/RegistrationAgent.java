package ru.prbb.agent;

import java.io.IOException;
import java.net.URI;

import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 
 * @author RBr
 *
 */
@Service
@EnableScheduling
public class RegistrationAgent {

	private final Log log = LogFactory.getLog(getClass());
	private final SetupAgent setup;

	@Autowired
	public RegistrationAgent(SetupAgent setup) {
		this.setup = setup;
	}

	/**
	 * Регистрация на сервере каждые 5 мин
	 */
	@Scheduled(fixedDelay = 1000 * 60 * 5, initialDelay = 1000)
	public void execute() {
		log.trace("registration");

		if (setup.isPassive()) {
			log.trace("passive mode");
			return;
		}
		
		String host = setup.getHostServer();
		if (null == host) {
			log.trace("server host is null");
			return;
		}

		log.info("registration on " + host);
		
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			URI uri = new URI("http", null, host, setup.getPortServer(), "/Agents/add", null, null);
			HttpGet httpget = new HttpGet(uri);

			log.debug("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

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
			String responseBody = httpclient.execute(httpget, responseHandler);
			log.debug(responseBody);
		} catch (Exception e) {
			log.error("registration", e);
		}
	}

	@PreDestroy
	public void destroy() {
		log.trace("unregistration");

		if (setup.isMaster()) {
			log.trace("passive mode");
			return;
		}
		
		String host = setup.getHostServer();
		if (null == host) {
			log.trace("server host is null");
			return;
		}
		
		log.info("unregistration " + host);

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			URI uri = new URI("http", null, host, setup.getPortServer(), "/Agents/del", null, null);
			HttpDelete httpget = new HttpDelete(uri);

			log.debug("Executing request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

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
			String responseBody = httpclient.execute(httpget, responseHandler);
			log.debug(responseBody);
		} catch (Exception e) {
			log.error("unregistration", e);
		}
	}

}
