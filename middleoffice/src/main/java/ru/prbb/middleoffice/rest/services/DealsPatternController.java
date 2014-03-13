package ru.prbb.middleoffice.rest.services;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.DealsPatternItem;
import ru.prbb.middleoffice.domain.Result;
import ru.prbb.middleoffice.repo.services.DealsPatternDao;

/**
 * Сохраненные шаблоны
 * 
 * @author RBr
 * 
 */
@Controller
@RequestMapping("/rest/DealsPattern")
public class DealsPatternController
{
	@Autowired
	private DealsPatternDao dao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<DealsPatternItem> show()
	{
		return dao.show();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	Result deleteById(
			@PathVariable("id") Long id)
	{
		dao.deleteById(id);
		return Result.SUCCESS;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] download(HttpServletResponse response,
			@PathVariable("id") Long id)
	{
		DealsPatternItem item = dao.getById(id);

		response.setHeader("Content-disposition", "attachment;filename=" + item.getFile_name());
		response.setContentType(item.getFile_type());

		return dao.getFileById(id);
	}

	@RequestMapping(value = "/Upload", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Object upload(
			@RequestParam Long id)
	{
		return null;
	}
}
