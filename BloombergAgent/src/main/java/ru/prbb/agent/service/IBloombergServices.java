/**
 * 
 */
package ru.prbb.agent.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ru.prbb.bloomberg.model.BloombergResultItem;
import ru.prbb.bloomberg.model.CashFlowData;
import ru.prbb.bloomberg.model.CashFlowResultItem;
import ru.prbb.bloomberg.model.SecForJobRequest;
import ru.prbb.bloomberg.model.SecurityItem;

/**
 * @author RBr
 * 
 */
public interface IBloombergServices {

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
	public Map<String, Map<String, String>> executeReferenceDataRequest(String name,
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
	 */
	public Map<String, Map<Date, Map<String, String>>> executeHistoricalDataRequest(String name,
			Date startDate, Date endDate, String[] securities, String[] fields);

	/**
	 * Загрузка доходности облигаций
	 * 
	 * @param startDate
	 * @param endDate
	 * @param securities
	 * @return
	 */
	public Map<String, Map<Date, Map<String, String>>> executeBondYeildLoad(Date startDate, Date endDate, String[] securities);

	/**
	 * Загрузка дат погашений
	 * 
	 * @param items
	 * @return
	 */
	public List<CashFlowResultItem> executeCashFlowLoad(List<CashFlowData> items);

	/**
	 * Загрузка котировок
	 * 
	 * @param startDate
	 * @param endDate
	 * @param securities
	 * @return
	 */
	public List<BloombergResultItem> executeQuotesLoad(Date startDate, Date endDate, String[] securities);

	/**
	 * Загрузка ставки по купонам
	 * 
	 * @param items
	 *            security -> id
	 * @return
	 */
	public List<BloombergResultItem> executeRateCouponLoad(Map<String, Long> items);

	/**
	 * Загрузка номинала
	 * 
	 * @param items
	 *            security -> id
	 * @return
	 */
	public List<BloombergResultItem> executeValuesLoad(Map<String, Long> items);

	/**
	 * Загрузка ATR
	 * 
	 * @param date
	 * @param securities
	 * @return 
	 */
	public Map<String, Map<java.sql.Date, Double>> executeAtrLoad(Date date, List<String> securities);

	/**
	 * 
	 * @param securities
	 * @return 
	 */
	public Map<String, Map<String, String>> executeBdpOverrideLoad(List<SecForJobRequest> securities);

	/**
	 * 
	 * @param securities
	 * @return 
	 */
	public Map<String, Object> executeBdsRequest(String name, String[] securities, String[] fields);

	/**
	 * 
	 * @param securities
	 * @return 
	 */
	public Map<String, Map<String, String>> executeBondsLoad(List<String> securities);

	/**
	 * 
	 * @param date
	 * @param securities
	 * @return
	 */
	public Map<String, Map<Date, Map<String, String>>> executeHistDataLoad(Date date, List<SecForJobRequest> securities);

	/**
	 * 
	 * @param date
	 * @param securities
	 * @return
	 */
	public Map<String, Map<Date, Map<String, String>>> executeQuotesLoad(Date date, List<String> securities);

	/**
	 * 
	 * @param securities
	 * @return 
	 */
	public Map<String, Map<String, String>> executeFuturesLoad(List<SecurityItem> securities);
}
