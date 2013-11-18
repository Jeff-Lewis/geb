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

import ru.prbb.middleoffice.domain.NotEnoughQuotationsItem;

/**
 * Не хватает котировок
 * 
 * @author RBr
 * 
 */
@Repository
@Transactional
public class NotEnoughQuotationsDaoImpl implements NotEnoughQuotationsDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<NotEnoughQuotationsItem> show() {
		// select * from mo_WebGet_QuotesNotExist_v
		final List<NotEnoughQuotationsItem> list = new ArrayList<NotEnoughQuotationsItem>();
		for (long i = 1; i < 11; ++i) {
			final NotEnoughQuotationsItem item = new NotEnoughQuotationsItem();
			item.setId_sec(i);
			item.setSecurityCode("SecurityCode" + i);
			item.setSecurityType("SecurityType" + i);
			item.setFirstTradeDate("FirstTradeDate" + i);
			list.add(item);
		}
		return list;
	}

}
