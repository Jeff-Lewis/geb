/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.prbb.middleoffice.domain.NoConformityItem;

/**
 * Нет соответствия
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class NoConformityDaoImpl implements NoConformityDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<NoConformityItem> show() {
		// {call dbo.mo_WebGet_DealBlmTickerUnSet_sp}
		final List<NoConformityItem> list = new ArrayList<NoConformityItem>();
		for (long i = 1; i < 11; ++i) {
			final NoConformityItem item = new NoConformityItem();
			item.setId(i);
			item.setTradeNum("TradeNum" + i);
			item.setDate(null);
			item.setSecShortName("SecShortName" + i);
			item.setOperation("Operation" + i);
			list.add(item);
		}
		return list;
	}

	@Override
	public void delete(Long[] ids) {
		// "{call dbo.mo_WebSet_dTickerUnSetDeals_sp ?}"

	}

}
