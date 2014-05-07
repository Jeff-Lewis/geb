package ru.prbb.middleoffice.rest.operations;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ru.prbb.middleoffice.domain.LoadInfoResult;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.operations.CouponsLoadingDao;
import ru.prbb.middleoffice.repo.operations.CouponsLoadingDao.Record;

/**
 * Загрузка погашения купонов из файла
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/CouponsLoading")
public class CouponsLoadingController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CouponsLoadingDao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData postUpload(
			@RequestParam("upload") MultipartFile file)
	{
		log.info("POST CouponsLoading: " + file.getOriginalFilename());
		List<Record> records = new ArrayList<>();

		try (InputStream is = file.getInputStream()) {
			final XSSFWorkbook wb2007 = new XSSFWorkbook(is);
			final Sheet sheet = wb2007.getSheetAt(0);
			final Iterator<Row> itRows = sheet.rowIterator();

			// Пропустим строку с заголовками
			if (itRows.hasNext()) {
				itRows.next();
			}

			while (itRows.hasNext()) {
				final XSSFRow row = (XSSFRow) itRows.next();
				final Record record = new Record(row);
				if (!record.security_code.isEmpty()) {
					records.add(record);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<Record, Exception> errors = new HashMap<>();
		for (Record r : records) {
			try {
				dao.put(r);
			} catch (Exception e) {
				errors.put(r, e);
			}
		}

		final LoadInfoResult info = new LoadInfoResult(records.size(), errors.size());
		for (Entry<Record, Exception> entry : errors.entrySet()) {
			Record key = entry.getKey();
			Exception val = entry.getValue();
			info.addError(key.RowNum, val.getMessage());
		}

		return new ResultData(info);
	}

}
