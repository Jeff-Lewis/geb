/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.ViewQuotesItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * Котировки
 * 
 * @author RBr
 */
@Service
public class ViewQuotesDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewQuotesItem> execute(ArmUserInfo user, Date begin, Date end, Long[] securities) {
		List<ViewQuotesItem> resAll = new ArrayList<>();
		String sql = "{call dbo.mo_WebGet_SelectQuotes_sp ?, ?, ?}";
		for (Long security : securities) {
			List<ViewQuotesItem> list = ems.getSelectList(user, ViewQuotesItem.class, sql, security, begin, end);
			resAll.addAll(list);
		}
		return resAll;
	}

}
