/**
 * 
 */
package ru.prbb.middleoffice.repo.dictionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.middleoffice.domain.CurrenciesItem;
import ru.prbb.middleoffice.domain.SimpleItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Валюты
 * 
 * @author RBr
 */
@Service
public class CurrenciesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<CurrenciesItem> findAll(ArmUserInfo user) {
		String sql = "{call dbo.mo_WebGet_Currency_sp}";
		return ems.getSelectList(user, CurrenciesItem.class, sql);
	}

	public List<SimpleItem> findCombo(String query) {
		String sql = "select id, name from dbo.mo_WebGet_ajaxCurrency_v";
		String where = " where lower(name) like ?";
		return ems.getComboList(sql, where, query);
	}
}
