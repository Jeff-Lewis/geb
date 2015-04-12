package ru.prbb.analytics.domain.tasks;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

public class TaskFieldInfoRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private String code;

	private transient final Map<String, String> result = new HashMap<>();

	public TaskFieldInfoRequest(String name) {
		super(name);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<String, String> getResult() {
		return result;
	}

	@Override
	protected void handleData(String data) throws Exception {
		Map<String, String> answer = mapper.readValue(data,
				new TypeReference<HashMap<String, String>>() {
				});

		result.putAll(answer);
	}
}
