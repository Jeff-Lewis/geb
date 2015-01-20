package ru.prbb.jobber.web;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.jobber.domain.ResultCode;
import ru.prbb.jobber.web.BaseController;

@Controller
public class LoginController
		extends BaseController
{

	private static final ResultCode LOGIN = new ResultCode(Boolean.FALSE, "login");
	private static final ResultCode LOGIN_ERROR = new ResultCode(Boolean.TRUE, "login_error");

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultCode getLogin()
	{
		log.info("GET /login");

		return LOGIN;
	}

	@RequestMapping(value = { "/login_error" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultCode getLoginError()
	{
		log.info("GET /login_error");

		return LOGIN_ERROR;
	}

	@RequestMapping(value = { "/login_success" }, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResultCode getLoginSuccess(HttpServletRequest request)
	{
		log.info("GET /login_success");

		Principal userPrincipal = request.getUserPrincipal();
		String user = (null != userPrincipal) ? userPrincipal.getName() : "Anonymous";

		ResultCode resultCode = new ResultCode(Boolean.TRUE, "login_success");
		resultCode.put("user", user);
		return resultCode;
	}

}
