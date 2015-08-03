/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.CountryTaxItem;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Налоги по странам
 * 
 * @author RBr
 */
@Service
public class CountryTaxesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<CountryTaxItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_CountryTaxes_sp}";
		return ems.getSelectList(user, CountryTaxItem.class, sql);
	}

	public CountryTaxItem findById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebGet_CountryTaxes_sp ?}";
		return ems.getSelectItem(user, CountryTaxItem.class, sql, id);
	}

	public int put(ArmUserInfo user, Long securityType, Long country, Long broker, Double value, Date dateBegin, Long country_recipient_id) {
		String sql = "{call dbo.mo_WebSet_putCountryTaxes_sp ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.INSERT, user, sql, securityType, country, broker, value, dateBegin, country_recipient_id);
	}

	public int updateById(ArmUserInfo user, Long id, Double value, Date dateBegin, Date dateEnd, Long country_recipient_id) {
		String sql = "{call dbo.mo_WebSet_udCountryTaxes_sp 'u', ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.UPDATE, user, sql, id, value, dateBegin, dateEnd, country_recipient_id);
	}

	public int deleteById(ArmUserInfo user, Long id) {
		String sql = "{call dbo.mo_WebSet_udCountryTaxes_sp 'd', ?}";
		return ems.executeUpdate(AccessAction.DELETE, user, sql, id);
	}

}
