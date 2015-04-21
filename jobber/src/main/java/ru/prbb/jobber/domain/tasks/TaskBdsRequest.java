package ru.prbb.jobber.domain.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.type.TypeReference;

public class TaskBdsRequest extends TaskData {

	private static final long serialVersionUID = 1L;

	private static final String BEST_ANALYST_RECS_BULK = "BEST_ANALYST_RECS_BULK";
	private static final String EARN_ANN_DT_TIME_HIST_WITH_EPS = "EARN_ANN_DT_TIME_HIST_WITH_EPS";
	private static final String ERN_ANN_DT_AND_PER = "ERN_ANN_DT_AND_PER";
	private static final String BLOOMBERG_PEERS = "BLOOMBERG_PEERS";
	private static final String PEERS = "PEERS";

	private String[] securities;
	private String[] fields;

	private transient final Map<String, Object> result = new HashMap<>();

	public TaskBdsRequest(String name) {
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

	public Map<String, Object> getResult() {
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void handleData(String data) throws Exception {
		Map<String, Object> answer = mapper.readValue(data,
				new TypeReference<HashMap<String, Object>>() {
				});

		handleBestAnalyst((Map<String, List<Map<String, String>>>) answer.get(BEST_ANALYST_RECS_BULK));

		handleEarnEPS((Map<String, List<Map<String, String>>>) answer.get(EARN_ANN_DT_TIME_HIST_WITH_EPS));

		handleErn((Map<String, List<Map<String, String>>>) answer.get(ERN_ANN_DT_AND_PER));

		handleBloombergPeers((Map<String, List<String>>) answer.get(BLOOMBERG_PEERS));

		handlePeers((List<Map<String, Object>>) answer.get(PEERS));
	}

	private void handleBestAnalyst(Map<String, List<Map<String, String>>> answer) {
		Object a = result.get(BEST_ANALYST_RECS_BULK);
		if (a != null) {
			Map<String, List<Map<String, String>>> bestAnalyst = (Map<String, List<Map<String, String>>>) a;
			for (Entry<String, List<Map<String, String>>> entry : answer.entrySet()) {
				String security = entry.getKey();
				List<Map<String,String>> values = entry.getValue();
				bestAnalyst.put(security, values);
			}
		} else {
			result.put(BEST_ANALYST_RECS_BULK, answer);
		}
	}

	private void handleEarnEPS(Map<String, List<Map<String, String>>> answer) {
		Object a = result.get(EARN_ANN_DT_TIME_HIST_WITH_EPS);
		if (a != null) {
			Map<String, List<Map<String, String>>> earnHistWithEps = (Map<String, List<Map<String, String>>>) a;
			for (Entry<String, List<Map<String, String>>> entry : answer.entrySet()) {
				String security = entry.getKey();
				List<Map<String,String>> values = entry.getValue();
				earnHistWithEps.put(security, values);
			}
		} else {
			result.put(EARN_ANN_DT_TIME_HIST_WITH_EPS, answer);
		}
	}

	private void handleErn(Map<String, List<Map<String, String>>> answer) {
		Object a = result.get(ERN_ANN_DT_AND_PER);
		if (a != null) {
			Map<String, List<Map<String, String>>> ernAnnDTandPer = (Map<String, List<Map<String, String>>>) a;
			for (Entry<String, List<Map<String, String>>> entry : answer.entrySet()) {
				String security = entry.getKey();
				List<Map<String,String>> values = entry.getValue();
				ernAnnDTandPer.put(security, values);
			}
		} else {
			result.put(ERN_ANN_DT_AND_PER, answer);
		}
	}

	private void handleBloombergPeers(Map<String, List<String>> answer) {
		Object a = result.get(BLOOMBERG_PEERS);
		if (a != null) {
			Map<String, List<String>> peerTicker = (Map<String, List<String>>) a;
			for (Entry<String, List<String>> entry : answer.entrySet()) {
				String security = entry.getKey();
				List<String> values = entry.getValue();
				peerTicker.put(security, values);
			}
		} else {
			result.put(BLOOMBERG_PEERS, answer);
		}
	}

	private void handlePeers(List<Map<String, Object>> answer) {
		Object a = result.get(PEERS);
		if (a != null) {
			List<Map<String, Object>> peersData = (List<Map<String, Object>>) a;
			peersData.addAll(answer);
		} else {
			result.put(PEERS, answer);
		}
	}

}
