package ru.prbb.jobber.repo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.SendMessageItem;
import ru.prbb.jobber.domain.SendingItem;
import ru.prbb.jobber.domain.SimpleItem;

/**
 * @author RBr
 * 
 */
public abstract class SendingDaoImpl extends BaseDaoImpl implements SendingDao {

	@Autowired
	protected EntityManager em;

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

	@Override
	public List<SendingItem> send(List<SendMessageItem> items) {
		List<SendingItem> list =new ArrayList<>(items.size());
		for (SendMessageItem item : items) {
			int type = item.getType().intValue();
			String subject = item.getSubj();
			String text = item.getText();
			String[] addrs = item.getAddrsArray();

			SendingItem res;
			switch (type) {
			case 0:
				for (String phone : addrs) {
					res = sendSms(text, phone);
					list.add(res);
				}
				break;

			case 1:
				for (String email : addrs) {
					res = sendMail(text, email, subject);
					list.add(res);
				}
				break;

			default:
				String status = "Unknow message type: " + item.getType();
				res = new SendingItem();
				res.setMail(subject);
				res.setStatus(status);
				log.warn(status);
			}
		}
		return list;
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

}
