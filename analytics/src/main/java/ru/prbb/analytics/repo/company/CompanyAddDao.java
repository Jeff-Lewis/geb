/**
 * 
 */
package ru.prbb.analytics.repo.company;

import java.util.List;
import java.util.Map;

/**
 * Добавление компаний
 * 
 * @author RBr
 * 
 */
public interface CompanyAddDao {

	/**
	 * @param codes
	 * @param answer
	 * @return
	 */
	List<String[]> execute(String[] codes, Map<String, Map<String, String>> answer);

	/**
	 * @param info 
	 * @param codes
	 * @param answer
	 * @return
	 */
	Map<String, String> createPeriodicity(Map<String, StringBuilder> info,
			String[] codes, Map<String, Map<String, String>> answer);

	public class InfoItem {

		public final String code;
		public long id;
		public String name;
		public String crncy;

		public InfoItem(String code) {
			this.code = code;
		}

		@Override
		public String toString() {
			return name + '(' + String.valueOf(id) + ", " + crncy + ')';
		}

	}

	/**
	 * @param codes
	 * @return
	 */
	List<InfoItem> getCompanyInfo(String[] codes);

}
