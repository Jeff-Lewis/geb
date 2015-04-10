package ru.prbb.activeagent.executors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;

import ru.prbb.activeagent.data.TaskItem;
import ru.prbb.activeagent.tasks.TaskBdsRequest;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

public class TaskBdsExecutor extends TaskExecutor {

	public TaskBdsExecutor() {
		super(TaskBdsRequest.class.getSimpleName());
	}

	@Override
	public void execute(TaskItem task, String data) throws Exception {
		taskData = mapper.readValue(data,
				new TypeReference<TaskBdsRequest>() {
				});

		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost("localhost");
		sesOpt.setServerPort(8194);

		Session session = new Session(sesOpt);
		session.start();
		// TODO Auto-generated method stub
		try {
			if (session.openService("//blp/refdata")) {
				Service service = session.getService("//blp/refdata");

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

					sendRequest(session, request);
				}
			}
		} finally {
			session.stop();
		}

	}

	private final Set<String> peers = new HashSet<String>();

	@Override
	protected void processMessage(Message message) {
		if (message.correlationID().isObject()) {
			Element ReferenceDataResponse = message.asElement();
			if (ReferenceDataResponse.hasElement("responseError")) {
				logger.severe("*****ResponceError*****");
			}
			Element securityDataArray = ReferenceDataResponse.getElement("securityData");
			int numItems = securityDataArray.numValues();
			for (int i = 0; i < numItems; ++i) {
				Element securityData = securityDataArray.getValueAsElement(i);
				String security = securityData.getElementAsString("security");
				if (securityData.hasElement("securityError")) {
					logger.severe("SecurityError:" + securityData.getElement("securityError"));
					return;
				} else {
					Element fieldData = securityData.getElement("fieldData");

					final PeerData item = new PeerData(security, fieldData);
					peersData.add(item);
				}
			}
			return;
		}

		Element ReferenceDataResponse = message.asElement();
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
					final List<BEST_ANALYST_RECS_BULK> values = new ArrayList<>();
					bestAnalyst.put(security, values);

					final Element best_anal_recs_bulk = fieldData.getElement("BEST_ANALYST_RECS_BULK");
					for (int m = 0; m < best_anal_recs_bulk.numValues(); m++) {
						final Element e = best_anal_recs_bulk.getValueAsElement(m);
						values.add(new BEST_ANALYST_RECS_BULK(e));
					}
				}

				if (p.equals("EARN_ANN_DT_TIME_HIST_WITH_EPS")) {
					final List<EARN_ANN_DT_TIME_HIST_WITH_EPS> values = new ArrayList<>();
					earnHistWithEps.put(security, values);

					final Element element = fieldData.getElement("EARN_ANN_DT_TIME_HIST_WITH_EPS");
					for (int t = 0; t < element.numValues(); t++) {
						final Element e = element.getValueAsElement(t);
						values.add(new EARN_ANN_DT_TIME_HIST_WITH_EPS(e));
					}
				}

				if (p.equals("ERN_ANN_DT_AND_PER")) {
					final List<ERN_ANN_DT_AND_PER> values = new ArrayList<>();
					ernAnnDTandPer.put(security, values);

					final Element element = fieldData.getElement("ERN_ANN_DT_AND_PER");
					final int ernItems = element.numValues();
					for (int j = 0; j < ernItems; ++j) {
						final Element e = element.getValueAsElement(j);
						values.add(new ERN_ANN_DT_AND_PER(e));
					}
				}

				if (p.equals("BLOOMBERG_PEERS")) {
					final List<String> values = new ArrayList<String>();
					peerTicker.put(security, values);

					final Element blm_peers = fieldData.getElement("BLOOMBERG_PEERS");
					for (int j = 0; j < blm_peers.numValues(); ++j) {
						final Element e = blm_peers.getValueAsElement(j);
						final String peer = e.getElementAsString("Peer Ticker");
						values.add(peer);

						peers.add(peer);
					}
				}
			}
		}
	}

	public static class PeerData {

		public final String sec;
		public final String cur_mkt_cap;
		public final String oper_roe;
		public final String bs_tot_liab2;
		public final String pe_ration;
		public final String ebitda;
		public final String group;
		public final String sub;

		public PeerData(String sec, Element fieldData) {
			this.sec = sec;
			this.cur_mkt_cap = fieldData.getElementAsString("CUR_MKT_CAP");
			this.oper_roe = fieldData.getElementAsString("OPER_ROE");
			this.bs_tot_liab2 = fieldData.getElementAsString("BS_TOT_LIAB2");
			this.pe_ration = fieldData.getElementAsString("PE_RATIO");
			this.ebitda = fieldData.getElementAsString("EBITDA");
			this.group = fieldData.getElementAsString("INDUSTRY_GROUP");
			this.sub = fieldData.getElementAsString("INDUSTRY_subGROUP");
		}
	}

	private final List<PeerData> peersData = new ArrayList<>();

	public static class BEST_ANALYST_RECS_BULK {

		public final String firm;
		public final String analyst;
		public final String recom;
		public final String rating;
		public final String action_code;
		public final String target_price;
		public final String period;
		public final String date;
		public final String barr;
		public final String year_return;

		public BEST_ANALYST_RECS_BULK(Element element) {
			this.firm = element.getElementAsString("Firm Name");
			this.analyst = element.getElementAsString("Analyst");
			this.recom = element.getElementAsString("Recommendation");
			this.rating = element.getElementAsString("Rating");
			this.action_code = element.getElementAsString("Action Code");
			this.target_price = element.getElementAsString("Target Price");
			this.period = element.getElementAsString("Period");
			this.date = element.getElementAsString("Date");
			this.barr = element.getElementAsString("BARR");
			this.year_return = element.getElementAsString("1 Year Return");
		}
	}

	/**
	 * security -> [ BEST_ANALYST_RECS_BULK ]
	 */
	private final Map<String, List<BEST_ANALYST_RECS_BULK>> bestAnalyst = new HashMap<>();

	public static class EARN_ANN_DT_TIME_HIST_WITH_EPS {

		public final String year_period;
		public final String announsment_date;
		public final String announsment_time;
		public final String earnings_EPS;
		public final String comparable_EPS;
		public final String estimate_EPS;

		public EARN_ANN_DT_TIME_HIST_WITH_EPS(Element e) {
			this.year_period = e.getElementAsString("Year/Period");
			this.announsment_date = e.getElementAsString("Announcement Date");
			this.announsment_time = e.getElementAsString("Announcement Time");
			this.earnings_EPS = e.getElementAsString("Earnings EPS");
			this.comparable_EPS = e.getElementAsString("Comparable EPS");
			this.estimate_EPS = e.getElementAsString("Estimate EPS");
		}

	}

	private final Map<String, List<EARN_ANN_DT_TIME_HIST_WITH_EPS>> earnHistWithEps = new HashMap<>();

	public static class ERN_ANN_DT_AND_PER {

		public final String ead;
		public final String eyap;

		/**
		 * @param e
		 */
		public ERN_ANN_DT_AND_PER(Element e) {
			this.ead = e.getElementAsString("Earnings Announcement Date");
			this.eyap = e.getElementAsString("Earnings Year and Period");
		}

	}

	private final Map<String, List<ERN_ANN_DT_AND_PER>> ernAnnDTandPer = new HashMap<>();

	/**
	 * security -> [ peer ]
	 */
	private final Map<String, List<String>> peerTicker = new HashMap<>();

	private TaskBdsRequest taskData;

}
