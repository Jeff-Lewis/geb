package ru.prbb.jobber.repo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.SendMessageItem;
import ru.prbb.jobber.domain.SendingItem;
import ru.prbb.jobber.domain.SimpleItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * Рассылка E-mail и SMS
 * 
 * @author RBr
 */
@Service
public class SendingDao
{

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	protected EntityManagerService ems;

	@Resource(mappedName = "java:jboss/mail/wwi")
	private Session mailSessionWWI;

	public List<String> getMailByGroup(String group) {
		String sql = "select value from dbo.ncontacts_groups_view where gname=? and type='E-mail'";
		return ems.getSelectList(String.class, sql, group);
	}

	public List<String> getPhoneByGroup(String group) {
		String sql = "select value from dbo.ncontacts_groups_view where gname=? and type='Мобильный телефон'";
		return ems.getSelectList(String.class, sql, group);
	}

	public List<SendingItem> send(List<SendMessageItem> items) {
		List<SendingItem> list = new ArrayList<>(items.size());
		for (SendMessageItem item : items) {
			int type = item.getType().intValue();
			String subject = item.getSubj();
			String text = item.getText();
			String[] addrs = item.getAddrsArray();

			switch (type) {
			case 0:
				list.addAll(sendSms(0, text, Arrays.asList(addrs), 1));
				break;

			case 1:
				list.addAll(sendMail(text, Arrays.asList(addrs), subject));
				break;

			default:
				String status = "Unknow message type: " + item.getType();
				log.warn(status);
				SendingItem res = new SendingItem();
				res.setMail(subject);
				res.setStatus(status);
				list.add(res);
			}
		}
		return list;
	}

