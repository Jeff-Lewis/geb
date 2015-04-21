package ru.prbb.analytics.domain.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.type.TypeReference;

public class TaskBdpRequestOverrideQuarter extends TaskData {

	private static final long serialVersionUID = 1L;

	private String over;
	private String[] securities;
	private String[] fields;
	private String[] currencies;

	/**
	 * security -> [ period -> [ { field, value } ] ]
	 */
	private transient final Map<String, Map<String, Map<String, String>>> result = new HashMap<>();

	public TaskBdpRequestOverrideQuarter(String name) {
		super(name);
	}

	public String getOver() {
		return over;
	}

	public void setOver(String over) {
		this.over = over;
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

	public String[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(String[] currencies) {
		this.currencies = currencies;
	}

	/**
	 * @return security -> [ period -> [ { field, value } ] ]
	 */
	public Map<String, Map<String, Map<String, String>>> getResult() {
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		Map<String, Map<String, Map<String, String>>> answer = mapper.readValue(data,
				new TypeReference<HashMap<String, HashMap<String, HashMap<String, String>>>>() {
				});

		for (Entry<String, Map<String, Map<String, String>>> entry : answer.entrySet()) {
			String security = entry.getKey();
			Map<String, Map<String, String>> periodvalues = entry.getValue();

			Map<String, Map<String, String>> periodvaluesRes = result.get(security);

			if (periodvaluesRes != null) {
				for (Entry<String, Map<String, String>> entry2 : periodvalues.entrySet()) {
					String period = entry2.getKey();
					Map<String, String> values = entry2.getValue();
					Map<String, String> valuesRes = periodvaluesRes.get(period);
					if (valuesRes != null) {
						valuesRes.putAll(values);
					} else {
						periodvaluesRes.put(period, values);
					}
				}
			} else {
				result.put(security, periodvalues);
			}
		}
	}
}
