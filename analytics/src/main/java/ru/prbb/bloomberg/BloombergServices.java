/**
 * 
 */
package ru.prbb.bloomberg;

import java.util.Map;
import java.util.Set;

/**
 * @author RBr
 */
public interface BloombergServices {

	/**
	 * Ввод нового параметра
	 * 
	 * @param name
	 * @param code
	 * @return
	 */
	public Map<String, Object> executeFieldInfoRequest(String name, String code);

	/**
	 * BDH запрос с EPS
	 * @param name
	 * @param dateStart
	 * @param dateEnd
	 * @param period
	 * @param calendar
	 * @param currencies
	 * @param securities
	 * @param params
	 */
	public void executeBdhEpsRequest(String name, String dateStart, String dateEnd, String period,
			String calendar, Set<String> currencies, String[] securities, String[] params);

	/**
	 * BDH запрос
	 * @param name
	 * @param dateStart
	 * @param dateEnd
	 * @param period
	 * @param calendar
	 * @param currencies
	 * @param securities
	 * @param params
	 */
	public void executeBdhRequest(String name, String dateStart, String dateEnd, String period,
			String calendar, Set<String> currencies, String[] securities, String[] params);

	/**
	 * BDP запрос<br>
	 * BDP запрос ежедневный
	 * 
	 * @param name
	 * @param securities
	 * @param params
	 */
	public void executeBdpRequest(String name, String[] securities, String[] params);

	/**
	 * BDP с override
	 * 
	 * @param name
	 * @param securities
	 * @param params
	 * @param period
	 * @param over
	 */
	public void executeBdpRequestOverride(String name, String[] securities, String[] params,
			String period, String over);

	/**
	 * BDP с override-quarter
	 * 
	 * @param name
	 * @param securities
	 * @param params
	 * @param currencies
	 * @param over
	 */
	public void executeBdpRequestOverrideQuarter(String name, String[] securities, String[] params,
			Set<String> currencies, String over);

	/**
	 * BDS запрос
	 * 
	 * @param name
	 * @param securities
	 * @param params
	 */
	public void executeBdsRequest(String name, String[] securities, String[] params);
}
