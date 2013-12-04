/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.NotEnoughQuotationsItem;

/**
 * Не хватает котировок
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class NotEnoughQuotationsDaoImpl implements NotEnoughQuotationsDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<NotEnoughQuotationsItem> show() {
		String sql = "select * from mo_WebGet_QuotesNotExist_v";
		Query q = em.createNativeQuery(sql, NotEnoughQuotationsItem.class);
		return q.getResultList();
	}

}
