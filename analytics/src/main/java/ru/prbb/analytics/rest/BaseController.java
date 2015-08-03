package ru.prbb.analytics.rest;

import java.sql.SQLException;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.prbb.ArmUserInfo;
import ru.prbb.Utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.prbb.analytics.domain.Result;
import ru.prbb.analytics.domain.ResultError;

public abstract class BaseController {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	protected ArmUserInfo createUserInfo(HttpServletRequest request) {
		return new ArmUserInfo(request.getRemoteUser(), request.getRemoteAddr());
	}

	@ExceptionHandler
	@ResponseBody
	private Result handleException(Exception ex) {
		String message = ex.getMessage();

		if (ex instanceof PersistenceException) {
			try {
				Throwable cause = ex.getCause();
				SQLException sqlException = (SQLException) cause.getCause();
				message = sqlException.getMessage();
			} catch (Exception e) {
				log.error("catch PersistenceException", e);
			}
		}

		if (Utils.isEmpty(message)) {
			message = ex.toString();
		}

		return new ResultError(message);
	}

}
