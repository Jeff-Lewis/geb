/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.CountryItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Страны
 * 
 * @author RBr
 */
@Service
public class CountriesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<CountryItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_Countries_sp}";
		return ems.getSelectList(user, CountryItem.class, sql);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxCountries_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}

}
