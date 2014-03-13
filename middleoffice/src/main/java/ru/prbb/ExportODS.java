/**
 * 
 */
package ru.prbb;

import java.io.ByteArrayOutputStream;

import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;

/**
 * @author RBr
 */
public class ExportODS extends Export {

	ExportODS() {
	}

	@Override
	public String getContentType() {
		return "application/vnd.oasis.opendocument.spreadsheet";
		//return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	}

	@Override
	public byte[] build() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (OdfSpreadsheetDocument ods = OdfSpreadsheetDocument.newSpreadsheetDocument()) {
			OdfTable table = ods.getTableList().get(0);
			table.setTableName(caption);

			ods.save(out);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return out.toByteArray();
	}

}
