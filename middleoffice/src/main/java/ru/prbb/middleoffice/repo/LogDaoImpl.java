/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Журнал отправки сообщений<br>
 * Журнал изменений справочника контактов<br>
 * Журнал подписки
 * 
 * @author RBr
 * 
 */
@Service
@Transactional
public class LogDaoImpl implements LogDao
{

	@Override
	public List<Map<String, Object>> getLogContacts(String start, String stop) {
		final ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			list.add(map);
			map.put("security_type", "SECURITY_TYPE_" + (i + 1));
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getLogMessages(String type, String start, String stop) {
		final ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			list.add(map);
			map.put("security_type", "SECURITY_TYPE_" + (i + 1));
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getLogSubscription() {
		final ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 10; i++) {
			final HashMap<String, Object> map = new HashMap<String, Object>();
			list.add(map);
			map.put("security_type", "SECURITY_TYPE_" + (i + 1));
		}
		return list;
	}

}
