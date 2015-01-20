package ru.prbb.jobber.repo;

import java.sql.Timestamp;
import java.util.List;

import ru.prbb.jobber.domain.LogUserActionItem;

/**
 * @author RBr
 */
public interface UserHistory {

	List<LogUserActionItem> getHistory(Timestamp date_b, Timestamp date_e);

	int putHist(String command);

}
