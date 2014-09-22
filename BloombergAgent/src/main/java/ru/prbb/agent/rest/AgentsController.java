package ru.prbb.agent.rest;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.prbb.agent.services.SetupAgent;

/**
 * @author RBr
 */
@RestController
@RequestMapping("/Agents")
public class AgentsController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private SetupAgent setup;

	private Set<String> agents = Collections.synchronizedSet(new HashSet<String>());

	private void checkAccess() {
		if (setup.isSlave())
			throw new RuntimeException("Not master agent");
	}

	/**
	 * Список работающих агентов
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String[] list(HttpServletRequest request) {
		log.trace("GET list");
		checkAccess();
		log.info(request.getRemoteAddr() + " get agents list");
		return agents.toArray(new String[0]);
	}

	/**
	 * Включение агента
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/add")
	public void add(HttpServletRequest request) {
		log.trace(request.getMethod() + " add");
		checkAccess();
		String host = request.getRemoteAddr();
		if (checkAgent(host)) {
			agents.add(host);
			log.info("add agent " + host);
		}
	}

	/**
	 * Выключение агента
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/del")
	public void del(HttpServletRequest request) {
		log.trace(request.getMethod() + " del");
		checkAccess();
		String host = request.getRemoteAddr();
		agents.remove(host);
		log.info("remove agent " + host);
	}

	/**
	 * Проверка активности агентов
	 */
	@Scheduled(fixedDelay = 1000 * 60 * 10)
	public void check() {
		if (setup.isMaster()) {
			String[] _agents = agents.toArray(new String[0]);
			for (String host : _agents) {
				if (!checkAgent(host))
					agents.remove(host);
			}
		}
	}

	private boolean checkAgent(String host) {
		log.trace("check host " + host);

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			URI uri = new URI("http", null, host, 8080, "/", null, null);
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
			return responseBody.startsWith("BloombergAgent/");
		} catch (Exception e) {
			log.error("Failed check host " + host, e);
		}
		return false;
	}
}
