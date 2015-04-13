/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.prbb.activeagent.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.executors.TaskAtrLoadExecutor;
import ru.prbb.activeagent.executors.TaskBdhEpsExecutor;
import ru.prbb.activeagent.executors.TaskBdhExecutor;
import ru.prbb.activeagent.executors.TaskBdpExecutor;
import ru.prbb.activeagent.executors.TaskBdpOverrideExecutor;
import ru.prbb.activeagent.executors.TaskBdpOverrideLoadExecutor;
import ru.prbb.activeagent.executors.TaskBdpOverrideQuarterExecutor;
import ru.prbb.activeagent.executors.TaskBdsExecutor;
import ru.prbb.activeagent.executors.TaskCashFlowLoadExecutor;
import ru.prbb.activeagent.executors.TaskCashFlowLoadNewExecutor;
import ru.prbb.activeagent.executors.TaskExecutor;
import ru.prbb.activeagent.executors.TaskFieldInfoExecutor;
import ru.prbb.activeagent.executors.TaskHistoricalDataExecutor;
import ru.prbb.activeagent.executors.TaskRateCouponLoadExecutor;
import ru.prbb.activeagent.executors.TaskReferenceDataExecutor;
import ru.prbb.activeagent.executors.TaskValuesLoadExecutor;

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
	protected void check(CloseableHttpClient httpClient) throws Exception {
		HttpGet requestId = new HttpGet(uri);
		TaskItem task = httpClient.execute(requestId, taskHandler);

		if (task.getId() == 0) {
			return;
		}

		logger.log(Level.INFO, "Process task {0}", task);

		String path = uri.getPath() + "/" + task.getId();
		URI uriTask = new URIBuilder(uri).setPath(path).build();

		HttpGet requestData = new HttpGet(uriTask);
		String taskData = httpClient.execute(requestData, taskDataHandler);

		if (taskData.isEmpty()) {
			return;
		}

		logger.log(Level.INFO, "Task data {0}", taskData);

		TaskExecutor taskExecutor = null;
		String type = task.getType();
		for (TaskExecutor executor : executors) {
			if (type.equals(executor.getType())) {
				taskExecutor = executor.getClass().newInstance();
				taskExecutor.setUri(uriTask);
				taskExecutor.setHttpClient(httpClient);
				break;
			}
		}

		try {
			taskExecutor.execute(task, taskData);
		} catch (Exception e) {
			String message = e.getMessage();
			logger.severe(message);
			taskExecutor.sendError(message);
		}

		taskExecutor.send("DONE");
	}

	private final List<TaskExecutor> executors = new ArrayList<>(16);

	{
		executors.add(new TaskAtrLoadExecutor());
		executors.add(new TaskBdhEpsExecutor());
		executors.add(new TaskBdhExecutor());
		executors.add(new TaskBdpExecutor());
		executors.add(new TaskBdpOverrideExecutor());
		executors.add(new TaskBdpOverrideLoadExecutor());
		executors.add(new TaskBdpOverrideQuarterExecutor());
		executors.add(new TaskBdsExecutor());
		executors.add(new TaskCashFlowLoadExecutor());
		executors.add(new TaskCashFlowLoadNewExecutor());
		executors.add(new TaskFieldInfoExecutor());
		executors.add(new TaskHistoricalDataExecutor());
		executors.add(new TaskRateCouponLoadExecutor());
		executors.add(new TaskReferenceDataExecutor());
		executors.add(new TaskValuesLoadExecutor());
	}

}
