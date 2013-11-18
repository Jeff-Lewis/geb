/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.DividendItem;

/**
 * Дивиденды
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class DividendsDaoImpl implements DividendsDao
{
	@Autowired
	private EntityManager em;

	/**
	 * @return
	 */
	@Override
	public List<DividendItem> findAll() {
		// {call dbo.mo_WebGet_Dividends_sp null, ?, ?, ?, ?, ?, ?}
		return new ArrayList<DividendItem>();
	}

	/**
	 * @param id
	 * @return
	 */
	@Override
	public DividendItem findById(Long id) {
		// "{call dbo.mo_WebGet_Dividends_sp ?}"
		return null;
	}

	@Override
	public Long put() {
		// "{call dbo.mo_WebSet_putDividends_sp ?, ?, ?, ?, ?, ?, ?, ?}"
		return null;
	}

	/**
	 * @param id
	 * @param value
	 * @return
	 */
	@Override
	public Long updateById(Long id, DividendItem value) {
		// "{call dbo.mo_WebSet_uActualDividends_sp ?, ?}"
		return id;
	}

	@Override
	public Long updateAttrById(Long id, String type, String value) {
		// "{call dbo.mo_WebSet_setDividendAttributes_sp ?, ?, ?}"
		return id;
	}

	/**
	 * @param id
	 */
	@Override
	public void deleteById(Long id) {
		// "{call dbo.mo_WebSet_dDividends_sp ?}"
	}

}
