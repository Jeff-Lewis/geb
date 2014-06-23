package ru.prbb.middleoffice.rest.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ru.prbb.middleoffice.domain.DealsPatternItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.repo.services.DealsPatternDao;
import ru.prbb.middleoffice.rest.BaseController;

/**
 * Сохраненные шаблоны
 * 
 * @author RBr
 */
@Controller
@RequestMapping("/rest/DealsPattern")
public class DealsPatternController
		extends BaseController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private DealsPatternDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<DealsPatternItem> getShow()
	{
		log.info("GET DealsPattern");
		return dao.show();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public Result deleteItem(
			@PathVariable("id") Long id)
	{
		log.info("DEL DealsPattern: id={}", id);
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getDownload(HttpServletResponse response,
			@PathVariable("id") Long id)
	{
		log.info("GET DealsPattern/Download: id={}", id);
		DealsPatternItem item = dao.getById(id);

		String filename = item.getFile_name();
		try {
			//filename = URLEncoder.encode(item.getFile_name(), "UTF-8");
		} catch (Exception e) {
			log.error("Download DealsPattern", e);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType(item.getFile_type() + "; charset=utf-8");
		response.setHeader("Content-disposition", "attachment;filename=" + filename);

		return dao.getFileById(id);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Result postUpload(
			@RequestParam("upload") MultipartFile file)
	{
		if (file.isEmpty()) {
			log.warn("POST DealsPattern/Upload: file is empty");
			return Result.FAIL;
		}
		log.info("POST DealsPattern/Upload: " + file.getOriginalFilename());
		try {
			String name = file.getOriginalFilename();
			String type = file.getContentType();
			byte[] data = file.getBytes();
			dao.add(name, type, data);
		} catch (Exception e) {
			log.error("Upload DealsPattern", e);
			return Result.FAIL;
		}
		return Result.SUCCESS;
	}
}
