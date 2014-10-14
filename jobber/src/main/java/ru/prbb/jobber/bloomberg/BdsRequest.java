/**
 * 
 */
package ru.prbb.jobber.bloomberg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.prbb.jobber.domain.SecForJobRequest;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * @author RBr
 * 
 */
public class BdsRequest implements BloombergRequest, MessageHandler {
	private static final Log log = LogFactory.getLog(BdsRequest.class);

	private final List<SecForJobRequest> securities;
	private final String[] fields;

	/**
	 * @param info
	 */
	public BdsRequest(List<SecForJobRequest> securities, String[] fields) {
		this.securities = securities;
		this.fields = fields;
	}

	private final Set<String> peers = new HashSet<String>();

	public String[] getPeers() {
		return peers.toArray(new String[peers.size()]);
	}

	@Override
	public void execute(String name) {
		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			final Request request = bs.createRequest("//blp/refdata", "ReferenceDataRequest");

			Element _securities = request.getElement("securities");
			for (SecForJobRequest security : securities) {
				_securities.appendValue(security.code);
			}

			Element _fields = request.getElement("fields");
			for (String field : fields) {
				_fields.appendValue(field);
			}

			bs.sendRequest(request, this);

			if (!peers.isEmpty()) {
				final Request requestP = bs.createRequest("//blp/refdata", "ReferenceDataRequest");

				final Element securitiesP = requestP.getElement("securities");
				for (String peer : peers) {
					securitiesP.appendValue(peer + " Equity");
				}

				final Element fieldsP = requestP.getElement("fields");
				fieldsP.appendValue("CUR_MKT_CAP");
				fieldsP.appendValue("OPER_ROE");
				fieldsP.appendValue("BS_TOT_LIAB2");
				fieldsP.appendValue("PE_RATIO");
				fieldsP.appendValue("INDUSTRY_subGROUP");
				fieldsP.appendValue("INDUSTRY_GROUP");
				fieldsP.appendValue("EBITDA");

				bs.sendRequest(requestP, new MessageHandler() {

					@Override
					public void processMessage(Message message) {
						Element ReferenceDataResponse = message.asElement();
						if (ReferenceDataResponse.hasElement("responseError")) {
							log.error("*****ResponceError*****");
						}
						Element securityDataArray = ReferenceDataResponse.getElement("securityData");
						int numItems = securityDataArray.numValues();
						for (int i = 0; i < numItems; ++i) {
							Element securityData = securityDataArray.getValueAsElement(i);
							String security = securityData.getElementAsString("security");
							if (securityData.hasElement("securityError")) {
								log.error("SecurityError:" + securityData.getElement("securityError"));
								return;
							} else {
								Element fieldData = securityData.getElement("fieldData");

								final PeerData item = new PeerData(security, fieldData);
								peersData.add(item);
							}
						}
					}
				}, new CorrelationID(1));
			}
		} finally {
			bs.stop();
		}
	}

	public static class PeerData {
		public final String sec;
		public final Double cur_mkt_cap;
		public final Double oper_roe;
		public final Double bs_tot_liab2;
		public final Double pe_ration;
		public final Double ebitda;
		public final String group;
		public final String sub;

		public PeerData(String sec, Element fieldData) {
			this.sec = sec;
			this.cur_mkt_cap = BloombergSession.getElementAsFloat64(fieldData, "CUR_MKT_CAP");
			this.oper_roe = BloombergSession.getElementAsFloat64(fieldData, "OPER_ROE");
			this.bs_tot_liab2 = BloombergSession.getElementAsFloat64(fieldData, "BS_TOT_LIAB2");
			this.pe_ration = BloombergSession.getElementAsFloat64(fieldData, "PE_RATIO");
			this.ebitda = BloombergSession.getElementAsFloat64(fieldData, "EBITDA");
			this.group = BloombergSession.getElementAsString(fieldData, "INDUSTRY_GROUP");
			this.sub = BloombergSession.getElementAsString(fieldData, "INDUSTRY_subGROUP");
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PeerData [sec=");
			builder.append(sec);
			builder.append(", cur_mkt_cap=");
			builder.append(cur_mkt_cap);
			builder.append(", oper_roe=");
			builder.append(oper_roe);
			builder.append(", bs_tot_liab2=");
			builder.append(bs_tot_liab2);
			builder.append(", pe_ration=");
			builder.append(pe_ration);
			builder.append(", ebitda=");
			builder.append(ebitda);
			builder.append(", group=");
			builder.append(group);
			builder.append(", sub=");
			builder.append(sub);
			builder.append("]");
			return builder.toString();
		}
	}

	private final List<PeerData> peersData = new ArrayList<>();

	/**
	 * @return the peersData
	 */
	public List<PeerData> getPeersData() {
		return peersData;
	}

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

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("BEST_ANALYST_RECS_BULK [firm=");
			builder.append(firm);
			builder.append(", analyst=");
			builder.append(analyst);
			builder.append(", recom=");
			builder.append(recom);
			builder.append(", rating=");
			builder.append(rating);
			builder.append(", action_code=");
			builder.append(action_code);
			builder.append(", target_price=");
			builder.append(target_price);
			builder.append(", period=");
			builder.append(period);
			builder.append(", date=");
			builder.append(date);
			builder.append(", barr=");
			builder.append(barr);
			builder.append(", year_return=");
			builder.append(year_return);
			builder.append("]");
			return builder.toString();
		}
	}

	/**
	 * security -> [ BEST_ANALYST_RECS_BULK ]
	 */
	private final Map<String, List<BEST_ANALYST_RECS_BULK>> bestAnalyst = new HashMap<>();

	/**
	 * security -> [ BEST_ANALYST_RECS_BULK ]
	 */
	public Map<String, List<BEST_ANALYST_RECS_BULK>> getBestAnalyst() {
		return bestAnalyst;
	}

	/**
	 * security -> [ peer ]
	 */
	private final Map<String, List<String>> peerTicker = new HashMap<>();

	/**
	 * security -> [ peer ]
	 */
	public Map<String, List<String>> getPeerTicker() {
		return peerTicker;
	}

	@Override
	public void processMessage(Message message) {
		Element ReferenceDataResponse = message.asElement();
		Element securityDataArray = ReferenceDataResponse.getElement("securityData");
		for (int i = 0; i < securityDataArray.numValues(); ++i) {
			final Element securityData = securityDataArray.getValueAsElement(i);

			final String security = securityData.getElementAsString("security");

			if (securityData.hasElement("securityError")) {
				log.error("SecurityError:" + securityData.getElement("securityError"));
				continue;
			}

			final Element fieldData = securityData.getElement("fieldData");

			for (String p : fields) {
				if (p.equals("BEST_ANALYST_RECS_BULK")) {
					final List<BEST_ANALYST_RECS_BULK> values = new ArrayList<BEST_ANALYST_RECS_BULK>();
					bestAnalyst.put(security, values);

					final Element best_anal_recs_bulk = fieldData.getElement("BEST_ANALYST_RECS_BULK");
					for (int m = 0; m < best_anal_recs_bulk.numValues(); m++) {
						final Element e = best_anal_recs_bulk.getValueAsElement(m);
						values.add(new BEST_ANALYST_RECS_BULK(e));
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
}
