package ru.prbb.middleoffice.repo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ru.prbb.middleoffice.domain.AtrLoadDataItem;

public interface BloombergServicesM {

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
	 * Загрузка дат погашений
	 * 
	 * @param ids
	 *            [ security -> id ]
	 * @param dates
	 *            [ security -> date ]
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> executeCashFlowLoad(Map<String, Long> ids, Map<String, String> dates);

	/**
	 * Загрузка дат погашений
	 * 
	 * @param ids
	 *            [ security -> id ]
	 * @param dates
	 *            [ security -> date ]
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> executeCashFlowLoadNew(Map<String, Long> ids, Map<String, String> dates);

	/**
	 * Загрузка номинала
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	List<Map<String, String>> executeValuesLoad(Map<String, Long> ids);

	/**
	 * Загрузка ставки по купонам
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> executeRateCouponLoad(Map<String, Long> ids);

	/**
	 * Загрузка ATR
	 * 
	 * @throws Exception
	 */
	List<AtrLoadDataItem> executeAtrLoad(Date startDate, Date endDate, String[] securities,
			String maType, Integer taPeriod, String period, String calendar);

}