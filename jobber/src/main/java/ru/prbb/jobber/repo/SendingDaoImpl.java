package ru.prbb.jobber.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.jobber.domain.SimpleItem;
import ru.prbb.jobber.repo.BaseDaoImpl;

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
