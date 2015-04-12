package ru.prbb.analytics.repo;

import java.util.Map;

public interface BloombergServicesA {

	/**
	 * Выполнить запрос //blp/refdata, ReferenceDataRequest<br>
	 * Добавить несколько компаний
	 * 
	 * @param name
	 *            Название запроса
	 * @param securities
	 *            Коды
	 * @param fields
	 *            Поля
	 * @return security -> {field, value}
	 * @throws Exception
	 */
	Map<String, Map<String, String>> executeReferenceDataRequest(String name,
			String[] securities, String[] fields);

	/**
	 * BDP запрос<br>
	 * BDP запрос ежедневный
	 * 
	 * @param string
	 * @param securities
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	Map<String, Map<String, String>> executeBdpRequest(String name,
			String[] securities, String[] fields);

	/**
	 * BDP с override
	 * 
	 * @param string
	 * @param security
	 * @param fields
	 * @param period
	 * @param over
	 * @return
	 * @throws Exception
	 */
	Map<String, Map<String, Map<String, String>>> executeBdpRequestOverride(String name,
			String[] securities, String[] fields, String period, String over);

	/**
	 * BDP с override-quarter
	 * 
	 * @param name
	 * @param securities
	 * @param fields
	 * @param currencies
	 * @param over
	 * @return
	 * @throws Exception
	 */
	Map<String, Map<String, Map<String, String>>> executeBdpRequestOverrideQuarter(String name,
			String[] securities, String[] fields, String[] currencies, String over);

	/**
	 * BDS запрос
	 * 
	 * @param name
	 * @param securities
	 * @param fields
	 * @return
	 *         [Peers, EARN_ANN_DT_TIME_HIST_WITH_EPS, ERN_ANN_DT_AND_PER,
	 *         PeerTicker, BEST_ANALYST_RECS_BULK]
	 */
	Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields);

	/**
	 * BDH запрос с EPS
	 * 
	 * @param name
	 * @param dateStart
	 * @param dateEnd
	 * @param period
	 * @param calendar
	 * @param currencies
	 * @param securities
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	Map<String, Map<String, Map<String, String>>> executeBdhEpsRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields);

	/**
	 * BDH запрос
	 * 
	 * @param name
	 * @param dateStart
	 * @param dateEnd
	 * @param period
	 * @param calendar
	 * @param currencies
	 * @param securities
	 * @param fields
	 * @return
	 */
	Map<String, Map<String, Map<String, String>>> executeBdhRequest(String name,
			String dateStart, String dateEnd, String period, String calendar,
			String[] currencies, String[] securities, String[] fields);

	/**
	 * Ввод нового параметра
	 * 
	 * @param name
	 * @param code
	 * @return
	 * @throws Exception
	 */
	Map<String, String> executeFieldInfoRequest(String name, String code);

}