package ru.prbb.middleoffice.domain.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.codehaus.jackson.type.TypeReference;

public class TaskHistoricalDataRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String dateStart;
	private String dateEnd;
	private String[] securities;
	private String[] fields;
	private String[] currencies;

	private transient final Map<String, Map<String, Map<String, String>>> result = new HashMap<>();

	public TaskHistoricalDataRequest(String name) {
		super(name);
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
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
	 * @return security -> {date -> { field, value }
	 */
	public Map<String, Map<String, Map<String, String>>> getResult() {
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		Map<String, Map<String, Map<String, String>>> answer = mapper.readValue(data,
				new TypeReference<HashMap<String, HashMap<String, HashMap<String, String>>>>() {
				});

		Set<Entry<String, Map<String, Map<String, String>>>> entrysA = answer.entrySet();
		for (Entry<String, Map<String, Map<String, String>>> entry : entrysA.toArray(new Entry[entrysA.size()])) {
			String security = entry.getKey();
			Map<String, Map<String, String>> datevalues = entry.getValue();

			Map<String, Map<String, String>> datevaluesRes = result.get(security);
			if (datevaluesRes != null) {
				for (Entry<String, Map<String, String>> entryDate : datevalues.entrySet()) {
					String date = entryDate.getKey();
					Map<String, String> values = entryDate.getValue();

					Map<String, String> valuesRes = datevaluesRes.get(date);
					if (valuesRes != null) {
						valuesRes.putAll(values);
					} else {
						datevaluesRes.put(date, valuesRes);
					}
				}
			} else {
				result.put(security, datevalues);
			}
		}
	}
}
