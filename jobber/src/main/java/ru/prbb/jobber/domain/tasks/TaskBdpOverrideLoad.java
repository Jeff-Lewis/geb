package ru.prbb.jobber.domain.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.type.TypeReference;

public class TaskBdpOverrideLoad extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] securities;
	private String[] currencies;

	/**
	 * security -> { period, value }
	 */
	private transient final Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();

	public TaskBdpOverrideLoad(String name) {
		super(name);
	}

	public String[] getSecurities() {
		return securities;
	}

	public void setSecurities(String[] securities) {
		this.securities = securities;
	}

	public String[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(String[] currencies) {
		this.currencies = currencies;
	}

	/**
	 * @return security -> { period, value }
	 */
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
