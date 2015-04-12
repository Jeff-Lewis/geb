package ru.prbb.analytics.domain.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.type.TypeReference;

public class TaskBdpRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] securities;
	private String[] fields;

	private transient final Map<String, Map<String, String>> result = new HashMap<>();

	public TaskBdpRequest(String name) {
		super(name);
	}

	public String[] getSecurities() {
		return securities;
	}

	public void setSecurities(String[] securities) {
		this.securities = securities;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public Map<String, Map<String, String>> getResult() {
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		Map<String, Map<String, String>> answer = mapper.readValue(data,
				new TypeReference<HashMap<String, HashMap<String, String>>>() {
				});

		for (Entry<String, Map<String, String>> entry : answer.entrySet()) {
			String security = entry.getKey();
			Map<String, String> values = entry.getValue();

			Map<String, String> valuesRes = result.get(security);

			if (valuesRes != null) {
				valuesRes.putAll(values);
			} else {
				result.put(security, values);
			}
		}
	}
}
