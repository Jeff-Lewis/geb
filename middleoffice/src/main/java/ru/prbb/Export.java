/**
 * 
 */
package ru.prbb;

import java.util.ArrayList;
import java.util.List;

/**
 * @author RBr
 */
public abstract class Export {

	public static Export newInstance() {
		boolean isUseHtml = true;
		return isUseHtml ? new ExportHTML() : new ExportODS();
	}

	protected String caption;
	protected final List<String> titles = new ArrayList<>();
	protected String[] columns;
	protected final List<Object[]> data = new ArrayList<>();

	/**
	 * @param caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @param string
	 */
	public void addTitle(String title) {
		titles.add(title);
	}

	/**
	 * @param values
	 */
	public void setColumns(String... values) {
		columns = values;
	}

	/**
	 * @param values
	 */
	public void addRow(Object... values) {
		data.add(values);
	}

	/**
	 * @return
	 */
	public abstract String getContentType();

	/**
	 * @return
	 */
	public abstract byte[] build();
}
