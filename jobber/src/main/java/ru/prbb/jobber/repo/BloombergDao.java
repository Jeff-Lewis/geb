/**
 * 
 */
package ru.prbb.jobber.repo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import ru.prbb.jobber.domain.AtrLoadDataItem;
import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SendMessageItem;

/**
 * @author RBr
 * 
 */
public interface BloombergDao {

	public List<SecForJobRequest> getLoadEstimatesPeersData();

	public void putPeersData(Map<String, Object> answer);
	
	public Object putPeersDataTest(Map<String, Object> data);

	public void putAnalysData(String security, Map<String, String> data);

	public void putPeersProc(String security, String data);

	public List<SecurityItem> getSecForUpdateFutures();

	public void putUpdatesFutures(Long securityId, Map<String, String> data);

	public int execQuotesPortfolio(Date yesterday);

	public List<String> getSecForQuotes();

	public void putQuotes(String date, String security, Number value);

	public List<String> getSecForAtr();

	public void putAtrData(AtrLoadDataItem item);

	public void putOverrideData(String code, String value, String period);

	public List<SecForJobRequest> getSecForHistData();

	public void putHistParamsData(String security, String field, Date date, String value, String currency);

	public List<String> getSecForCurrency();

	public void putCurrencyData(String security, Integer quoteFactor, Number pxLast);

	public List<String> getSecForBonds();

	public void putBondsData(String security, String[] fields, Map<String, String> data);

	public List<SendMessageItem> exec(String sql);

}
