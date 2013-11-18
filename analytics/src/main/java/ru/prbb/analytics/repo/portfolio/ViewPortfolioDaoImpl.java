/**
 * 
 */
package ru.prbb.analytics.repo.portfolio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.analytics.domain.ViewPortfolioItem;

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
	public List<ViewPortfolioItem> getSecurities() {
		final List<ViewPortfolioItem> list = new ArrayList<ViewPortfolioItem>();
		for (long i = 1; i < 11; i++) {
			final ViewPortfolioItem item = new ViewPortfolioItem();
			item.setId_sec(i);
			item.setSecurity_code("SECURITY_" + i);
			list.add(item);
		}
		return list;
	}

	@Override
	public List<ViewPortfolioItem> getPortfolio() {
		final List<ViewPortfolioItem> list = new ArrayList<ViewPortfolioItem>();
		for (long i = 1; i < 11; i++) {
			final ViewPortfolioItem item = new ViewPortfolioItem();
			item.setId_sec(i);
			item.setSecurity_code("SECURITY_" + i);
			list.add(item);
		}
		return list;
	}

	@Override
	public void put(String[] ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void del(String[] ids) {
		// TODO Auto-generated method stub

	}

}
