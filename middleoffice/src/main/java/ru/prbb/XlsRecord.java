/**
 * 
 */
package ru.prbb;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

/**
 * @author RBr
 * 
 */
public abstract class XlsRecord {

	private static final Log log = LogFactory.getLog(XlsRecord.class);

	public final long RowNum;

	protected XlsRecord(XSSFRow row) {
		RowNum = 1 + row.getRowNum();
	}

	protected Date getDateValue(XSSFRow row, char col) {
		final XSSFCell cell = row.getCell(col - 'A');
		java.util.Date d = null;
		if (null != cell) {
			if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
				final String s = cell.getStringCellValue();
				try {
					d = new SimpleDateFormat().parse(s);
				} catch (ParseException e) {
					log.error("Неправильный формат даты '" + s + "'", e);
				}
			} else {
				d = cell.getDateCellValue();
			}
		}
		return (null == d) ? null : new Date(d.getTime());
	}

	protected Time getTimeValue(XSSFRow row, char col) {
		final XSSFCell cell = row.getCell(col - 'A');
		java.util.Date d = null;
		if (null != cell) {
			if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
				final String s = cell.getStringCellValue();
				try {
					d = new SimpleDateFormat().parse(s);
				} catch (ParseException e) {
					log.error("Неправильный формат времени '" + s + "'", e);
				}
			} else {
				d = cell.getDateCellValue();
			}
		}
		return (null == d) ? null : new Time(d.getTime());
	}

	protected Integer getIntegerValue(XSSFRow row, char col) {
		final XSSFCell cell = row.getCell(col - 'A');
		Integer d = null;
		if (null != cell) {
			if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
				final String s = cell.getStringCellValue();
				try {
					d = new Integer(s);
				} catch (NumberFormatException e) {
					log.error("Неправильный формат целого '" + s + "'", e);
				}
			} else {
				d = (int) cell.getNumericCellValue();
			}
		}
		return d;
	}

	protected String getStringValue(XSSFRow row, char col) {
		final XSSFCell cell = row.getCell(col - 'A');
		if (null != cell) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell.getRichStringCellValue().getString();
			case Cell.CELL_TYPE_NUMERIC:
				return cell.getRawValue().toString();
			}
		}
		return "";
	}
}
