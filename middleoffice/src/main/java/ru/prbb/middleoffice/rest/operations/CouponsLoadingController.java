package ru.prbb.middleoffice.rest.operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.LoadInfoRecord;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.operations.CouponsLoadingDao;

/**
 * Загрузка погашения купонов из файла
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/CouponsLoading")
public class CouponsLoadingController
{

	@Autowired
	private CouponsLoadingDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ResultData upload()
	{
		List<CouponsLoadingDao.Record> records = new ArrayList<>();

		try {
			File upload = new File("");
			final InputStream is = new FileInputStream(upload);
			try {
				final XSSFWorkbook wb2007 = new XSSFWorkbook(is);
				final Sheet sheet = wb2007.getSheetAt(0);
				final Iterator<Row> itRows = sheet.rowIterator();

				// Пропустим строку с заголовками
				if (itRows.hasNext()) {
					itRows.next();
				}

				while (itRows.hasNext()) {
					final XSSFRow row = (XSSFRow) itRows.next();
					final CouponsLoadingDao.Record record = new CouponsLoadingDao.Record(row);
					if (!record.security_code.isEmpty()) {
						records.add(record);
					}
				}
			} finally {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<CouponsLoadingDao.Record, SQLException> errors = dao.put(records);

		final List<LoadInfoRecord> info = new ArrayList<>();

		for (Entry<CouponsLoadingDao.Record, SQLException> entry : errors.entrySet()) {
			CouponsLoadingDao.Record key = entry.getKey();
			SQLException val = entry.getValue();
			info.add(new LoadInfoRecord(String.valueOf(key.RowNum), val.getMessage()));
		}

		Collections.sort(info);

		return new ResultData(info);
	}

}
