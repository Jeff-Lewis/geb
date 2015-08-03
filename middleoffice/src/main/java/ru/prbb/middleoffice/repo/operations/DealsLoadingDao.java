/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.Utils;
import ru.prbb.XlsRecord;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class DealsLoadingDao
{

	public static class Record extends XlsRecord {

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
		public final String Initiator; //varchar(255) = null M

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
			Initiator = getStringValue(row, 'M');
		}
	}

	@Autowired
	private EntityManagerService ems;

	public int put(ArmUserInfo user, Record r) {
		String sql = "{call dbo.mo_WebSet_putDeals_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				r.Batch, r.TradeNum, r.TradeTicker, r.TradeDate, r.SettleDate, r.TradeOper, r.TradePrice,
				r.Quantity, r.Currency, r.TradeSystem, r.Account, r.Portfolio, r.Initiator);
	}

}
