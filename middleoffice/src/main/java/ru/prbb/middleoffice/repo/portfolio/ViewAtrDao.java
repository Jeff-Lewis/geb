/**
 * 
 */
package ru.prbb.middleoffice.repo.portfolio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ru.prbb.ArmUserInfo;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.ViewAtrItem;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class ViewAtrDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewAtrItem> execute(ArmUserInfo user, Date begin, Date end, Long[] securities) {
		List<ViewAtrItem> resAll = new ArrayList<>();
		String sql = "{call dbo.mo_WebGet_ATR_sp ?, ?, ?}";
		for (Long security : securities) {
			Collection<ViewAtrItem> list = ems.getSelectList(user, ViewAtrItem.class, sql, security, begin, end);
			resAll.addAll(list);
		}
		return resAll;
	}
}
