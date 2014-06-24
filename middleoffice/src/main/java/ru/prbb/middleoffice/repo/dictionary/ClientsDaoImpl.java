/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.middleoffice.domain.ClientsItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Клиенты
 * 
 * @author RBr
 * 
 */
@Repository
public class ClientsDaoImpl extends BaseDaoImpl implements ClientsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	@SuppressWarnings("unchecked")
	public List<ClientsItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SelectClients_sp}";
		Query q = em.createNativeQuery(sql, ClientsItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public ClientsItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_SelectClients_sp ?}";
		Query q = em.createNativeQuery(sql, ClientsItem.class)
				.setParameter(1, id);
		return (ClientsItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String name, String comment, Long country_id, Date date_begin, Date date_end) {
		String sql = "{call dbo.mo_WebSet_putClients_sp ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, name)
				.setParameter(2, comment)
				.setParameter(3, country_id)
				.setParameter(4, date_begin)
				.setParameter(5, date_end);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, String name, String comment, Long country_id, Date date_begin, Date date_end) {
		String sql = "{call dbo.mo_WebSet_udClients_sp 'u', ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, name)
				.setParameter(3, comment)
				.setParameter(4, country_id)
				.setParameter(5, date_begin)
				.setParameter(6, date_end);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udClients_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxClient_v";
		Query q;
		if (Utils.isEmpty(query)) {
			q = em.createNativeQuery(sql, SimpleItem.class);
		} else {
			sql += " where lower(name) like ?";
			q = em.createNativeQuery(sql, SimpleItem.class)
					.setParameter(1, query.toLowerCase() + '%');
		}
		return q.getResultList();
	}
}
