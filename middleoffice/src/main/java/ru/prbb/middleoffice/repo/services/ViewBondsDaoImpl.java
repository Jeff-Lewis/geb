/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Редактирование облигаций
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ViewBondsDaoImpl implements ViewBondsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public void put(Long code, String ticker) {
		// "{call dbo.mo_WebSet_putDealsSecuritiesMapping_sp ?, ?, null, 5}"
	}

	@Override
	public void del(Long code, String ticker) {
		// "{call dbo.mo_WebSet_dDealsSecuritiesMapping_sp ?, ?}"
	}

}
