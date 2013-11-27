/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewExceptionsItem;

/**
 * Отчёт по исключениям
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class ViewExceptionsDaoImpl implements ViewExceptionsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<ViewExceptionsItem> execute() {
		String sql = "{call dbo.output_equities_exceptions}";
		return em.createQuery(sql, ViewExceptionsItem.class).getResultList();
	}

}
