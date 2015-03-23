package ru.prbb.agent.model;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

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

	public HttpUriRequest getUriResponse(String type, String idTask, String result) {
		HttpPost httpPost = new HttpPost(uri);
		List<BasicNameValuePair> nvp = new ArrayList<>();
		nvp.add(new BasicNameValuePair("type", type));
		nvp.add(new BasicNameValuePair("idTask", idTask));
		nvp.add(new BasicNameValuePair("result", result));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvp, "UTF-8"));
		} catch (UnsupportedEncodingException ignore) {
		}
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
