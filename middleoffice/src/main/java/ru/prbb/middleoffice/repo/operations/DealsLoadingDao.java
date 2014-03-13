/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;

import ru.prbb.Utils;
import ru.prbb.XlsRecord;

/**
 * Загрузка сделок
 * 
 * @author RBr
 * 
 */
public interface DealsLoadingDao {

	class Record extends XlsRecord {

		public final String Batch; // varchar(50) in может быть null A
		public final String TradeNum; // varchar(50) B
		public final String TradeTicker; // varchar(50) C
		public final Date TradeDate; // datetime D
		public final Date SettleDate; // datetime E
		public final String TradeOper; // varchar(50) F
		public final String TradePrice;// numeric(21,6) G
		public final String Quantity;// numeric(21,6) H
		public final String Currency; // varchar(10) I
		public final String TradeSystem; // varchar(255) J
		public final String Account; // varchar(255) K
		public final String Portfolio; // varchar(255)L

		public Record(XSSFRow row) {
			super(row);

			Batch = Utils.parseString(getStringValue(row, 'A'));
			TradeNum = getStringValue(row, 'B');
			TradeTicker = getStringValue(row, 'C');
			TradeDate = getDateValue(row, 'D');
			SettleDate = getDateValue(row, 'E');
			TradeOper = getStringValue(row, 'F');
			TradePrice = getStringValue(row, 'G');
			Quantity = getStringValue(row, 'H');
			Currency = getStringValue(row, 'I');
			TradeSystem = getStringValue(row, 'J');
			Account = getStringValue(row, 'K');
			Portfolio = getStringValue(row, 'L');
		}
	}

	/**
	 * @param records
	 * @return
	 */
	Map<Record, SQLException> put(List<Record> records);
}
