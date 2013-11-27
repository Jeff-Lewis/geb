/**
 * 
 */
package ru.prbb.analytics.repo.portfolio;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

	@Override
	public List<ViewPortfolioSecurityItem> getSecurities() {
		String sql = "select * from dbo.equity_request_v";
		return em.createQuery(sql, ViewPortfolioSecurityItem.class).getResultList();
	}

	@Override
	public List<ViewPortfolioItem> getPortfolio() {
		String sql = "select id_sec, security_code from dbo.securities where portfolio = 'portfolio'";
		return em.createQuery(sql, ViewPortfolioItem.class).getResultList();
	}

	@Override
	public int[] put(String[] ids) {
		String sql = "update dbo.securities set portfolio='portfolio' where security_code=:id";
		int i = 0;
		int[] res = new int[ids.length];
		for (String id : ids) {
			try {
				res[i++] = em.createQuery(sql).setParameter(1, id).executeUpdate();
			} catch (DataAccessException e) {
				// TODO: handle exception
			}
		}
		return res;
	}

	@Override
	public int[] del(String[] ids) {
		String sql = "update dbo.securities set portfolio='' where security_code=:id";
		int i = 0;
		int[] res = new int[ids.length];
		for (String id : ids) {
			try {
				res[i++] = em.createQuery(sql).setParameter(1, id).executeUpdate();
			} catch (DataAccessException e) {
				// TODO: handle exception
			}
		}
		return res;
	}

}
