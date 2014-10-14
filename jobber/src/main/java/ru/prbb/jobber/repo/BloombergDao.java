/**
 * 
 */
package ru.prbb.jobber.repo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import ru.prbb.jobber.bloomberg.BdsRequest.BEST_ANALYST_RECS_BULK;
import ru.prbb.jobber.bloomberg.BdsRequest.PeerData;
import ru.prbb.jobber.domain.AtrDataItem;
import ru.prbb.jobber.domain.BloombergResultItem;
import ru.prbb.jobber.domain.CashFlowResultItem;
import ru.prbb.jobber.domain.HistParamData;
import ru.prbb.jobber.domain.OverrideData;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.UpdateFutureData;

/**
 * @author RBr
 * 
 */
public interface BloombergDao {

	/**
	 * Список инструментов для загрузки ATR
	 * 
	 * @return
	 */
	public List<String> getSecForAtr();

	public void putAtrData(List<AtrDataItem> items);

	/**
	 * Список инструментов для загрузки
	 * 
	 * @return
	 */
	public List<SecForJobRequest> getLoadEstimatesPeersData();

	/**
	 * 
	 * @param items
	 */
	public void putOverrideData(List<OverrideData> items);

	/**
	 * 
	 * @param peer
	 * @param items
	 */
	public void putAnalysData(String peer, List<BEST_ANALYST_RECS_BULK> items);

	/**
	 * 
	 * @param items
	 */
	public void putPeersData(List<PeerData> items);

	/**
	 * 
	 * @param peer
	 * @param sec
	 */
	public void putPeersProc(String peer, List<String> sec);

	/**
	 * Список инструментов для загрузки
	 * 
	 * @return
	 */
	public List<String> getSecForBonds();

	/**
	 * 
	 * @param items
	 */
	public void putBondsData(List<BloombergResultItem> items);

	/**
	 * Список инструментов для загрузки
	 * 
	 * @return
	 */
	public List<SecForJobRequest> getSecForHistData();

	/**
	 * 
	 * @param items
	 */
	public void putHistParamsData(List<HistParamData> items);

	/**
	 * Список инструментов для загрузки
	 * 
	 * @return
	 */
	public List<String> getSecForQuotes();

	/**
	 * 
	 * @param items
	 */
	public void putQuotes(List<BloombergResultItem> items);

	/**
	 * Список инструментов для загрузки
	 * 
	 * @return
	 */
	public List<SecurityItem> getSecForUpdateFutures();

	/**
	 * 
	 * @param items
	 */
	public void putUpdatesFutures(List<UpdateFutureData> items);

	/**
	 * Загрузка доходности облигаций
	 * 
	 * @param items
	 */
	public void putBondYeild(final List<BloombergResultItem> items);

	/**
	 * Загрузка дат погашений
	 * 
	 * @param items
	 */
	public void putSecurityCashFlow(List<CashFlowResultItem> items);

	/**
	 * Загрузка котировок
	 * 
	 * @param items
	 */
	public void putQuotesOne(List<BloombergResultItem> items);

	/**
	 * Загрузка ставки по купонам
	 * 
	 * @param items
	 */
	public void putSecurityCouponSchedule(List<BloombergResultItem> items);

	/**
	 * Загрузка номинала
	 * 
	 * @param items
	 */
	public void putFaceAmount(List<BloombergResultItem> items);

	/**
	 * Ввод новой акции
	 * 
	 * @param values
	 */
	public void putSharesData(Map<String, String> values);

	/**
	 * Ввод нового индекса
	 * 
	 * @param values
	 */
	public void putIndexData(Map<String, String> values);

	/**
	 * Ввод новой облигации
	 * 
	 * @param values
	 */
	public void putBondsData(Map<String, String> values);

	/**
	 * Ввод нового фьючерса
	 * 
	 * @param values
	 */
	public void putFuturesData(Map<String, String> values);

	public List<String> getSecForCurrency();

	public void putCurrencyData(String blm_query_code, Date dated, Double px_last, Integer quote_factor);

}
