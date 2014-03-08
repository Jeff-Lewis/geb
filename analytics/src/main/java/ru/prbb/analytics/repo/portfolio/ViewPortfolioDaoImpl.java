/**
 * 
 */
package ru.prbb.analytics.repo.portfolio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewPortfolioItem;

/**
 * Добавление организаций в Portfolio
 * 
 * @author RBr
 * 
 */
@Repository
public class ViewPortfolioDaoImpl implements ViewPortfolioDao
{
	@Autowired
	private EntityManager em;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewPortfolioItem> findAll() {
		String sql = "select id_sec, security_code from dbo.equity_request_v";
		Query q = em.createNativeQuery(sql, ViewPortfolioItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewPortfolioItem> findAllPortfolio() {
		String sql = "select id_sec, security_code from dbo.securities where portfolio = 'portfolio'";
		Query q = em.createNativeQuery(sql, ViewPortfolioItem.class);
		return q.getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] put(Long[] ids) {
		String sql = "update dbo.securities set portfolio='portfolio' where id_sec=?";
		int i = 0;
		int[] res = new int[ids.length];
		Query q = em.createNativeQuery(sql);
		for (Long id_sec : ids) {
			try {
				q.setParameter(1, id_sec);
				res[i++] = q.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return res;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public int[] del(Long[] ids) {
		String sql = "update dbo.securities set portfolio='' where id_sec=?";
		int i = 0;
		int[] res = new int[ids.length];
		Query q = em.createNativeQuery(sql);
		for (Long id_sec : ids) {
			try {
				q.setParameter(1, id_sec);
				res[i++] = q.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return res;
	}

}
