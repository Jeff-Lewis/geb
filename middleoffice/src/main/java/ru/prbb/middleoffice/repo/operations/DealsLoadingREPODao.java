/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;
import java.sql.Time;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;

import ru.prbb.ArmUserInfo;

import org.springframework.stereotype.Service;

import ru.prbb.XlsRecord;
import ru.prbb.middleoffice.repo.UserHistory.AccessAction;
import ru.prbb.middleoffice.services.EntityManagerService;

/**
 * @author RBr
 */
@Service
public class DealsLoadingREPODao
{

	public static class Record extends XlsRecord {

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

	@Autowired
	private EntityManagerService ems;

	public int put(ArmUserInfo user, Record r) {
		String sql = "{call dbo.mo_WebSet_putRepoDeals_sp ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				r.date, r.time, r.deal_num, r.deal_security, r.oper, r.price,
				r.quantity, r.account, r.rate, r.days, r.currency, r.trade_system);
	}

}
