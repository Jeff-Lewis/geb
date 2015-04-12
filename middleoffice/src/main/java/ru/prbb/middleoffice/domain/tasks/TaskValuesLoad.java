package ru.prbb.middleoffice.domain.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

public class TaskValuesLoad extends TaskData {

	private static final long serialVersionUID = 1L;

	private String[] ids;

	private transient final List<Map<String, String>> result = new ArrayList<>();

	public TaskValuesLoad(String name) {
		super(name);
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public List<Map<String, String>> getResult() {
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		List<Map<String, String>> answer = mapper.readValue(data,
				new TypeReference<ArrayList<HashMap<String, String>>>() {
				});

		result.addAll(answer);
	}
}
