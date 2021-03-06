package ru.prbb.middleoffice.rest.operations;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ru.prbb.middleoffice.domain.LoadInfoResult;
import ru.prbb.middleoffice.domain.ResultData;
import ru.prbb.middleoffice.repo.operations.DealsLoadingREPODao;
import ru.prbb.middleoffice.repo.operations.DealsLoadingREPODao.Record;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Загрузка сделок РЕПО из файла
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/DealsLoadingREPO")
public class DealsLoadingREPOController
		extends BaseController
{

	@Autowired
	private DealsLoadingREPODao dao;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResultData postUpload(HttpServletRequest request,
			@RequestParam("upload") MultipartFile file) throws Exception
	{
		log.info("POST DealsLoadingREPO: " + file.getOriginalFilename());
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
				if (!record.deal_num.isEmpty()) {
					records.add(record);
				}
			}
		}

		Map<Record, Exception> errors = new HashMap<>();
		for (Record r : records) {
			try {
				dao.put(createUserInfo(request),r);
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
