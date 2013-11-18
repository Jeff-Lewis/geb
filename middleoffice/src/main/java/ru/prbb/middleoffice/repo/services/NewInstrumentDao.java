/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.List;

/**
 * Ввод нового инструмента
 * 
 * @author RBr
 * 
 */
public interface NewInstrumentDao {

	/**
	 * @param instruments
	 */
	List<String[]> execute(String[] instruments);

}
