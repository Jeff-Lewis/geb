/**
 * 
 */
package ru.prbb.jobber.repo;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.SendingItem;

/**
 * @author RBr
 * 
 */
//@Service
public class SendingDaoImplDB extends SendingDaoImpl
{

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public SendingItem sendMail(String email_text, String email) {
		String subject = "info";

		String sql = "{call dbo.make_send_email ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, email_text)
				.setParameter(2, subject)
				.setParameter(3, email);
		String res = "0";
		try {
			storeSql(sql, q);
			executeUpdate(q, sql);
		} catch (Exception e) {
			log.error("sendMail", e);
			res = e.getMessage();
		}
		SendingItem si = new SendingItem();
		si.setMail(email);
		si.setStatus(res);
		return si;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public SendingItem sendSms(String sms_text, String phone) {
		String sql = "{call dbo.MakeSendSmsJava ?, ?, 2}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, sms_text)
				.setParameter(2, phone);
		Object obj = getSingleResult(q, sql);
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

		return si;
	}

}
