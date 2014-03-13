/**
 * 
 */
package ru.prbb;

import java.util.Arrays;

/**
 * @author RBr
 */
public class ExportHTML extends Export {

	ExportHTML() {
	}

	@Override
	public String getContentType() {
		return "text/html";
	}

	@Override
	public byte[] build() {
		StringBuilder sb = new StringBuilder();

		sb.append("<!DOCTYPE html>\n");
		sb.append("<HTML>\n");
		sb.append("<HEAD>\n");
		sb.append("<META charset='utf-8'>\n");
		sb.append("<META http-equiv='Content-Type' content='text/html; charset=utf-8'>\n");
		sb.append("<TITLE>").append(caption).append("</TITLE>\n");
		sb.append("</HEAD>\n");
		sb.append("<BODY>\n");

		for (String title : titles) {
			sb.append("<H2>").append(title).append("</H2>\n");
		}

		sb.append("<TABLE cellspacing='0' border='1' cellpadding='5' style='white-space:nowrap'>\n");

		sb.append("<THEAD>\n");
		sb.append("<TR>\n");
		for (String col : columns) {
			sb.append("\t<TH>").append(col).append("</TH>\n");
		}
		sb.append("</TR>\n");
		sb.append("</THEAD>\n");

		sb.append("<TBODY>\n");
		if (data.isEmpty()) {
			sb.append("<TR>\n");
			sb.append("\t<TD colspan='").append(columns.length).append("'>Данных нет</TD>\n");
			sb.append("</TR>\n");
		} else {
			for (Object[] row : data) {
				sb.append("<TR>\n");
				if (row.length != columns.length) {
					row = Arrays.copyOf(row, columns.length);
				}
				for (Object cell : row) {
					if (null == cell) {
						cell = "&nbsp;";
					}
					sb.append("\t<TD>").append(cell).append("</TD>\n");
				}
				sb.append("</TR>\n");
			}
		}
		sb.append("</TBODY>\n");

		sb.append("</TABLE>\n");

		sb.append("</BODY>\n");
		sb.append("</HTML>\n");

		return sb.toString().getBytes();
	}

}
