package ru.prbb.middleoffice.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.middleoffice.domain.ResultCode;

@Controller
@RequestMapping(value = "/")
public class IndexController
{

	private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String displayIndex(Model model)
	{
		log.info("GET /");
		model.addAttribute("username", "CURRENT_USER");
		return "index";
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultCode displayLogin()
	{
		log.info("GET /login");

		return ResultCode.LOGIN;
	}

	@RequestMapping(value = { "/login_error" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultCode displayLoginError()
	{
		log.info("GET /login_error");

		return ResultCode.LOGIN_ERROR;
	}

}
