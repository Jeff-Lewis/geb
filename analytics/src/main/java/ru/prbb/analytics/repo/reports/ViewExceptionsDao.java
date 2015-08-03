/**
 * 
 */
package ru.prbb.analytics.repo.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.ArmUserInfo;
import ru.prbb.analytics.domain.ViewExceptionsItem;
import ru.prbb.analytics.services.EntityManagerService;

/**
 * Отчёт по исключениям
 * 
 * @author RBr
 */
@Service
public class ViewExceptionsDao
{

	@Autowired
	private EntityManagerService ems;

	public List<ViewExceptionsItem> execute(ArmUserInfo user) {
		String sql = "{call dbo.output_equities_exceptions}";
		return ems.getSelectList(user, ViewExceptionsItem.class, sql);
	}

}
