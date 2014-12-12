/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.Map;

/**
 * Ввод нового инструмента
 * 
 * @author RBr
 */
public interface NewInstrumentDao {

	int putSecurityData(Map<String, String> values);

	int putIndexData(Map<String, String> values);

	int putBondsData(Map<String, String> values);

	int putFuturesData(Map<String, String> values);

	int putOptionsData(Map<String, String> values);

}
