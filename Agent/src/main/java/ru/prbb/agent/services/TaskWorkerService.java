package ru.prbb.agent.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ruslan
 *
 */
@Service
public class TaskWorkerService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private StringBuilder status = new StringBuilder("<h2>Сервис остановлен.</h2>");
	private WorkThread wt;
	private boolean running;

	public synchronized void start(String host) throws URISyntaxException {
		if (wt != null) {
			throw new IllegalStateException("The service already running");
		}

		wt = new WorkThread(host);
		wt.start();
	}

	public synchronized void stop() {
		if (wt == null) {
			throw new IllegalStateException("The service does not running");
		}

		status = new StringBuilder("<h2>Сервис останавливается...</h2>");

		try {
			running = false;
			wt.join();
		} catch (InterruptedException ignore) {
		}

		status = new StringBuilder("<h2>Сервис остановлен.</h2>");
	}

	public boolean isRunning() {
		return (wt != null) && running;
	}

	public String getStatus() {
		return status.toString();
	}

	private class WorkThread extends Thread {
		private URI server;
		private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			public String handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					String reason = response.getStatusLine().getReasonPhrase();
					throw new ClientProtocolException("Unexpected response status: " + status + ' ' + reason);
				}
			}

		};

		public WorkThread(String host) throws URISyntaxException {
			super("WorkThread for " + host);
			setDaemon(true);
			server = new URI(host);
		}

		@Override
		public void run() {
			try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
				HttpGet httpGet = new HttpGet(server);
				log.debug("Executing request " + httpGet.getRequestLine());

				status = new StringBuilder("<h2>Сервис запущен.</h2><br/>");

				running = true;
				do {
					String responseBody = httpClient.execute(httpGet, responseHandler);

					processTask(responseBody);

					try {
						Thread.sleep(1500);
					} catch (InterruptedException ignore) {
					}
				} while (running);
			} catch (Exception e) {
				log.error("WorkThread.run", e);
				status.append("Ошибка: ").append(e.getMessage());
			} finally {
				wt = null;
			}
		}
	}

	private void processTask(String responseBody) {
		log.info("Executing request " + responseBody);
	}
}
