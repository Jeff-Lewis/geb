package ru.prbb.middleoffice.repo.services;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.ClientSortItem;
import ru.prbb.middleoffice.repo.BaseDaoImpl;

/**
 * Сортировка клиентов
 * 
 * @author RBr
 */
@Repository
public class ClientSortDaoImpl extends BaseDaoImpl implements ClientSortDao
{

	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientSortItem> showSelected() {
		String sql = "{call dbo.mo_WebGet_SelectClientSort_sp 1}";
		Query q = em.createNativeQuery(sql, ClientSortItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientSortItem> showUnselected() {
		String sql = "{call dbo.mo_WebGet_SelectClientSort_sp 0}";
		Query q = em.createNativeQuery(sql, ClientSortItem.class);
		return getResultList(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int action(Long id, Integer flag) {
		String sql = "{call dbo.mo_WebSet_ClientSort_sp ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, id);
		q.setParameter(2, flag);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int setDate(Long id, Date date_b) {
		String sql = "{call dbo.mo_WebSet_uDateClientSort_sp ?, ?}";
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, id);
		q.setParameter(2, date_b);
		storeSql(sql, q);
		return executeUpdate(q, sql);
	}
}
