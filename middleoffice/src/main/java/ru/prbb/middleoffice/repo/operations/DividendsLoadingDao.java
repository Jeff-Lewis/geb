/**
 * 
 */
package ru.prbb.middleoffice.repo.operations;

import java.sql.Date;

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
public class DividendsLoadingDao
{

	public static class Record extends XlsRecord {

		public final String security_code;
		public final Date record_date;
		public final String div_per_share;
		public final String quantity;
		public final Date receive_date;
		public final String account;
		public final String extra_costs;
		public final String currency;
		public final String fund;

		public Record(XSSFRow row) {
			super(row);

			char col = 'A';
			security_code = getStringValue(row, col++);
			record_date = getDateValue(row, col++);
			div_per_share = getStringValue(row, col++);
			quantity = getStringValue(row, col++);
			receive_date = getDateValue(row, col++);
			account = getStringValue(row, col++);
			fund = getStringValue(row, col++);
			extra_costs = getStringValue(row, col++);
			currency = getStringValue(row, col++);
		}
	}

	@Autowired
	private EntityManagerService ems;

	public int put(ArmUserInfo user, Record r) {
		String sql = "{call dbo.mo_xlsLoadDividends_sp ?, ?, ?, ?, ?, ?, ?, ?, ?}";
		return ems.executeUpdate(AccessAction.OTHER, user, sql,
				r.security_code, r.record_date, r.div_per_share, r.quantity, r.receive_date, r.account, r.extra_costs, r.currency, r.fund);
	}

}
