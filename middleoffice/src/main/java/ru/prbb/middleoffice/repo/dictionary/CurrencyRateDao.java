/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.CurrencyRateItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class CurrencyRateDao
{

	@Autowired
	private EntityManagerService ems;

	public List<CurrencyRateItem> findAll(ArmUserInfo user, Date dated, String iso) {
		String sql = "{call dbo.mo_WebGet_CurrencyRate_sp ?, ?}";
		return ems.getSelectList(user, CurrencyRateItem.class, sql, dated, iso);
	}

}
