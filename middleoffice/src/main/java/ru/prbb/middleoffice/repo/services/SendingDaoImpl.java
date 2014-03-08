/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
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

/**
 * @author RBr
 * 
 */
@Service
public class SendingDaoImpl implements SendingDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	private List<String> getMailByGroup(String group) {
		String sql = "select value from dbo.ncontacts_groups_view where gname=? and type='E-mail'";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, group);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	private List<String> getPhoneByGroup(String group) {
		String sql = "select value from dbo.ncontacts_groups_view where gname=? and type='Мобильный телефон'";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, group);
		return q.getResultList();
	}

	private SendingItem sendMail(final String text, final String receiver) {
		String sql = "{=call dbo.make_send_email ?, 'info', ?}";
		Query q = em.createNativeQuery(sql, String.class)
				.setParameter(1, text)
				.setParameter(2, receiver);
		Object sr = q.getSingleResult();
		String res = Utils.toString(sr);
		/*
		String res = jdbcTemplate.execute(sql,
				new CallableStatementCallback<String>() {

					@Override
					public String doInCallableStatement(CallableStatement ps) throws SQLException {
						ps.registerOutParameter(1, Types.VARCHAR);
						ps.setString(2, text);
						ps.setString(3, receiver);
						ps.execute();
						return ps.getString(1);
					}
				});
		*/
		SendingItem si = new SendingItem();
		si.setMail(receiver);
		si.setStatus("0".equals(res) ? "0" : "-1");
		return si;
	}

	private SendingItem sendSms(final String text, final String receiver) {
		String sql = "{=call dbo.MakeSendSmsJava ?, ?, 2}";
		Query q = em.createNativeQuery(sql, String.class)
				.setParameter(1, text)
				.setParameter(2, receiver);
		Object sr = q.getSingleResult();
		String res = Utils.toString(sr);
		/*
		String res = jdbcTemplate.execute(sql,
				new CallableStatementCallback<String>() {

					@Override
					public String doInCallableStatement(CallableStatement cs) throws SQLException {
						cs.registerOutParameter(1, Types.VARCHAR);
						cs.setString(2, text);
						cs.setString(3, receiver);
						cs.execute();
						return cs.getString(1);
					}
				});
		*/
		SendingItem si = new SendingItem();
		si.setMail(receiver);
		si.setStatus("0".equals(res) ? "0" : "-1");
		return si;
	}

	/**
	 * @param recMails
	 *            E-mail
	 * @param recPhones
	 *            SMS
	 */
	@Override
	public List<SendingItem> execute(String text, String recPhones, String recMails) {
		List<SendingItem> res = new ArrayList<>();

		if (Utils.isNotEmpty(recMails)) {
			String mails[] = recMails.split(",");
			for (String mail : mails) {
				if (Utils.isNotEmpty(mail)) {
					if (mail.contains("@")) {
						res.add(sendMail(text, mail));
					} else {
						List<String> groupMails = getMailByGroup(mail);
						for (String groupMail : groupMails) {
							res.add(sendMail(text, groupMail));
						}
					}
				}
			}
		}

		if (Utils.isNotEmpty(recPhones)) {
			String phones[] = recPhones.split(",");
			for (String phone : phones) {
				if (Utils.isNotEmpty(phone)) {
					if (phone.startsWith("+")) {
						res.add(sendSms(text, phone));
						sleep();
					} else {
						List<String> groupPhones = getPhoneByGroup(phone);
						for (String groupPhone : groupPhones) {
							res.add(sendSms(text, groupPhone));
							sleep();
						}
					}
				}
			}
		}

		return res;
	}

	private void sleep() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException ie)
		{
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public String getAnalitic() {
		String sql = "{call dbo.sms_template_proc}";
		Query q = em.createNativeQuery(sql);
		return Utils.toString(q.getSingleResult());
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public String getTrader() {
		String sql = "{call dbo.sms_template_trader}";
		Query q = em.createNativeQuery(sql);
		return Utils.toString(q.getSingleResult());
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findComboPhone(String query) {
		String sql = "select value from ncontacts_request_v where type != 'E-mail'";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " and lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, '%' + query.toLowerCase() + '%');
		}
		return Utils.toSimpleItem(q.getResultList());
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public List<SimpleItem> findComboMail(String query) {
		String sql = "select value from ncontacts_request_v where type = 'E-mail'";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql);
		} else {
			sql += " and lower(name) like ?";
			q = em.createNativeQuery(sql)
					.setParameter(1, '%' + query.toLowerCase() + '%');
		}
		return Utils.toSimpleItem(q.getResultList());
	}

}
