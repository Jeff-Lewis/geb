package ru.prbb.analytics.domain.tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.type.TypeReference;

public class TaskBdhEpsRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String dateStart;
	private String dateEnd;
	private String period;
	private String calendar;
	private String[] currencies;
	private String[] securities;
	private String[] fields;

	private transient final Map<String, Map<String, Map<String, String>>> result = new HashMap<>();

	public TaskBdhEpsRequest(String name) {
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCalendar() {
		return calendar;
	}

	public void setCalendar(String calendar) {
		this.calendar = calendar;
	}

	public String[] getCurrencies() {
		return currencies;
	}

	public void setCurrencies(String[] currencies) {
		this.currencies = currencies;
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

	public Map<String, Map<String, Map<String, String>>> getResult() {
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		int p = data.indexOf('\t');
		String security = data.substring(0, p);
		Map<String, Map<String, String>> datevalues = mapper.readValue(data.substring(p + 1),
				new TypeReference<HashMap<String, HashMap<String, String>>>() {
				});

		Map<String, Map<String, String>> datevaluesRes = result.get(security);
		if (datevaluesRes != null) {
			for (Entry<String, Map<String, String>> entry : datevalues.entrySet()) {
				String date = entry.getKey();
				Map<String, String> values = entry.getValue();
				
				Map<String, String> valuesRes = datevaluesRes.get(date);
				
				if (valuesRes != null) {
					valuesRes.putAll(values);
				} else {
					datevaluesRes.put(date, values);
				}
			}
		} else {
			result.put(security, datevalues);
		}
	}
}
