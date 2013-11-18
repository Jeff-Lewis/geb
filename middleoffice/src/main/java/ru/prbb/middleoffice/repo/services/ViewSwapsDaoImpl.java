/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Редактирование свопов
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ViewSwapsDaoImpl implements ViewSwapsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public void put(Long code, String deal) {
		// "{call dbo.mo_WebSet_putDealsSecuritiesMapping_sp ?, ?, null, 4}"
	}

	@Override
	public void del(Long code, String deal) {
		// "{call dbo.mo_WebSet_dDealsSecuritiesMapping_sp ?, ?}"
	}

}
