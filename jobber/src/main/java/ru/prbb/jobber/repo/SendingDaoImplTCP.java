/**
 * 
 */
package ru.prbb.jobber.repo;

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
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.SendingItem;

/**
 * @author RBr
 * 
 */
@Service
public class SendingDaoImplTCP extends SendingDaoImpl
{

	@Override
	public SendingItem sendMail(String email_text, String email) {
		final String smtpServer = "wonderworksinvestments.com";
		final String fromAddress = "noreply@wonderworksinvestments.com";
		final String userName = fromAddress;
		final String password = "D6c8W3g2";
		final String toAddress = email;
		final String subject = "info";
		final String body = email_text;

		if (toAddress.contains("prbb.ru")) {
			throw new IllegalArgumentException("В отправителях домен prbb.ru");
		}

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
		String api_id = "33bd77b5-d915-c964-a184-a6e07b15b226";
		String from = "LIFE";

		String res = null;
		try {
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("sms.ru")
					.setPath("/sms/send")
					.build();

			HttpPost httpPost = new HttpPost(uri);
			
			List<NameValuePair> nvps = new ArrayList<>();
			//nvps.add(new BasicNameValuePair("test", "1"));
			nvps.add(new BasicNameValuePair("api_id", api_id));
			nvps.add(new BasicNameValuePair("from", from));
			nvps.add(new BasicNameValuePair("to", to));
			nvps.add(new BasicNameValuePair("text", text));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

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
