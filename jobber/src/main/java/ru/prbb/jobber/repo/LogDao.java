/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.LogContactItem;
import ru.prbb.jobber.domain.LogMessagesItem;
import ru.prbb.jobber.domain.LogSubscriptionItem;
import ru.prbb.jobber.services.EntityManagerService;

/**
 * Журнал отправки сообщений<br>
 * Журнал изменений справочника контактов<br>
 * Журнал подписки
 * 
 * @author RBr
 * 
 */
@Service
public class LogDao
{
	@Autowired
	private EntityManagerService ems;

	public List<LogContactItem> getLogContacts(String start, String stop) {
		String sql = "{call dbo.check_ncontacts_change_log ?, ?}";
		return ems.getSelectList(LogContactItem.class, sql, start, stop);
	}

	public List<LogMessagesItem> getLogMessages(String type, String start, String stop) {
		String sql = "{call dbo.check_sms_and_email_reciver ?, ?, ?}";
		return ems.getSelectList(LogMessagesItem.class, sql, type, start, stop);
	}

	public List<LogSubscriptionItem> getLogSubscription() {
		String sql = "{call dbo.subscription_data_v_proc}";
		return ems.getSelectList(LogSubscriptionItem.class, sql);
	}

}
