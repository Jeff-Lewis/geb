/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import ru.prbb.middleoffice.domain.SendingItem;

/**
 * @author RBr
 * 
 */
//@Service
public class SendingDaoImplTCP extends SendingDaoImpl
{
	private String api_id = "fdee122b-3a2e-59d4-5951-54ace233fd42";
	private String from = "LIFE";
	private String proxyHostname = "prbwg.life.corp";
	private int proxyPort = 8080;

	@Override
	public SendingItem sendMail(String email_text, String email) {
		final String smtpServer = "appsext.life.corp";
		final String fromAddress = "hopebackup@prbb.ru";
		final String userName = "hopebackup";
		final String password = "gjhdkjsfgkja";
		final String toAddress = email;
		final String subject = "info";
		final String body = email_text;

		String res;
		
		// create session
		//java:jboss/mail/Default
		Session session = Session.getDefaultInstance(System.getProperties());
		try {
			// create message
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromAddress));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			msg.setSubject(subject);
			msg.setText(body);

			Transport tr = session.getTransport();
			try {
				tr.connect(smtpServer, 25, userName, password);
				tr.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
				res = "0";
			} finally {
				tr.close();
			}
		} catch (Exception e) {
			log.error("sendMail", e);
			res = e.getMessage();
		}

		SendingItem si = new SendingItem();
		si.setMail(email);
		si.setStatus(res);
		return si;
	}

	@Override
	public SendingItem sendSms(String text, String to) {
//		System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1|172.*.*.*");
//		System.setProperty("http.proxyHost", "prbwg.life.corp");
//		System.setProperty("http.proxyPort", "8080");
//		System.setProperty("http.proxyUser", "BrihlyaevRA");
//		System.setProperty("http.proxyPassword", "Java77proG");

		String res = null;
		try {
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("sms.ru")
					.setPath("/sms/send")
					.build();

			HttpPost httpPost = new HttpPost(uri);
			
			List<NameValuePair> nvps = new ArrayList<>();
			nvps.add(new BasicNameValuePair("test", "1"));
			nvps.add(new BasicNameValuePair("api_id", api_id));
			//nvps.add(new BasicNameValuePair("from", from));
			nvps.add(new BasicNameValuePair("to", to));
			nvps.add(new BasicNameValuePair("text", text));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

//			HttpRoutePlanner routePlanner;
//			routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
//			routePlanner = new DefaultProxyRoutePlanner(new HttpHost(proxyHostname, proxyPort));
//
//			AuthenticationStrategy auth = new ProxyAuthenticationStrategy();
//
//			HttpClientBuilder httpClientBuilder = HttpClients.custom()
//					.setRoutePlanner(routePlanner)
//					.setProxyAuthenticationStrategy(auth);
//
//			try (CloseableHttpClient httpclient = httpClientBuilder.build()) {
//			try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			try (CloseableHttpClient httpclient = HttpClients.createSystem()) {
				log.debug("Executing request " + httpPost.getRequestLine());
				String responseBody = httpclient.execute(httpPost, new ResponseHandler<String>() {

					public String handleResponse(final HttpResponse response)
							throws ClientProtocolException, IOException {
						int status = response.getStatusLine().getStatusCode();
						if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
							HttpEntity entity = response.getEntity();
							return entity != null ? EntityUtils.toString(entity) : null;
						} else {
							throw new ClientProtocolException("Unexpected response status: " + status);
						}
					}

				});
				log.debug("Response " + responseBody);
				res = responseBody;
			}
		} catch (Exception e) {
			log.error("executeHttpRequest", e);
			throw new RuntimeException(e);
		}

		try {
			if ("0".equals(res)) {
				Thread.sleep(4000);
			}
		} catch (InterruptedException e) {
			log.error("sendSms", e);
		}

		SendingItem si = new SendingItem();
		si.setMail(to);
		si.setStatus(res);
		return si;
	}

}
