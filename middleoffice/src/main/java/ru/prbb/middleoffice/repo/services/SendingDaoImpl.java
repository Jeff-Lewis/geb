/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.SendingItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * @author RBr
 * 
 */
@Service
public class SendingDaoImpl extends BaseDaoImpl implements SendingDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getMailByGroup(String group) {
		String sql = "select value from dbo.ncontacts_groups_view where gname=? and type='E-mail'";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, group);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getPhoneByGroup(String group) {
		String sql = "select value from dbo.ncontacts_groups_view where gname=? and type='Мобильный телефон'";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, group);
		return getResultList(q, sql);
	}

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public String getAnalitic() {
		String sql = "{call dbo.sms_template_proc}";
		Query q = em.createNativeQuery(sql);
		return Utils.toString(getSingleResult(q, sql));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public String getTrader() {
		String sql = "{call dbo.sms_template_trader}";
		Query q = em.createNativeQuery(sql);
		return Utils.toString(getSingleResult(q, sql));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findComboPhone(String query) {
		String sql = "select value from dbo.ncontacts_request_v where type != 'E-mail'";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " and lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, '%' + query.toLowerCase() + '%');
		}
		return Utils.toSimpleItem(getResultList(q, sql));
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findComboMail(String query) {
		String sql = "select value from dbo.ncontacts_request_v where type = 'E-mail'";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " and lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, '%' + query.toLowerCase() + '%');
		}
		return Utils.toSimpleItem(getResultList(q, sql));
	}

}
