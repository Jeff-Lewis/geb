package ru.prbb.jobber.repo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ru.prbb.jobber.domain.AtrLoadDataItem;
import ru.prbb.jobber.domain.SecForJobRequest;

public interface BloombergServicesJ {

	/**
	 * BDS запрос
	 * 
	 * @param name
	 * @param securities
	 * @param fields
	 * @return [PEERS, EARN_ANN_DT_TIME_HIST_WITH_EPS, ERN_ANN_DT_AND_PER,
	 *         BLOOMBERG_PEERS, BEST_ANALYST_RECS_BULK]
	 */
	Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields);

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
	 * @throws Exception
	 */
	Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields);

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
	 * @throws Exception
	 */
	Map<String, Map<String, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields, String[] currencies);

	/**
	 * Загрузка ATR
	 * 
	 * @throws Exception
	 */
	List<AtrLoadDataItem> executeAtrLoad(String name, Date startDate, Date endDate, String[] securities,
			String maType, Integer taPeriod, String period, String calendar);

	Map<String, Map<String, String>> executeBdpOverrideLoad(String name, List<SecForJobRequest> securities);

}
