/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;

import org.apache.poi.xssf.usermodel.XSSFRow;

import ru.prbb.XlsRecord;

/**
 * @author RBr
 *
 */
public interface CouponsLoadingDao {

	class Record extends XlsRecord {

		public final String security_code;
		public final Date record_date;
		public final String coupon_per_share;
		public final String quantity;
		public final Date receive_date;
		public final String account;
		public final String extra_costs;
		public final String currency;
		public final String oper_name;
		public final String fund;

		public Record(XSSFRow row) {
			super(row);

			char col = 'A';
			security_code = getStringValue(row, col++);
			record_date = getDateValue(row, col++);
			coupon_per_share = getStringValue(row, col++);
			quantity = getStringValue(row, col++);
			receive_date = getDateValue(row, col++);
			account = getStringValue(row, col++);
			fund = getStringValue(row, col++);
			extra_costs = getStringValue(row, col++);
			currency = getStringValue(row, col++);
			oper_name = getStringValue(row, col++);
		}
	}

	/**
	 * @param record
	 * @return
	 */
	int put(Record record);
}
