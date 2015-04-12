package ru.prbb.middleoffice.domain.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

public class TaskRateCouponLoad extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] ids;

	private transient final List<Map<String, Object>> result = new ArrayList<>();

	public TaskRateCouponLoad(String name) {
		super(name);
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public List<Map<String, Object>> getResult() {
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		List<Map<String, Object>> answer = mapper.readValue(data,
				new TypeReference<ArrayList<Map<String, Object>>>() {
				});

		result.addAll(answer);
	}
}
