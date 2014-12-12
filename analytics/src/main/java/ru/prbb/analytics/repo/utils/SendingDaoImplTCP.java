/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ru.prbb.analytics.domain.SendingItem;

/**
 * @author RBr
 * 
 */
//@Service
public class SendingDaoImplTCP extends SendingDaoImpl
{
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
	public SendingItem sendSms(String sms_text, String phone) {
		String res = null;
		String api_id = "fdee122b-3a2e-59d4-5951-54ace233fd42";

		try {
			if ("0".equals(res)) {
				Thread.sleep(4000);
			}
		} catch (InterruptedException e) {
			log.error("sendSms", e);
		}

		SendingItem si = new SendingItem();
		si.setMail(phone);
		si.setStatus(res);
		return si;
	}

}
