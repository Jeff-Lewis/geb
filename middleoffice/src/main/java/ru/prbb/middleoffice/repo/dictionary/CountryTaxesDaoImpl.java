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

import ru.prbb.middleoffice.domain.CountryTaxItem;

/**
 * Налоги по странам
 * 
 * @author RBr
 * 
 */
@Repository
public class CountryTaxesDaoImpl implements CountryTaxesDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryTaxItem> findAll() {
		String sql = "{call dbo.mo_WebGet_CountryTaxes_sp}";
		Query q = em.createNativeQuery(sql, CountryTaxItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public CountryTaxItem findById(Long id) {
		String sql = "{call dbo.mo_WebGet_CountryTaxes_sp ?}";
		Query q = em.createNativeQuery(sql, CountryTaxItem.class)
				.setParameter(1, id);
		return (CountryTaxItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(Long securityType, Long country, Long broker, Double value, Date dateBegin) {
		String sql = "{call dbo.mo_WebSet_putCountryTaxes_sp ?, ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, securityType)
				.setParameter(2, country)
				.setParameter(3, broker)
				.setParameter(4, value)
				.setParameter(5, dateBegin);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, Double value, Date dateBegin, Date dateEnd) {
		String sql = "{call dbo.mo_WebSet_udCountryTaxes_sp 'u', ?, ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, value)
				.setParameter(3, dateBegin)
				.setParameter(4, dateEnd);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udCountryTaxes_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		return q.executeUpdate();
	}

}
