/**
 * 
 */
package ru.prbb.jobber.repo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import ru.prbb.jobber.domain.BloombergResultItem;
import ru.prbb.jobber.domain.CashFlowData;
import ru.prbb.jobber.domain.CashFlowResultItem;

/**
 * @author RBr
 * 
 */
public interface BloombergServices {

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
	public List<BloombergResultItem> executeBondYeildLoad(Date startDate, Date endDate, String[] securities);

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
	 */
	public void executeAtrLoad(Date date);

	/**
	 * 
	 */
	public void executeBdpOverrideLoad();

	/**
	 * 
	 */
	public void executeBdsLoad();

	/**
	 * 
	 */
	public void executeBondsLoad();

	/**
	 * @param date
	 */
	public void executeHistDataLoad(Date date);

	/**
	 * @param date
	 */
	public void executeQuotesLoad(Date date);

	/**
	 * 
	 */
	public void executeFuturesLoad();
}
