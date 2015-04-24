package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskBdsRequest;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;

public class TaskBdsExecutor extends TaskExecutor {

	public TaskBdsExecutor() {
		super(TaskBdsRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		taskData = mapper.readValue(data,
				new TypeReference<TaskBdsRequest>() {
				});

		Session session = startSession();
		try {
			String serviceUri = "//blp/refdata";
			if (session.openService(serviceUri)) {
				Service service = session.getService(serviceUri);

				Request request = service.createRequest("ReferenceDataRequest");

				Element _securities = request.getElement("securities");
				for (String security : taskData.getSecurities()) {
					_securities.appendValue(security);
				}

				Element _fields = request.getElement("fields");
				for (String field : taskData.getFields()) {
					_fields.appendValue(field);
				}

				sendRequest(session, request);

				if (!peers.isEmpty()) {
					request = service.createRequest("ReferenceDataRequest");

					final Element securitiesP = request.getElement("securities");
					for (String peer : peers) {
						securitiesP.appendValue(peer + " Equity");
					}

					final Element fieldsP = request.getElement("fields");
					fieldsP.appendValue("CUR_MKT_CAP");
					fieldsP.appendValue("OPER_ROE");
					fieldsP.appendValue("BS_TOT_LIAB2");
					fieldsP.appendValue("PE_RATIO");
					fieldsP.appendValue("INDUSTRY_subGROUP");
					fieldsP.appendValue("INDUSTRY_GROUP");
					fieldsP.appendValue("EBITDA");

					sendRequest(session, request, new CorrelationID(1));
				}
			} else {
				throw new IOException("Unable to open service " + serviceUri);
			}
		} finally {
			session.stop();
		}

	}

	private final Set<String> peers = new HashSet<String>();

	@Override
	protected void processMessage(Message message) {
		Map<String, Object> result = new HashMap<>();

		Element ReferenceDataResponse = message.asElement();

		if (message.correlationID().isValue()) {
			if (ReferenceDataResponse.hasElement("responseError")) {
				logger.severe("*****ResponceError*****");
			}
			Element securityDataArray = ReferenceDataResponse.getElement("securityData");
			int numItems = securityDataArray.numValues();

			List<Map<String, String>> peersData = new ArrayList<>();

			for (int i = 0; i < numItems; ++i) {
				Element securityData = securityDataArray.getValueAsElement(i);
				String security = securityData.getElementAsString("security");
				if (securityData.hasElement("securityError")) {
					logger.severe("SecurityError:" + securityData.getElement("securityError"));
				} else {
					Element fieldData = securityData.getElement("fieldData");

					Map<String, String> data = new HashMap<>(8, 1);
					data.put("sec", security);
					data.put("cur_mkt_cap", getElementAsString(fieldData, "CUR_MKT_CAP"));
					data.put("oper_roe", getElementAsString(fieldData, "OPER_ROE"));
					data.put("bs_tot_liab2", getElementAsString(fieldData, "BS_TOT_LIAB2"));
					data.put("pe_ration", getElementAsString(fieldData, "PE_RATIO"));
					data.put("ebitda", getElementAsString(fieldData, "EBITDA"));
					data.put("group", getElementAsString(fieldData, "INDUSTRY_GROUP"));
					data.put("sub", getElementAsString(fieldData, "INDUSTRY_subGROUP"));
					peersData.add(data);
				}
			}
			result.put("PEERS", peersData);
		} else {
			Element securityDataArray = ReferenceDataResponse.getElement("securityData");
			for (int i = 0; i < securityDataArray.numValues(); ++i) {
				final Element securityData = securityDataArray.getValueAsElement(i);

				final String security = securityData.getElementAsString("security");

				if (securityData.hasElement("securityError")) {
					logger.severe("SecurityError:" + securityData.getElement("securityError"));
					continue;
				}

				final Element fieldData = securityData.getElement("fieldData");

				for (String p : taskData.getFields()) {
					if (p.equals("BEST_ANALYST_RECS_BULK")) {
						List<Map<String, String>> values = new ArrayList<>();

						final Element best_anal_recs_bulk = fieldData.getElement("BEST_ANALYST_RECS_BULK");
						for (int m = 0; m < best_anal_recs_bulk.numValues(); m++) {
							final Element e = best_anal_recs_bulk.getValueAsElement(m);
							Map<String, String> data = new HashMap<>(12, 1);
							data.put("firm", e.getElementAsString("Firm Name"));
							data.put("analyst", e.getElementAsString("Analyst"));
							data.put("recom", e.getElementAsString("Recommendation"));
							data.put("rating", e.getElementAsString("Rating"));
							data.put("action_code", e.getElementAsString("Action Code"));
							data.put("target_price", e.getElementAsString("Target Price"));
							data.put("period", e.getElementAsString("Period"));
							data.put("date", e.getElementAsString("Date"));
							data.put("barr", e.getElementAsString("BARR"));
							data.put("year_return", e.getElementAsString("1 Year Return"));
							values.add(data);
						}

						Map<String, List<Map<String, String>>> bestAnalyst = new HashMap<>();
						bestAnalyst.put(security, values);
						result.put("BEST_ANALYST_RECS_BULK", bestAnalyst);
					}

					if (p.equals("EARN_ANN_DT_TIME_HIST_WITH_EPS")) {
						Element element = fieldData.getElement("EARN_ANN_DT_TIME_HIST_WITH_EPS");

						List<Map<String, String>> values = new ArrayList<>();

						for (int t = 0; t < element.numValues(); t++) {
							final Element e = element.getValueAsElement(t);
							Map<String, String> data = new HashMap<>(8, 1);
							data.put("year_period", e.getElementAsString("Year/Period"));
							data.put("announsment_date", e.getElementAsString("Announcement Date"));
							data.put("announsment_time", e.getElementAsString("Announcement Time"));
							data.put("earnings_EPS", e.getElementAsString("Earnings EPS"));
							data.put("comparable_EPS", e.getElementAsString("Comparable EPS"));
							data.put("estimate_EPS", e.getElementAsString("Estimate EPS"));
							values.add(data);
						}

						Map<String, List<Map<String, String>>> earnHistWithEps = new HashMap<>();
						earnHistWithEps.put(security, values);
						result.put("EARN_ANN_DT_TIME_HIST_WITH_EPS", earnHistWithEps);
					}

					if (p.equals("ERN_ANN_DT_AND_PER")) {
						final List<Map<String, String>> values = new ArrayList<>();

						final Element element = fieldData.getElement("ERN_ANN_DT_AND_PER");
						final int ernItems = element.numValues();
						for (int j = 0; j < ernItems; ++j) {
							final Element e = element.getValueAsElement(j);
							Map<String, String> data = new HashMap<>();
							data.put("ead", e.getElementAsString("Earnings Announcement Date"));
							data.put("eyap", e.getElementAsString("Earnings Year and Period"));
							values.add(data);
						}

						Map<String, List<Map<String, String>>> ernAnnDTandPer = new HashMap<>();
						ernAnnDTandPer.put(security, values);
						result.put("ERN_ANN_DT_AND_PER", ernAnnDTandPer);
					}

					if (p.equals("BLOOMBERG_PEERS")) {
						final List<String> values = new ArrayList<String>();

						final Element blm_peers = fieldData.getElement("BLOOMBERG_PEERS");
						for (int j = 0; j < blm_peers.numValues(); ++j) {
							final Element e = blm_peers.getValueAsElement(j);
							final String peer = e.getElementAsString("Peer Ticker");
							values.add(peer);

							peers.add(peer);
						}

						Map<String, List<String>> peerTicker = new HashMap<>();
						peerTicker.put(security, values);
						result.put("BLOOMBERG_PEERS", peerTicker);
					}
				}
			}
		}

		send(result);
	}

	private void send(Map<String, Object> answer) {
		try {
			send(mapper.writeValueAsString(answer));
		} catch (Exception e) {
			sendError(e.getMessage());
		}
	}

	private TaskBdsRequest taskData;

}
