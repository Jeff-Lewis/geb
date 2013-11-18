/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Редактирование фьючерсов
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class ViewFuturesDaoImpl implements ViewFuturesDao
{
	@Autowired
	private EntityManager em;

	@Override
	public void put(Long code, String deal, Long futures) {
		// "{call dbo.blm_cmdt_mapping ?, ?, ?, 2}"
	}

	@Override
	public void del(Long code, String deal) {
		// "{call dbo.blm_cmdt_delete ?, ?}"
	}

}
