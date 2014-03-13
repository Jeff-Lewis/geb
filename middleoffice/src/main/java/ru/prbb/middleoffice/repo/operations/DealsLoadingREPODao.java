/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;

import ru.prbb.XlsRecord;

/**
 * Загрузка сделок РЕПО из файла
 * 
 * @author RBr
 * 
 */
public interface DealsLoadingREPODao {

	class Record extends XlsRecord {

		public final Date date;
		public final Time time;
		public final String deal_num;
		public final String deal_security;
		public final String oper;
		public final String price;
		public final Integer quantity;
		public final String account;
		public final String rate;
		public final Integer days;
		public final String currency;
		public final String trade_system;

		public Record(XSSFRow row) {
			super(row);

			date = getDateValue(row, 'A');
			time = getTimeValue(row, 'B');
			deal_num = getStringValue(row, 'C');
			deal_security = getStringValue(row, 'D');
			oper = getStringValue(row, 'E');
			price = getStringValue(row, 'F');
			quantity = getIntegerValue(row, 'G');
			account = getStringValue(row, 'H');
			rate = getStringValue(row, 'I');
			days = getIntegerValue(row, 'J');
			currency = getStringValue(row, 'K');
			trade_system = getStringValue(row, 'L');
		}
	}

	/**
	 * @param records
	 * @return
	 */
	Map<Record, SQLException> put(List<Record> records);

}
