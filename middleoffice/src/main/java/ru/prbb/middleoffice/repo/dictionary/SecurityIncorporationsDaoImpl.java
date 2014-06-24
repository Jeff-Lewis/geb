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

import ru.prbb.middleoffice.domain.SecurityIncorporationItem;
import ru.prbb.middleoffice.domain.SecurityIncorporationListItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Регистрация инструментов
 * 
 * @author RBr
 * 
 */
@Repository
public class SecurityIncorporationsDaoImpl extends BaseDaoImpl implements SecurityIncorporationsDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityIncorporationListItem> findAll() {
		String sql = "{call dbo.mo_WebGet_SecurityIncorporations_sp}";
		Query q = em.createNativeQuery(sql, SecurityIncorporationListItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@Override
	public SecurityIncorporationItem findById(Long id) {
		String sql = "select * from mo_WebGet_SecurityIncorporations_v where id = ?";
		Query q = em.createNativeQuery(sql, SecurityIncorporationItem.class)
				.setParameter(1, id);
		return (SecurityIncorporationItem) q.getSingleResult();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int put(Long security, Long country, Date dateBegin) {
		String sql = "{call dbo.mo_WebSet_putSecurityIncorporations_sp ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, security)
				.setParameter(2, country)
				.setParameter(3, dateBegin);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int updateById(Long id, Date dateBegin, Date dateEnd) {
		String sql = "{call dbo.mo_WebSet_udSecurityIncorporations_sp 'u', ?, ?, ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id)
				.setParameter(2, dateBegin)
				.setParameter(3, dateEnd);
		storeSql(sql, q);
		return q.executeUpdate();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int deleteById(Long id) {
		String sql = "{call dbo.mo_WebSet_udSecurityIncorporations_sp 'd', ?}";
		Query q = em.createNativeQuery(sql)
				.setParameter(1, id);
		storeSql(sql, q);
		return q.executeUpdate();
	}

}
