package ru.prbb.analytics.repo;

import java.sql.Date;
import java.util.List;

import ru.prbb.analytics.domain.LogUserActionItem;

/**
 * @author RBr
 */
public interface UserHistory {

	List<LogUserActionItem> getHistory(Date date_b, Date date_e);

	int putHist(String command);

}
