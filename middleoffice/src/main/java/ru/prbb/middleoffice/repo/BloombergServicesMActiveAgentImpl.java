/**
 * 
 */
package ru.prbb.middleoffice.repo;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.prbb.middleoffice.domain.AgentTask;
import ru.prbb.middleoffice.services.AgentTaskService;

/**
 * @author RBr
 */
@Service
public class BloombergServicesMActiveAgentImpl implements BloombergServicesM {

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
			log.error("ReferenceDataRequest", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeHistoricalDataRequest");
		m.put("dateStart", sdf.format(startDate));
		m.put("dateEnd", sdf.format(endDate));
		m.put("securities", securities);
		m.put("fields", fields);

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
	public List<Map<String, Object>> executeCashFlowLoad(Map<String, Long> _ids, Map<String, String> _dates) {
		List<String> ids = new ArrayList<>(_ids.size());
		for (Entry<String, Long> entry : _ids.entrySet()) {
			Long id = entry.getValue();
			String security = entry.getKey();
			ids.add(id + ";" + security);
		}

		List<String> dates = new ArrayList<>(_dates.size());
		for (Entry<String, String> entry : _dates.entrySet()) {
			String date = entry.getValue();
			String security = entry.getKey();
			dates.add(date + ";" + security);
		}

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeCashFlowLoad");
		m.put("ids", ids);
		m.put("dates", dates);

		try {
			String response = executeRequest("CashFlowLoad", m);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result =
					(List<Map<String, Object>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("CashFlowLoad", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeCashFlowLoadNew(Map<String, Long> _ids, Map<String, String> _dates) {
		List<String> ids = new ArrayList<>(_ids.size());
		for (Entry<String, Long> entry : _ids.entrySet()) {
			Long id = entry.getValue();
			String security = entry.getKey();
			ids.add(id + ";" + security);
		}

		List<String> dates = new ArrayList<>(_dates.size());
		for (Entry<String, String> entry : _dates.entrySet()) {
			String date = entry.getValue();
			String security = entry.getKey();
			dates.add(date + ";" + security);
		}

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeCashFlowLoadNew");
		m.put("ids", ids);
		m.put("dates", dates);

		try {
			String response = executeRequest("CashFlowLoadNew", m);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result =
					(List<Map<String, Object>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("CashFlowLoadNew", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeValuesLoad(Map<String, Long> _ids) {
		List<String> ids = new ArrayList<>(_ids.size());
		for (Entry<String, Long> entry : _ids.entrySet()) {
			Long id = entry.getValue();
			String security = entry.getKey();
			ids.add(id + ";" + security);
		}

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeValuesLoad");
		m.put("ids", ids);

		try {
			String response = executeRequest("ValuesLoad", m);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result =
					(List<Map<String, Object>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("ValuesLoad", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeRateCouponLoad(Map<String, Long> _ids) {
		List<String> ids = new ArrayList<>(_ids.size());
		for (Entry<String, Long> entry : _ids.entrySet()) {
			Long id = entry.getValue();
			String security = entry.getKey();
			ids.add(id + ";" + security);
		}

		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeRateCouponLoad");
		m.put("ids", ids);

		try {
			String response = executeRequest("RateCouponLoad", m);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result =
					(List<Map<String, Object>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("RateCouponLoad", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Map<String, Object>> executeAtrLoad(Date startDate, Date endDate, String[] securities,
			String maType, Integer taPeriod, String period, String calendar) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Map<String, Object> m = new HashMap<>();
		m.put("type", "executeAtrLoad");
		m.put("startDate", sdf.format(startDate));
		m.put("endDate", sdf.format(endDate));
		m.put("maType", maType);
		m.put("taPeriod", taPeriod);
		m.put("period", period);
		m.put("calendar", calendar);
		m.put("securities", securities);

		try {
			String response = executeRequest("AtrLoad", m);
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> result =
					(List<Map<String, Object>>) deserialize(response);
			return result;
		} catch (Exception e) {
			log.error("AtrLoad", e);
			throw new RuntimeException(e);
		}
	}
}
