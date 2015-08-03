/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.ArrayList;
import java.util.List;

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
	public List<SendingItem> sendMail(String email_text, List<String> emails, String subject) {
		List<SendingItem> list = new ArrayList<>(emails.size());
		for (String email : emails) {
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
			list.add(si);
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SendingItem> sendSms(Number service, String sms_text, List<String> phones, Number type) {
		List<SendingItem> list = new ArrayList<>(phones.size());
		for (String phone : phones) {
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

			list.add(si);
		}
		return list;
	}

}
