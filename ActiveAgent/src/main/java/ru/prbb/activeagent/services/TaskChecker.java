/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.prbb.activeagent.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;

/**
 * @author ruslan
 */
public class TaskChecker extends AbstractChecker {

	public TaskChecker(String host, String path) throws URISyntaxException {
		super("http://" + host + path + "/Tasks");
	}

	@Override
	protected int getDelaySec() {
		return 3;
	}

	private ResponseHandler<TaskItem> taskHandler = new ResponseHandler<TaskItem>() {

		private TaskItem empty_task = new TaskItem();
		{
			empty_task.setId(Long.valueOf(0));
			empty_task.setType("empty_task");
		}

		@Override
		public TaskItem handleResponse(HttpResponse response) {
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

					return mapper.readValue(body,
							new TypeReference<TaskItem>() {
							});
				}
			} catch (Exception ex) {
				logger.log(Level.SEVERE, "JobberChecker", ex);
			}
			return empty_task;
		}
	};

	private ResponseHandler<String> taskDataHandler = new ResponseHandler<String>() {

		@Override
		public String handleResponse(HttpResponse response) {
			try {
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode < 200 || statusCode >= 300) {
					String reason = statusLine.getReasonPhrase();
					throw new Exception("Response status: " + statusCode + " " + reason);
				}
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity);
				}
			} catch (Exception ex) {
				logger.log(Level.SEVERE, "JobberChecker", ex);
			}
			return "";
		}
	};

	@Override
	protected void check(CloseableHttpClient httpClient) throws IOException {
		HttpGet requestId = new HttpGet(uri);
		TaskItem task = httpClient.execute(requestId, taskHandler);

		if (task.getId() == 0) {
			return;
		}

		logger.log(Level.INFO, "Process task {0}", task);

		HttpGet requestData = new HttpGet(uri);
		String taskData = httpClient.execute(requestData, taskDataHandler);

		if (taskData.isEmpty()) {
			return;
		}

		logger.log(Level.INFO, "Task data {0}", taskData);
	}

}
