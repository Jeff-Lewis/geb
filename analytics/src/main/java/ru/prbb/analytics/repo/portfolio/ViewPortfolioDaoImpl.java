/**
 * 
 */
package ru.prbb.analytics.repo.portfolio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewPortfolioItem;
import ru.prbb.analytics.domain.ViewPortfolioSecurityItem;

/**
 * Добавление организаций в Portfolio
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ViewPortfolioDaoImpl implements ViewPortfolioDao
{
	@Autowired
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewPortfolioSecurityItem> findAll() {
		String sql = "select * from dbo.equity_request_v";
		Query q = em.createNativeQuery(sql, ViewPortfolioSecurityItem.class);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewPortfolioItem> findAllPortfolio() {
		String sql = "select id_sec, security_code from dbo.securities where portfolio = 'portfolio'";
		Query q = em.createNativeQuery(sql, ViewPortfolioItem.class);
		return q.getResultList();
	}

	@Override
	public int[] put(String[] ids) {
		String sql = "update dbo.securities set portfolio='portfolio' where security_code=?";
		int i = 0;
		int[] res = new int[ids.length];
		Query q = em.createNativeQuery(sql);
		for (String id : ids) {
			try {
				q.setParameter(1, id);
				res[i++] = 0;//q.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return res;
	}

	@Override
	public int[] del(String[] ids) {
		String sql = "update dbo.securities set portfolio='' where security_code=?";
		int i = 0;
		int[] res = new int[ids.length];
		Query q = em.createNativeQuery(sql);
		for (String id : ids) {
			try {
				q.setParameter(1, id);
				res[i++] = 0;//q.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return res;
	}

}
