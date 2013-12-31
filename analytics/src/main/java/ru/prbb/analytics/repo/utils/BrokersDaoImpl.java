/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.Utils;
import ru.prbb.analytics.domain.BrokerItem;
import ru.prbb.analytics.domain.SimpleItem;

/**
 * Справочник брокеров
 * 
 * @author RBr
 * 
 */
@Repository
public class BrokersDaoImpl implements BrokersDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<BrokerItem> findAll() {
		String sql = "{call dbo.anca_WebGet_SelectBrokers_sp}";
		Query q = em.createNativeQuery(sql, BrokerItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public BrokerItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_SelectBrokers_sp ?}";
		Query q = em.createNativeQuery(sql, BrokerItem.class)
				.setParameter(1, id);
		return (BrokerItem) q.getSingleResult();
	}

	/**
	 * @param full_name
	 *            varchar(255),
	 * @param rating
	 *            int,
	 * @param bloomberg_code
	 *            varchar(255),
	 * @param cover_russian
	 *            int = 0,
	 * @param short_name
	 *            varchar(50)
	 * @return
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(String full_name, Integer rating, String bloomberg_code,
			Integer cover_russian, String short_name) {
		String sql = "{call dbo.anca_WebSet_putBrokers_sp ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, full_name)
				.setParameter(2, rating)
				.setParameter(3, bloomberg_code)
				.setParameter(4, cover_russian)
				.setParameter(5, short_name);
		return q.executeUpdate();
	}

	/**
	 * @param action
	 *            varchar(1),
	 * @param id
	 *            numeric(18),
	 * @param full_name
	 *            varchar(255) = null,
	 * @param rating
	 *            int = null,
	 * @param bloomberg_code
	 *            varchar(255) = null,
	 * @param cover_russian
	 *            int = 0,
	 * @param short_name
	 *            varchar(50) = null
	 * @return
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, String full_name, Integer rating, String bloomberg_code,
			Integer cover_russian, String short_name) {
		String sql = "{call dbo.anca_WebSet_udBrokers_sp 'u', ?, ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, full_name)
				.setParameter(3, rating)
				.setParameter(4, bloomberg_code)
				.setParameter(5, cover_russian)
				.setParameter(6, short_name);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.anca_WebSet_udBrokers_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.anca_WebGet_ajaxBrokers_v";
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
