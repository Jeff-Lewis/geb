/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.prbb.activeagent.services;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.SecurityItem;
import ru.prbb.activeagent.data.SubscriptionItem;
import static ru.prbb.activeagent.data.SubscriptionItem.RUNNING;
import static ru.prbb.activeagent.data.SubscriptionItem.STOPPED;

/**
 * @author ruslan
 */
public class SubscriptionChecker extends AbstractChecker {

	private final List<SubscriptionRunner> subs = new ArrayList<>();

	public SubscriptionChecker(String host) throws URISyntaxException {
		super("http://" + host + "/Jobber/Subscription");
	}

	private ResponseHandler<List<SubscriptionItem>> subscriptionsHandler = new ResponseHandler<List<SubscriptionItem>>() {

		@Override
		public List<SubscriptionItem> handleResponse(HttpResponse response) {
			List<SubscriptionItem> result = Collections.emptyList();
			try {
				StatusLine statusLine = response.getStatusLine();

				int statusCode = statusLine.getStatusCode();
				if (statusCode < 200 || statusCode >= 300) {
					String reason = statusLine.getReasonPhrase();
					throw new Exception("Response status: " + statusCode + " " + reason);
				}

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String body = EntityUtils.toString(entity);

					List<SubscriptionItem> r = mapper.readValue(body,
							new TypeReference<ArrayList<SubscriptionItem>>() {
							});
					if (r != null) {
						result = r;
					}
				}
			} catch (Exception ex) {
				logger.log(Level.SEVERE, "SubscriptionChecker", ex);
			}
			return result;
		}
	};

	@Override
	protected int getDelaySec() {
		return 30;
	}

	@Override
	protected void check(CloseableHttpClient httpClient) throws Exception {
		HttpGet request = new HttpGet(uri);
		List<SubscriptionItem> list = httpClient.execute(request, subscriptionsHandler);

		for (SubscriptionItem item : list) {

			int idx = -1;

			for (int i = 0; i < subs.size(); ++i) {
				SubscriptionRunner runner = subs.get(i);
				if (runner.getId().equals(item.getId())) {
					idx = i;
					break;
				}
			}

			if (idx >= 0) {
				SubscriptionRunner runner = subs.get(idx);
				runner.setName(item.getName());
				runner.setComment(item.getComment());

				if (runner.isStopped()) {
					if (isRunning(item)) {
						start(httpClient, runner);
					}
				} else {
					if (isRunning(item)) {
						start(httpClient, runner);
					} else {
						runner.stop();
					}
				}
			} else {
				if (isRunning(item)) {
					SubscriptionRunner runner = new SubscriptionRunner(uri, item);
					subs.add(runner);
					start(httpClient, runner);
				}
			}
		}
	}

	private boolean isRunning(SubscriptionItem item) {
		String status = item.getStatus();
		if (RUNNING.equals(status)) {
			return true;
		}
		if (STOPPED.equals(status)) {
			return false;
		}
		throw new RuntimeException("Unknown subscription status: " + status);
	}

	private ResponseHandler<Set<SecurityItem>> securitiesHandler = new ResponseHandler<Set<SecurityItem>>() {

		@Override
		public Set<SecurityItem> handleResponse(HttpResponse response) {
			Set<SecurityItem> result = Collections.emptySet();
			try {
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode < 200 || statusCode >= 300) {
					String reason = statusLine.getReasonPhrase();
					throw new Exception("Response status: " + statusCode + " " + reason);
				}
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String body = EntityUtils.toString(entity);
					Set<SecurityItem> r = mapper.readValue(body,
							new TypeReference<HashSet<SecurityItem>>() {
							});
					if (r != null) {
						result = r;
					}
				}
			} catch (Exception ex) {
				logger.log(Level.SEVERE, "Subscription securities", ex);
			}
			return result;
		}
	};

	private void start(CloseableHttpClient httpClient, SubscriptionRunner runner) throws Exception {
		HttpGet request = new HttpGet(runner.getURI());
		Set<SecurityItem> secs = httpClient.execute(request, securitiesHandler);
		runner.start(secs);
	}

	@Override
	public void stop() {
		super.stop();

		for (SubscriptionRunner sub : subs) {
			sub.stop();
		}
	}
}
