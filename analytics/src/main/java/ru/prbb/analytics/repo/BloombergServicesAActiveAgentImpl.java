/**
 * 
 */
package ru.prbb.analytics.repo;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.analytics.domain.AgentTask;
import ru.prbb.analytics.services.AgentTaskService;

/**
 * @author RBr
 */
@Service
public final class BloombergServicesAActiveAgentImpl implements BloombergServicesA {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AgentTaskService tasks;
	private Random rnd = new Random(System.currentTimeMillis());

	private String executeRequest(String name, Map<String, Object> data) throws InterruptedException {
		long idTask = rnd.nextLong();
		data.put("idTask", idTask);
		data.put("name", name);
		String json = serialize(data);
		AgentTask task = new AgentTask(idTask, json);
		tasks.add(task);
		synchronized (task) {
			for (int iter = 120, c = 0; c < iter; ++c) {
				try {
					task.wait(1000);
				} catch (InterruptedException ignore) {
				}

				String result = task.getResult();

				if (result == null) {
					continue;
				}

				if ("WAIT".equals(result)) {
					iter = 1200;
					continue;
				}

				tasks.remove(task);
				return result;
			}
		}
		throw new InterruptedException("Agent task execute timeout");
	}

	private final ObjectMapper m = new ObjectMapper();

	private String serialize(Object object) {
		try {
			StringWriter w = new StringWriter();
			m.writeValue(w, object);
			return w.toString();
		} catch (Exception e) {
			log.error("serialize", e);
		}
		throw new RuntimeException("serialize");
	}

	private Object deserialize(String content) {
		try {
			log.info(content);
			return m.readValue(content, Object.class);
		} catch (Exception e) {
			log.error("deserialize", e);
			throw new RuntimeException(e.getMessage());
		}
	}

	
	
	
	@Override
	public Map<String, Map<String, String>> executeReferenceDataRequest(String name,
			String[] securities, String[] fields) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeReferenceDataRequest");
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> result =
					(Map<String, Map<String, String>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("ReferenceData", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, String>> executeBdpRequest(String name,
			String[] securities, String[] fields) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdpRequest");
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> result =
					(Map<String, Map<String, String>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("BdpRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeBdpRequestOverride(String name,
			String[] securities, String[] fields, String period, String over) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdpRequestOverride");
		m.put("period", period);
		m.put("over", over);
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, String>>> result =
					(Map<String, Map<String, Map<String, String>>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("BdpRequestOverride", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeBdpRequestOverrideQuarter(String name,
			String[] securities, String[] fields, String[] currencies, String over) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdpRequestOverrideQuarter");
		m.put("over", over);
		m.put("securities", securities);
		m.put("fields", fields);
		m.put("currencies", currencies);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, String>>> result =
					(Map<String, Map<String, Map<String, String>>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("BdpRequestOverrideQuarter", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdsRequest");
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Object> result =
					(Map<String, Object>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("BdsRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeBdhEpsRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdhEpsRequest");
		m.put("dateStart", dateStart);
		m.put("dateEnd", dateEnd);
		m.put("period", period);
		m.put("calendar", calendar);
		m.put("currencies", currencies);
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, String>>> result =
					(Map<String, Map<String, Map<String, String>>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("BdhEpsRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeBdhRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdhRequest");
		m.put("dateStart", dateStart);
		m.put("dateEnd", dateEnd);
		m.put("period", period);
		m.put("calendar", calendar);
		m.put("currencies", currencies);
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, String>>> result =
					(Map<String, Map<String, Map<String, String>>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("BdhRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Object> executeFieldInfoRequest(String name, String code) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeFieldInfoRequest");
		m.put("code", code);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("FieldInfoRequest", e);
			throw new RuntimeException(e);
		}
	}
}
