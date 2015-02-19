/**
 * 
 */
package ru.prbb.jobber.repo;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import ru.prbb.jobber.domain.SecForJobRequest;
import ru.prbb.jobber.domain.SecurityItem;
import ru.prbb.jobber.domain.SendMessageItem;

/**
 * @author RBr
 * 
 */
public interface BloombergDao {

	public List<SecForJobRequest> getLoadEstimatesPeersData();

	public void putPeersData(List<Map<String, Object>> answer);

	public void putAnalysData(String[] securities, Map<String, List<Map<String, String>>> answer);

	public void putPeersProc(String[] securities, Map<String, List<String>> answer);

	public List<SecurityItem> getSecForUpdateFutures();

	public void putUpdatesFutures(List<SecurityItem> securities, Map<String, Map<String, String>> answer);

	public int execQuotesPortfolio(Date yesterday);

	public List<String> getSecForQuotes();

	public void putQuotes(String date, String[] securities, Map<String, Map<String, Map<String, String>>> answer);

	public List<String> getSecForAtr();

	public void putAtrData(String[] securities, List<Map<String, Object>> answer);

	public void putOverrideData(List<SecForJobRequest> securities, Map<String, Map<String, String>> answer);

	public List<SecForJobRequest> getSecForHistData();

	public void putHistParamsData(String date, String[] currencies, String[] cursec,
			Map<String, Map<String, Map<String, String>>> answer);

	public List<String> getSecForCurrency();

	public void putCurrencyData(String[] securities, Map<String, Map<String, String>> answer);

	public List<String> getSecForBonds();

	public void putBondsData(String[] securities, Map<String, Map<String, String>> answer);

	public List<SendMessageItem> exec(String sql);

}
