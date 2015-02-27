package ru.prbb.agent.domain;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

public class JobServer {

	private final URI uri;

	private String status = "Добавлен";

	public JobServer(String host) throws URISyntaxException {
		uri = new URIBuilder("http://" + host)
				.setPath("/Jobber/AgentTask")
				.build();
	}

	public String getHost() {
		return uri.getHost();
	}

	public HttpUriRequest getUriRequest() {
		return new HttpGet(uri);
	}

	public HttpUriRequest getUriResponse(String string) {
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(new StringEntity(string, "UTF-8"));
		return httpPost;
	}

	@Override
	public String toString() {
		return uri.toString();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String s) {
		this.status = s;
	}
}
