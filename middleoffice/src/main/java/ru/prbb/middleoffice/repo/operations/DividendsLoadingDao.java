/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;

import org.apache.poi.xssf.usermodel.XSSFRow;

import ru.prbb.XlsRecord;

/**
 * Загрузка дивидендов из файла
 * 
 * @author RBr
 * 
 */
public interface DividendsLoadingDao {

	class Record extends XlsRecord {

		public final String security_code;
		public final Date record_date;
		public final String div_per_share;
		public final String quantity;
		public final Date receive_date;
		public final String account;
		public final String extra_costs;
		public final String currency;

		public Record(XSSFRow row) {
			super(row);

			security_code = getStringValue(row, 'A');
			record_date = getDateValue(row, 'B');
			div_per_share = getStringValue(row, 'C');
			quantity = getStringValue(row, 'D');
			receive_date = getDateValue(row, 'E');
			account = getStringValue(row, 'F');
			extra_costs = getStringValue(row, 'G');
			currency = getStringValue(row, 'H');
		}
	}

	/**
	 * @param record
	 * @return
	 */
	int put(Record record);
}
