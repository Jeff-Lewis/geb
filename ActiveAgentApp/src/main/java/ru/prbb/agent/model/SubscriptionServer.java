package ru.prbb.agent.model;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

public class SubscriptionServer {

	private final URI uri;

	private String status = "Добавлен";

	public SubscriptionServer(String host) throws URISyntaxException {
		uri = new URI(host);
	}

	public String getHost() {
		return uri.getHost();
	}

	public HttpGet getUriRequestList() {
		return new HttpGet(uri);
	}

	public HttpGet getUriRequestSecs(Long id) throws URISyntaxException {
		return new HttpGet(newSecurity(id));
	}

	public HttpPost getUriRequestUpdate(Long id) throws URISyntaxException {
		return new HttpPost(newSecurity(id));
	}

	public HttpDelete getUriRequestStop(Long id) throws URISyntaxException {
		return new HttpDelete(newSecurity(id));
	}

	private URI newSecurity(Long id) throws URISyntaxException {
		String path = uri.getPath() + "/" + id;
		URIBuilder ub = new URIBuilder(uri).setPath(path);
		return ub.build();
	}

	public HttpPost getUriResponse(String result) {
		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(new StringEntity(result, "UTF-8"));
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