	private List<SendingItem> sendMailDB(String email_text, List<String> emails, String subject) {
		String sql = "{call dbo.make_send_email ?, ?, ?}";
		List<SendingItem> list = new ArrayList<>(emails.size());
		for (String email : emails) {
			String res = "0";
			try {
				ems.executeUpdate(sql, email_text, subject, email);
			} catch (Exception e) {
				log.error("sendMail", e);
				res = e.getMessage();
			}
			SendingItem si = new SendingItem();
			si.setMail(email);
			si.setStatus(res);
			list.add(si);
		}
		return list;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SendingItem> sendMail(String email_text, List<String> emails, String subject) {
//		final String smtpServer = "wonderworksinvestments.com";
		final String fromAddress = "noreply@wonderworksinvestments.com";
//		final String userName = fromAddress;
//		final String password = "D6c8W3g2";
		final String body = email_text;

		List<SendingItem> result = new ArrayList<>(emails.size());

		try {
			for (String toAddress : emails) {
				SendingItem si = new SendingItem();
				si.setMail(toAddress);
				si.setStatus("Отправление");
				result.add(si);

				if (toAddress.contains("prbb.ru")) {
					si.setStatus("Отправление отменено. В отправителях домен prbb.ru");
					continue;
				}

				try {
					// create message
					MimeMessage message = new MimeMessage(mailSessionWWI);
					message.setFrom(new InternetAddress(fromAddress));
					//message.setReplyTo(new Address[] { new InternetAddress(mailReplyTo) });
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
					message.setHeader("Content-Type", "text/plain; charset='UTF-8'");
					message.setSubject(subject, "UTF-8");
					message.setText(body, "UTF-8");
					Transport.send(message);

					si.setStatus("Отправлено");
				} catch (Exception e) {
					log.error("sendMail", e);
					si.setStatus(e.getMessage());
				}

				logEmail(toAddress, body, si.getStatus());
			}
		} catch (Exception e) {
			log.error("sendMail", e);
			SendingItem si = new SendingItem();
			si.setMail("ERROR");
			si.setStatus(e.getMessage());
			result.add(si);
		}

		return result;
	}

	private int logEmail(String email, String text, String result) {
		try {
			String sql = "{call dbo.iu_email_log ?, ?, ?}";
			return ems.executeUpdate(sql, text, email, result);
		} catch (Exception e) {
			log.error("iu_email_log", e);
		}
		return 0;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SendingItem> sendSms(Number service, String text, List<String> phones, Number type) {
		text = text.replaceAll("\u20AC", "EUR");
		text = text.replaceAll("\u00A5", "JPY");
		text = text.replaceAll("\u00A3", "GBP");

		switch (service.intValue()) {
		case 0:
			return sendSmsMFMSru(text, phones, type);

		case 1:
			return sendSmsSMSru(text, phones);

		default:
			throw new IllegalArgumentException("Unknown service for sending SMS " + service);
		}
	}

	private List<SendingItem> sendSmsDB(String sms_text, List<String> phones, Number type) {
		String sql = "{call dbo.MakeSendSmsJava ?, ?, 2}";
		List<SendingItem> list = new ArrayList<>(phones.size());
		for (String phone : phones) {
			Object obj = ems.getSelectItem(Object.class, sql, sms_text, phone);
			String res = Utils.toString(obj);

			SendingItem si = new SendingItem();
			si.setMail(phone);
			si.setStatus("0".equals(res) ? "0" : res);
			
			try {
				if ("0".equals(res)) {
					Thread.sleep(4000);
				}
			} catch (InterruptedException e) {
				log.error("sendSms", e);
			}

			list.add(si);
		}
		return list;
	}

	private List<SendingItem> sendSmsSMSru(String text, List<String> phones) {
		String api_id = "33bd77b5-d915-c964-a184-a6e07b15b226";
//		String from = "LIFE";

		StringBuilder to = new StringBuilder(phones.size() * 10);
		try {
			URI uri = new URIBuilder()
					.setScheme("http")
					.setHost("sms.ru")
					.setPath("/sms/send")
					.build();

			HttpPost httpPost = new HttpPost(uri);

			for (String phone : phones) {
				to.append(phone);
				to.append(',');
			}
			to.setLength(to.length() - 1);

			List<NameValuePair> nvps = new ArrayList<>();
			//nvps.add(new BasicNameValuePair("test", "1"));
			nvps.add(new BasicNameValuePair("api_id", api_id));
			// TODO nvps.add(new BasicNameValuePair("from", from));
			nvps.add(new BasicNameValuePair("to", to.toString()));
			nvps.add(new BasicNameValuePair("text", text));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

			String responseBody;
			try (CloseableHttpClient httpclient = HttpClients.createSystem()) {
				log.debug("Executing request " + httpPost.getRequestLine());
				responseBody = httpclient.execute(httpPost, new ResponseHandler<String>() {

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
			}

			String[] lines = responseBody.split("\n");
			
			if (Integer.parseInt(lines[0]) != 100) {
				SendingItem si = new SendingItem();
				si.setMail(to.toString());
				si.setStatus(responseBody);
				return Arrays.asList(si);
			}

			List<SendingItem> result = new ArrayList<>(phones.size());
			for (int i = 0; i < phones.size(); i++) {
				SendingItem si = new SendingItem();
				si.setMail(phones.get(i));
				si.setStatus(lines[i + 1]);
				result.add(si);
			}
			return result;
		} catch (Exception e) {
			log.error("executeHttpRequest", e);
			throw new RuntimeException(e);
		}

	}

	private List<SendingItem> sendSmsMFMSru(String text, List<String> phones, Number type) {
		final boolean isSecure = false;

		if (Utils.isDebug()) {
			List<SendingItem> res = new ArrayList<>(phones.size());
			for (String phone : phones) {
				SendingItem e = new SendingItem();
				e.setMail(phone);
				e.setStatus("Отправлено для отладки");
				res.add(e);
			}
			return res;
		}

		try {
			URIBuilder uriBuilder = new URIBuilder()
					.setHost("gate10.mfms.ru")
					.setPath("/lifeinvestments/connector0/service");
			if (isSecure) {
				uriBuilder.setScheme("https");
				uriBuilder.setPort(9404);
			} else {
				uriBuilder.setScheme("http");
				uriBuilder.setPort(9403);
			}
			URI uri = uriBuilder.build();

			Map<Number, String> map = new HashMap<>(phones.size());

			String xml = createXmlConsumeOutMessageRequest(map, text, phones, type);

			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new StringEntity(xml, "UTF-8"));
			log.debug("Executing request {}", httpPost.getRequestLine());
			log.debug("Executing request\n{}", xml);

			try (CloseableHttpClient httpclient = HttpClients.createSystem()) {
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
				log.debug("Response {}", responseBody);
				return parseConsumeOutMessageResponse(map, responseBody);
			}
		} catch (Exception e) {
			log.error("executeHttpRequest", e);
			String status = e.getMessage();
			List<SendingItem> result = new ArrayList<>();
			for (String phone : phones) {
				SendingItem item = new SendingItem();
				item.setMail(phone);
				item.setStatus(status);
				result.add(item);
			}
			return result;
		}
	}

	private String createXmlConsumeOutMessageRequest(Map<Number, String> map, String commonContent, List<String> phones, Number type) {
		final String login = "lifein0";
		final String password = "JWtwRait";

		// <?xml version="1.0" encoding="utf-8"?>
		// <consumeOutMessageRequest>
		// <header>
		// <auth>
		// <login>login</login>
		// <password>password</password>
		// </auth>
		// </header>
		// <payload>
		// <outMessageCommon>
		// <subject>TheBank</subject>
		// <priority>high</priority>
		// <startTime>2007-10-29 11:00:00</startTime>
		// <validityPeriodMinutes>20</validityPeriodMinutes>
		// <contentType>text</contentType>
		// <content>message text</content>
		// <comment>collection department</comment>
		// </outMessageCommon>
		// <outMessageList>
		// <outMessage clientId="1">
		// <subject>TheBank</subject>
		// <address>79035555555</address>
		// <priority>high</priority>
		// <startTime>2007-10-29 11:00:00</startTime>
		// <contentType>text</contentType>
		// <content>message text</content>
		// <comment>collection department</comment>
		// </outMessage>
		// <outMessage clientId="2">
		// <address>79035555555</address>
		// <content>message text</content>
		// </outMessage>
		// <outMessage clientId="3">
		// <address>79035555555</address>
		// </outMessage>
		// <outMessage clientId="4">
		// <address>6666666</address>
		// </outMessage>
		// </outMessageList>
		// </payload>
		// </consumeOutMessageRequest>

		StringWriter sw = new StringWriter();

		try {
			XMLStreamWriter xmlWriter = XMLOutputFactory.newFactory().createXMLStreamWriter(sw);
			try {
				xmlWriter.writeStartDocument("utf-8", "1.0");
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeStartElement("consumeOutMessageRequest");
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeStartElement("header");
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeStartElement("auth");

				xmlWriter.writeStartElement("login");
				xmlWriter.writeCharacters(login);
				xmlWriter.writeEndElement();

				xmlWriter.writeStartElement("password");
				xmlWriter.writeCharacters(password);
				xmlWriter.writeEndElement();

				xmlWriter.writeEndElement();
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeEndElement();
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeStartElement("payload");
				xmlWriter.writeCharacters("\n");
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeStartElement("outMessageCommon");
				xmlWriter.writeCharacters("\n");

//				xmlWriter.writeStartElement("subject");
//				xmlWriter.writeCharacters("LIFE");
//				xmlWriter.writeEndElement();
//				xmlWriter.writeCharacters("\n");

				xmlWriter.writeStartElement("content");
				xmlWriter.writeCharacters(commonContent);
				xmlWriter.writeEndElement();
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeEndElement();
				xmlWriter.writeCharacters("\n");
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeStartElement("outMessageList");
				xmlWriter.writeCharacters("\n");

				for (String phone : phones) {
					xmlWriter.writeStartElement("outMessage");
					Number clientId = logSmsGetId(phone, commonContent, type);
					xmlWriter.writeAttribute("clientId", clientId.toString());

					xmlWriter.writeStartElement("address");
					xmlWriter.writeCharacters(phone);
					xmlWriter.writeEndElement();

					map.put(clientId, phone);

					xmlWriter.writeEndElement();
					xmlWriter.writeCharacters("\n");
				}

				xmlWriter.writeEndElement();
				xmlWriter.writeCharacters("\n");
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeEndElement();
				xmlWriter.writeCharacters("\n");

				xmlWriter.writeEndElement();

				xmlWriter.writeEndDocument();
			} finally {
				xmlWriter.close();
			}
		} catch (XMLStreamException e) {
			throw new RuntimeException(e);
		}

		return sw.toString();
	}

	private Number logSmsGetId(String phone, String sms_text, Number sms_type) {
		String sql = "{call dbo.iu_sms_log 'i', ?, ?, ?}";
		return ems.getSelectItem(Number.class, sql, sms_text, phone, sms_type);
	}

	private int logSms(String id, String response) {
		try {
			String sql = "{call dbo.iu_sms_log 'u', null, null, null, ?, ?}";
			return ems.executeUpdate(sql, id, response);
		} catch (Exception e) {
			log.error("iu_sms_log", e);
		}
		return 0;
	}

	private List<SendingItem> parseConsumeOutMessageResponse(Map<Number, String> map, String xml) {
		List<SendingItem> result = new ArrayList<>();
		try {
			XMLStreamReader sr = XMLInputFactory.newFactory().createXMLStreamReader(new StringReader(xml));
			try {
				SendingItem item = null;
				while (sr.hasNext()) {
					int et = sr.next();
					if (XMLStreamConstants.START_ELEMENT == et) {
						String localName = sr.getLocalName();

						if ("outMessage".equals(localName)) {
							String clientId = sr.getAttributeValue(null, "clientId");
							String providerId = sr.getAttributeValue(null, "providerId");
							item = new SendingItem();
							item.setMail(clientId);
							item.setStatus(providerId);
							result.add(item);
						}

						if ("code".equals(localName)) {
							sr.next();
							String text = sr.getText();
							if (item != null) {
								String clientId = item.getMail();
								String phone = map.get(clientId);
								item.setMail(phone);
								item.setStatus(text + "/" + item.getStatus());
								logSms(clientId, item.getStatus());
							} else {
								item = new SendingItem();
								item.setMail("RESULT");
								item.setStatus(text);
								result.add(item);
							}
							item = null;
						}
					}
				}
			} finally {
				sr.close();
			}
		} catch (Exception e) {
			log.error("parseConsumeOutMessageResponse", e);
		}
		return result;
	}

	public List<SimpleItem> findComboPhone(String query) {
		String sql = "select value from dbo.ncontacts_request_v where type != 'E-mail'";
		String where = " and lower(name) like ?";
		return ems.getComboListName(sql, where, query);
	}

	public List<SimpleItem> findComboMail(String query) {
		String sql = "select value from dbo.ncontacts_request_v where type = 'E-mail'";
		String where = " and lower(name) like ?";
		return ems.getComboListName(sql, where, query);
	}

	public String getAnalitic() {
		String sql = "{call dbo.sms_template_proc}";
		return Utils.toString(ems.getSelectItem(String.class, sql));
	}

	public String getTrader() {
		String sql = "{call dbo.sms_template_trader}";
		return ems.getSelectItem(String.class, sql);
	}

}
