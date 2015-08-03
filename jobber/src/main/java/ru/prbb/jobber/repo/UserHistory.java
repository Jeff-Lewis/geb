package ru.prbb.jobber.repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.LogUserActionItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class UserHistory
{

	@Autowired
	private EntityManagerService ems;

	public List<LogUserActionItem> getHistory(Timestamp date_b, Timestamp date_e) {
		String sql = "{call dbo.WebGet_SelectHist_sp ?, ?}";
		return ems.getSelectList(LogUserActionItem.class, sql, date_b, date_e);
	}

}
