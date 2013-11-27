/**
 * 
 */
package ru.prbb.analytics.repo.utils;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
@Transactional
public class BrokersDaoImpl implements BrokersDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<BrokerItem> findAll() {
		String sql = "{call dbo.anca_WebGet_SelectBrokers_sp}";
		return em.createQuery(sql, BrokerItem.class).getResultList();
	}

	@Override
	public BrokerItem findById(Long id) {
		String sql = "{call dbo.anca_WebGet_SelectBrokers_sp :id}";
		return em.createQuery(sql, BrokerItem.class).setParameter(1, id).getSingleResult();
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
	@Override
	public int put(String full_name, Integer rating, String bloomberg_code,
			Integer cover_russian, String short_name) {
		String sql = "{call dbo.anca_WebSet_putBrokers_sp :full_name, :rating, :bloomberg_code, :cover_russian, :short_name}";
		return em.createQuery(sql).setParameter(1, full_name).setParameter(2, rating)
				.setParameter(3, bloomberg_code).setParameter(4, cover_russian).setParameter(5, short_name)
				.executeUpdate();
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
	@Override
	public int updateById(Long id, String full_name, Integer rating, String bloomberg_code,
			Integer cover_russian, String short_name) {
		String sql = "{call dbo.anca_WebSet_udBrokers_sp 'u', :id, :full_name, :rating, :bloomberg_code, :cover_russian, :short_name}";
		return em.createQuery(sql).setParameter(1, id).setParameter(2, full_name)
				.setParameter(3, rating).setParameter(4, bloomberg_code)
				.setParameter(5, cover_russian).setParameter(6, short_name)
				.executeUpdate();
	}

	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.anca_WebSet_udBrokers_sp 'd', :id}";
		return em.createQuery(sql).setParameter(1, id).executeUpdate();
	}

	@Override
	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.anca_WebGet_ajaxBrokers_v";
		if (Utils.isEmpty(query)) {
			return em.createQuery(sql, SimpleItem.class).getResultList();
		} else {
			sql += " where lower(name) like :q";
			query = '%' + query.toLowerCase() + '%';
			return em.createQuery(sql, SimpleItem.class).setParameter(1, query).getResultList();
		}
	}
}
