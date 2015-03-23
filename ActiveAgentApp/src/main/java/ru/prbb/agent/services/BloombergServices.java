/**
 * 
 */
package ru.prbb.agent.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ru.prbb.agent.model.SecForJobRequest;
import ru.prbb.agent.requests.AtrLoadRequest;
import ru.prbb.agent.requests.BdhEpsRequest;
import ru.prbb.agent.requests.BdhRequest;
import ru.prbb.agent.requests.BdpOverrideRequest;
import ru.prbb.agent.requests.BdpRequest;
import ru.prbb.agent.requests.BdpRequestOverride;
import ru.prbb.agent.requests.BdpRequestOverrideQuarter;
import ru.prbb.agent.requests.BdsRequest;
import ru.prbb.agent.requests.BdsRequest.BEST_ANALYST_RECS_BULK;
import ru.prbb.agent.requests.BdsRequest.EARN_ANN_DT_TIME_HIST_WITH_EPS;
import ru.prbb.agent.requests.BdsRequest.ERN_ANN_DT_AND_PER;
import ru.prbb.agent.requests.BdsRequest.PeerData;
import ru.prbb.agent.requests.CashFlowLoadRequest;
import ru.prbb.agent.requests.CashFlowLoadRequestNew;
import ru.prbb.agent.requests.FieldInfoRequest;
import ru.prbb.agent.requests.HistoricalDataRequest;
import ru.prbb.agent.requests.RateCouponLoadRequest;
import ru.prbb.agent.requests.ReferenceDataRequest;
import ru.prbb.agent.requests.ValuesLoadRequest;

/**
 * @author RBr
 */
@Component
public class BloombergServices {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public Map<String, Map<String, String>> executeBdpOverrideLoad(String name, String[] securities, String[] currencies) {
		log.info("BdpOverrideLoad:");

		List<SecForJobRequest> jobSecurities = new ArrayList<>();
		for (String currency : currencies) {
			for (String security : securities) {
				if (security.startsWith(currency)) {
					String security_code = security.substring(currency.length());
					String iso = currency;
					SecForJobRequest e = new SecForJobRequest(security_code, iso);
					jobSecurities.add(e);
				}
			}
		}

		final BdpOverrideRequest r = new BdpOverrideRequest(jobSecurities);

		r.execute(name);

		return r.getAnswer();
	}

	public Map<String, Map<String, String>> executeBdpRequest(String name,
			String[] securities, String[] fields) {
		log.info("BdpRequest:" + name);

		final BdpRequest r = new BdpRequest(securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, Map<String, String>> executeBdpRequestOverride(String name,
			String[] securities, String[] fields, String period, String override) {
		log.info("BdpRequestOverride:" + name);

		final BdpRequestOverride r = new BdpRequestOverride(securities, fields, period, override);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, Map<String, Map<String, String>>> executeBdpRequestOverrideQuarter(String name,
			String[] securities, String[] fields, String[] currencies, String over) {
		log.info("BdpRequestOverrideQuarter:" + name);

		final BdpRequestOverrideQuarter r = new BdpRequestOverrideQuarter(securities, fields, currencies, over);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields) {
		log.info("BdsRequest:");

		final BdsRequest r = new BdsRequest(securities, fields);

		r.execute(name);

		Map<String, Object> res = new HashMap<>();

		Map<String, List<BEST_ANALYST_RECS_BULK>> ba = r.getBestAnalyst();
		res.put("BEST_ANALYST_RECS_BULK", ba);

		Map<String, List<EARN_ANN_DT_TIME_HIST_WITH_EPS>> ehwe = r.getEarnHistWithEps();
		res.put("EARN_ANN_DT_TIME_HIST_WITH_EPS", ehwe);

		Map<String, List<ERN_ANN_DT_AND_PER>> eap = r.getErnAnnDTandPer();
		res.put("ERN_ANN_DT_AND_PER", eap);

		Map<String, List<String>> pt = r.getPeerTicker();
		res.put("PeerTicker", pt);

		List<PeerData> pd = r.getPeersData();
		res.put("Peers", pd);

		return res;
	}

	/**
	 * Выполнить запрос //blp/refdata, ReferenceDataRequest<br>
	 * Ввод новой акции<br>
	 * Ввод нового индекса<br>
	 * Ввод новой облигации<br>
	 * Ввод нового фьючерса
	 * 
	 * @param name
	 *            Название запроса
	 * @param securities
	 *            Коды
	 * @param fields
	 *            Поля
	 * @return security -> {field, value}
	 */
	public Map<String, Map<String, String>> executeReferenceDataRequest(
			String name, String[] securities, String[] fields) {
		log.info("ReferenceDataRequest:" + name);

		final ReferenceDataRequest r = new ReferenceDataRequest(securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	/**
	 * Загрузка котировок
	 * 
	 * @param name
	 *            Название запроса
	 * @param startDate
	 * @param endDate
	 * @param securities
	 *            Коды
	 * @param fields
	 *            Поля
	 * @return security -> {date -> { field, value } }
	 */
	public Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(
			String name, String startDate, String endDate, String[] securities, String[] fields, String[] currencies) {
		log.info("HistoricalDataRequest:" + name);

		final HistoricalDataRequest r = new HistoricalDataRequest(startDate, endDate, securities, fields, currencies);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, Map<String, Map<String, String>>> executeBdhRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		log.info("BdhRequest:" + name);

		final BdhRequest r = new BdhRequest(dateStart, dateEnd, period, calendar, currencies, securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, Map<String, Map<String, String>>> executeBdhEpsRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields) {
		log.info("BdhEpsRequest:" + name);

		final BdhEpsRequest r = new BdhEpsRequest(dateStart, dateEnd, period, calendar, currencies, securities, fields);
		r.execute(name);
		return r.getAnswer();
	}

	public Map<String, String> executeFieldInfoRequest(String name, String code) {
		log.info("FieldInfoRequest:" + name);

		final FieldInfoRequest r = new FieldInfoRequest(code);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadCashFlowRequest(String name,
			Map<String, Long> ids, Map<String, String> dates) {
		log.info("LoadCashFlowRequest:" + name);

		CashFlowLoadRequest r = new CashFlowLoadRequest(ids, dates);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadCashFlowRequestNew(String name,
			Map<String, Long> ids, Map<String, String> dates) {
		log.info("LoadCashFlowRequest:" + name);

		CashFlowLoadRequestNew r = new CashFlowLoadRequestNew(ids, dates);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadValuesRequest(String name, Map<String, Long> ids) {
		log.info("LoadValuesRequest:" + name);

		ValuesLoadRequest r = new ValuesLoadRequest(ids);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadRateCouponRequest(String name, Map<String, Long> ids) {
		log.info("LoadRateCouponRequest:" + name);

		RateCouponLoadRequest r = new RateCouponLoadRequest(ids);
		r.execute(name);
		return r.getAnswer();
	}

	public List<Map<String, Object>> executeLoadAtrRequest(String name, String startDate, String endDate,
			String[] securities, String maType, Integer taPeriod, String period, String calendar) {
		log.info("LoadAtrRequest:" + name);

		AtrLoadRequest r = new AtrLoadRequest(startDate, endDate, securities, maType, taPeriod, period, calendar);
		r.execute(name);
		return r.getAnswer();
	}
}
