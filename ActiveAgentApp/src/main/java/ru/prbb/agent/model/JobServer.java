package ru.prbb.agent.model;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

public class JobServer {

	private final URI uri;

	private String status = "Добавлен";

	public JobServer(String host) throws URISyntaxException {
		uri = new URI(host);
	}

	public String getHost() {
		return uri.getHost();
	}

	public HttpUriRequest getUriRequest() {
		return new HttpGet(uri);
	}

	public HttpUriRequest getUriResponse(String idTask, String status, String result) {
		StringBuilder content = new StringBuilder(idTask.length() + 50 + result.length());
		content.append(idTask).append(' ');
		content.append(status).append('\n');
		content.append(result);

		HttpEntity entity;
		try {
			byte[] bs = content.toString().getBytes("UTF-8");
			entity = new ByteArrayEntity(bs, ContentType.APPLICATION_OCTET_STREAM);
		} catch (UnsupportedEncodingException ignore) {
			entity = new StringEntity(content.toString(), "UTF-8");
		}

		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(entity);
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
