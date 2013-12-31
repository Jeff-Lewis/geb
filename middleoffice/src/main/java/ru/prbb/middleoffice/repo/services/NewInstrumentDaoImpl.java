/**
 * 
 */
package ru.prbb.middleoffice.repo.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Ввод нового инструмента
 * 
 * @author RBr
 * 
 */
@Repository
public class NewInstrumentDaoImpl implements NewInstrumentDao
{
	@Autowired
	private EntityManager em;

	@Override
	public List<String[]> execute(String[] instruments) {
		final List<String> shares = new ArrayList<>();
		final List<String> indexes = new ArrayList<>();
		final List<String> bonds = new ArrayList<>();
		final List<String> futures = new ArrayList<>();

		final List<String[]> info = new ArrayList<>();
		for (String item : instruments) {
			final int p = item.indexOf('|');
			final String type = item.substring(0, p);
			final String code = item.substring(p + 1);

			if ("Акция".equals(type)) {
				shares.add(code);
				info.add(new String[] { code, type, "Добавлено в список." });
				continue;
			}
			if ("Индекс".equals(type)) {
				indexes.add(code);
				info.add(new String[] { code, type, "Добавлено в список." });
				continue;
			}
			if ("Облигация".equals(type)) {
				bonds.add(code);
				info.add(new String[] { code, type, "Добавлено в список." });
				continue;
			}
			if ("Фьючерс".equals(type)) {
				futures.add(code);
				info.add(new String[] { code, type, "Добавлено в список." });
				continue;
			}

			info.add(new String[] { code, type, "Неизвестный тип инструмента." });
		}

		if (!shares.isEmpty()) {
			processShares(info, shares);
		}

		if (!indexes.isEmpty()) {
			processIndexes(info, indexes);
		}

		if (!bonds.isEmpty()) {
			processBonds(info, bonds);
		}

		if (!futures.isEmpty()) {
			processFutures(info, futures);
		}

		return info;
	}

	private void processShares(List<String[]> info, List<String> codes) {
		// TODO NewInstrumentDaoImpl.processShares
	}

	private void processIndexes(List<String[]> info, List<String> codes) {
		// TODO NewInstrumentDaoImpl.processIndexes
	}

	private void processBonds(List<String[]> info, List<String> codes) {
		// TODO NewInstrumentDaoImpl.processBonds
	}

	private void processFutures(List<String[]> info, List<String> codes) {
		// TODO NewInstrumentDaoImpl.processFutures
	}

	private void updateInfo(List<String[]> info, String code, String type, String state) {
		for (String[] item : info) {
			if (code.equals(item[0]) && type.equals(item[1])) {
				item[2] = state;
				break;
			}
		}
	}
}
