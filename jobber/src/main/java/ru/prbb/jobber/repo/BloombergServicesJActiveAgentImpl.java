/**
 * 
 */
package ru.prbb.jobber.repo;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.jobber.domain.AgentTask;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.services.AgentTaskService;

/**
 * @author RBr
 */
@Service
public class BloombergServicesJActiveAgentImpl implements BloombergServicesJ {

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
	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields) {
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdsRequest");
		m.put("securities", securities);
		m.put("fields", fields);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("BdsRequest", e);
			throw new RuntimeException(e);
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
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeHistoricalDataRequest");
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("fields", fields);
		m.put("currencies", null);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, Map<String, String>>> result =
					(Map<String, Map<String, Map<String, String>>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("HistoricalDataRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields, String[] currencies) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeHistoricalDataRequest");
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
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
			log.error("HistoricalDataRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeAtrLoad(String name, Date startDate, Date endDate, String[] securities,
			String maType, Integer taPeriod, String period, String calendar) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeAtrLoad");
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("maType", maType);
		m.put("taPeriod", taPeriod);
		m.put("period", period);
		m.put("calendar", calendar);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result =
					(List<Map<String, Object>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("LoadAtrRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, String>> executeBdpOverrideLoad(String name, List<SecForJobRequest> securities) {
		Set<String> currencies = new HashSet<>();
		List<String> cursecs = new ArrayList<>(securities.size());
		for (SecForJobRequest security : securities) {
			currencies.add(security.iso);
			String cursec = security.iso + security.code;
			cursecs.add(cursec);
		}

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeBdpOverrideLoad");
		m.put("cursecs", cursecs);
		m.put("currencies", currencies);

		try {
			String response = executeRequest(name, m);
			@SuppressWarnings("unchecked")
			Map<String, Map<String, String>> result =
					(Map<String, Map<String, String>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("LoadBdpOverrideRequest", e);
			throw new RuntimeException(e);
		}
	}
}
