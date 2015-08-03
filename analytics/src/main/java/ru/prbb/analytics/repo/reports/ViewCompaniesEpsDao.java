/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.ViewCompaniesEpsItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * EPS по компаниям
 * 
 * @author RBr
 */
@Service
public class ViewCompaniesEpsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewCompaniesEpsItem> execute(ArmUserInfo user) {
		String sql = "{call dbo.anca_WebGet_EquityEPSinfo_sp}";
		return ems.getSelectList(user, ViewCompaniesEpsItem.class, sql);
	}

}
