package ru.prbb.jobber.domain.tasks;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.jobber.domain.AtrLoadDataItem;

public class TaskAtrLoad extends TaskData {

	private static final long serialVersionUID = 1L;

	private String dateStart;
	private String dateEnd;
	private String[] securities;
	private String maType;
	private Integer taPeriod;
	private String period;
	private String calendar;

	private transient final List<AtrLoadDataItem> result = new ArrayList<>();

	public TaskAtrLoad(String name) {
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

	public String getMaType() {
		return maType;
	}

	public void setMaType(String maType) {
		this.maType = maType;
	}

	public Integer getTaPeriod() {
		return taPeriod;
	}

	public void setTaPeriod(Integer taPeriod) {
		this.taPeriod = taPeriod;
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

	/**
	 * [ { security, date, value } ]
	 */
	public List<AtrLoadDataItem> getResult() {
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		List<String> answer = mapper.readValue(data,
				new TypeReference<ArrayList<String>>() {
				});

		if (answer.size() < 2) {
			throw new Exception(data);
		}

		String security = answer.get(0);

		for (int i = 1; i < answer.size(); i++) {
			String line = answer.get(i);

			int p = line.indexOf(';');
			String date = line.substring(0, p);
			String value = line.substring(p + 1);

			AtrLoadDataItem item = new AtrLoadDataItem();
			item.setSecurity(security);
			item.setDate(date);
			item.setValue(value);

			result.add(item);
		}
	}
}
