/**
 * 
 */
package ru.prbb;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.doc.table.OdfTableRow;

/**
 * @author RBr
 */
public class ExportXLS extends Export {

	ExportXLS() {
	}

	@Override
	public String getContentType() {
		return "application/vnd.ms-excel";
	}

	@Override
	public byte[] build() {
//		XSSFWorkbook wb2007 = new XSSFWorkbook();
//		XSSFSheet sheet = wb2007.getSheetAt(0);
//		wb2007.
//
//		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (OdfSpreadsheetDocument ods = OdfSpreadsheetDocument.newSpreadsheetDocument()) {
			OdfTable table = ods.getTableList().get(0);
			table.setTableName(caption);

			OdfTableRow row = table.getRowByIndex(0);
			for (int i = 0; i < titles.size(); i++) {
				String text = titles.get(i);
				OdfTableCell cell = row.getCellByIndex(0);
				cell.setStringValue(text);
				row = table.appendRow();
			}

			for (int c = 0; c < columns.length; c++) {
				OdfTableCell cell = row.getCellByIndex(c);
				String text = columns[c];
				cell.setStringValue(text);
			}

			if (data.isEmpty()) {
				String text = "Данных нет";
				OdfTableCell cell = row.getCellByIndex(0);
				cell.setStringValue(text);
			} else {
				for (Object[] rowData : data) {
					row = table.appendRow();
					int c = 0;
					for (Object cellData : rowData) {
						if (null == cellData) {
							continue;
						}
						OdfTableCell cell = row.getCellByIndex(c++);
						if (cellData instanceof Number) {
							Number number = (Number) cellData;
							Double value = number.doubleValue();
							cell.setFormatString("0.000");
							cell.setDoubleValue(value);
							continue;
						}
						if (cellData instanceof Date) {
							Date d = (Date) cellData;
							Calendar date = Calendar.getInstance();
							date.setTime(d);
							cell.setFormatString("dd.MM.yyyy");
							cell.setDateValue(date);
							continue;
						}
						String text = cellData.toString();
						cell.setStringValue(text);
					}
				}
			}

			ods.save(out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}

}
